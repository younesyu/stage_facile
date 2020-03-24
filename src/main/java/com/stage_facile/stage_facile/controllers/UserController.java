package com.stage_facile.stage_facile.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stage_facile.stage_facile.models.ERole;
import com.stage_facile.stage_facile.models.Internship;
import com.stage_facile.stage_facile.models.User;
import com.stage_facile.stage_facile.services.UserService;

/**
 * Contrôleur pour l'API Utilisateur
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController {
     
    @Autowired
    private UserService userService;
        
    public UserController(UserService userService) {
    	this.userService = userService; 
    }
    
    @RequestMapping("/")
	public ModelAndView welcome(Map<String, Object> model) {
		return new ModelAndView("redirect:/users/all", model);
	}

    /**
	 * Charge en base les utilisateurs depuis le fichier .ods fourni
	 * en annexe.
	 */
    @GetMapping("/load")
    public void loadUsers() {
        this.userService.loadUsers();
    }
    
	/**
	 * Renvoie tous les utilisateurs en base.
	 */
    @GetMapping("/all")
    public List<User> getUsers() {
        return this.userService.findAll();
    }
    
    /**
	 * Ajoute l'utilisateur en corps de requête à
	 * la base.
	 * @param user
	 */
    @PostMapping("/add")
    void addUser(@RequestBody User user) {
        userService.save(user);
    } 

    /**
     * Supprime l'utilisateur de la base.
     * @param user
     */
    @PostMapping("/delete")
    public void deleteUser(@RequestBody User user) {
        userService.delete(user);
    }
    
    /**
     * Supprime tous les utilisateurs de la base.
     */
    @GetMapping("/deleteAll")
    public void deleteUsers() {
        userService.findAll().forEach(userService::delete);
    }
    
	/**
	 * Renvoie l'utilisateur d'identifiant id qui se trouve en base.
	 * @param id
	 * @return l'utilisateur d'id id
	 */
    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable Long id) {
    	return this.userService.find(id);
    }
    
    /**
     * Renvoie l'utilisateur ayant pour email le paramètre.
     * @param email
     * @return
     */
    @GetMapping("/user")
    public Optional<User> findByEmail(@RequestParam String email) {
    	return this.userService.findByEmail(email);
    }
    
    /**
     * Renvoie l'ensemble des stages ayant été effectués par l'utilisateur
     * d'identifiant id.
     * @param id
     * @return
     */
    @GetMapping("/{id}/internships")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Set<Internship> findInternshipsById(@PathVariable Long id) {
        return userService.getInternships(id);
    }

    /**
     * Renvoie la liste des utilisateurs ayant pour rôle ROLE_USER.
     */
    @GetMapping("/students")
    public List<User> findStudents() {
    	return this.userService.findStudents();
    }

    /**
     * Renvoie la liste des utilisateurs ayant pour rôle ROLE_MODERATOR.
     */
    @GetMapping("/moderators")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findModerators() {
    	return this.userService.findModerators();
    }
    
    /**
     * Renvoie la liste des utilisateurs ayant pour rôle ROLE_ADMIN.
     */
    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAdmins() {
    	return this.userService.findAdmins();
    }
    
    /**
     * Renvoie la liste des utilisateurs ayant pour rôle ROLE_MODERATOR
     * et qui ont l'attribut validated à false.
     */
    @GetMapping("/nonValidatedMods")
	@PreAuthorize("hasRole('ADMIN')")
    public List<User> nonValidatedMods() {
    	return this.userService.getNonValidatedMods();
    }
    
    /**
     * Valide le modérateur passé en corps de requête.
     * @param moderator
     */
    @PostMapping("/validate")
	@PreAuthorize("hasRole('ADMIN')")
    public void validateMod(@RequestBody User moderator) {
    	this.userService.find(moderator.getId()).ifPresent(user -> {
    		if(user.getRole().getName().equals(ERole.ROLE_MODERATOR)) {
    			user.setValidated(true);
    			userService.save(user);
    		}
    	});
    }
    
    // To be deleted
    @GetMapping("/unvalidateAll")
    @PreAuthorize("hasRole('ADMIN')")
    public void unvalidate() {
    	this.userService.findAll().forEach(user -> {
    		if (user.getRole().getName().equals(ERole.ROLE_MODERATOR)) {
    			user.setValidated(false);
    			userService.save(user);
    		}
    	});
    }
    
    @GetMapping("/randomizeGenders")
    public void randomizeGenders() {
    	Random rand = new Random();
    	this.userService.findAll().forEach(user -> {
    		user.setGender(rand.nextInt() % 2 == 0);
    		userService.save(user);
    	});
    }
}
