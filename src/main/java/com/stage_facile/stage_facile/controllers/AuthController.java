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

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String role = userDetails.getAuthorities().iterator().next().getAuthority(); 	
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

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getBirthDate(),
				signUpRequest.getGender());

		String strRoles = signUpRequest.getRole();
		Role role;

		if (strRoles == null) {
			role = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		} else {
			if (strRoles.equals("admin")) {
				role = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			} else if (strRoles.equals("mod")) {
				role = roleRepository.findByName(ERole.ROLE_MODERATOR)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			} else {
				role = roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			}
		}

		user.setRole(role);
		userRepository.save(user);
		System.err.println("great");
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}
