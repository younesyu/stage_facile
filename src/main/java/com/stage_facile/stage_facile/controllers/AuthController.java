package com.stage_facile.stage_facile.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stage_facile.stage_facile.models.ERole;
import com.stage_facile.stage_facile.models.Role;
import com.stage_facile.stage_facile.models.User;
import com.stage_facile.stage_facile.repositories.RoleRepository;
import com.stage_facile.stage_facile.repositories.UserRepository;
import com.stage_facile.stage_facile.security.jwt.JwtUtils;
import com.stage_facile.stage_facile.security.payload.request.LoginRequest;
import com.stage_facile.stage_facile.security.payload.request.SignupRequest;
import com.stage_facile.stage_facile.security.payload.response.JwtResponse;
import com.stage_facile.stage_facile.security.payload.response.MessageResponse;
import com.stage_facile.stage_facile.security.services.UserDetailsImpl;
/**
 * Contrôleur pour l'API authentification.
 *
 */
@RestController
@CrossOrigin(origins = { "https://www.stage-facile.firebaseapp.com", "http://localhost:4200" })
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	/**
	 * Authentifie l'utilisateur en utilisant les identifiants
	 * passés en paramètre.
	 * @param loginRequest requête contenant les identifiants.
	 * @return Une requête 200 OK si l'utilisateur est authentifié.
	 * Une bad request est renvoyée si les identifiants sont incorrects,
	 * ou s'ils appartiennent à un compte modérateur non validé.
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String role = userDetails.getAuthorities().iterator().next().getAuthority(); 	
		
		// Vérifie si le compte modérateur est validé
		if (role.equals("ROLE_MODERATOR")) {
			User mod = userRepository.findById(userDetails.getId()).get();
			if(mod.getValidated() == false) {
					return ResponseEntity.badRequest().body(new MessageResponse("Ces identifiants sont associés à un compte non validé. Veuillez attendre qu'un administrateur vous octroie les droits d'accès."));
			}
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), role));
	}

	/**
	 * Inscrit dans la base de données l'utilisateur dont les informations sont passées
	 * dans la requête en paramètres.
	 * @param signUpRequest
	 * @return Une réponse 200 OK si l'utilisateur est enregistré.
	 * @throws RuntimeException si les rôles ne sont pas chargés en base
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Erreur: Cette adresse électronique est déjà utilisée."));
		}

		// Crée le nouveau compte utilisateur
		User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getBirthDate(),
				signUpRequest.getGender());
		user.setValidated(true);

		String strRoles = signUpRequest.getRole();
		Role role;

		if (strRoles == null) {
			role = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Erreur: Rôle non trouvé."));
		} else {
			if (strRoles.equals("admin")) {
				role = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Erreur: Rôle non trouvé."));
			} else if (strRoles.equals("mod")) {
				role = roleRepository.findByName(ERole.ROLE_MODERATOR)
						.orElseThrow(() -> new RuntimeException("Erreur: Rôle non trouvé."));
				user.setValidated(false);
			} else {
				role = roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Erreur: Rôle non trouvé."));
			}
		}

		user.setRole(role);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("Utilisateur créé avec succès!"));
	}

}
