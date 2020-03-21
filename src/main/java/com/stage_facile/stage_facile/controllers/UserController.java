package com.stage_facile.stage_facile.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stage_facile.stage_facile.models.ERole;
import com.stage_facile.stage_facile.models.User;
import com.stage_facile.stage_facile.services.UserService;

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
 
    @GetMapping("/all")
    public List<User> getUsers() {
        return this.userService.findAll();
    }
    
    @PostMapping("/add")
    void addUser(@RequestBody User user) {
        userService.save(user);
    } 
    
    @PostMapping("/delete")
    void deleteUser(@RequestBody User user) {
        userService.delete(user);
    }
    
    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable Long id) {
    	return this.userService.find(id);
    }

    @GetMapping("/students")
    public List<User> findStudents() {
    	return this.userService.findStudents();
    }

    @GetMapping("/moderators")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findModerators() {
    	return this.userService.findModerators();
    }
    
    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAdmins() {
    	return this.userService.findAdmins();
    }
    
    @GetMapping("/nonValidatedMods")
	@PreAuthorize("hasRole('ADMIN')")
    public List<User> nonValidatedMods() {
    	return this.userService.getNonValidatedMods();
    }
    
    @PostMapping("/validate")
	@PreAuthorize("hasRole('ADMIN')")
    public void validateMod(@RequestBody User moderator) {
    	System.err.println("Validating user of id " + moderator.getId() + "...");
    	this.userService.find(moderator.getId()).ifPresent(user -> {
    		if(user.getRole().getName().equals(ERole.ROLE_MODERATOR)) {
    			user.setValidated(true);
    			userService.save(user);
    		}
    	});
    }
    
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
}
