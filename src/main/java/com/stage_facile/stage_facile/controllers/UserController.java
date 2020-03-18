package com.stage_facile.stage_facile.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
		this.loadUsers();
		return new ModelAndView("redirect:/users/all", model);
	}
    
    @GetMapping("/load")
    public void loadUsers() {
    	this.userService.loadUsers();
    }
 
    @GetMapping("/all")
    public List<User> getUsers() {
        return this.userService.findAll();
    }
 
    @PostMapping("/add")
    void addUser(@RequestBody User user) {
        userService.save(user);
    }
    
    @GetMapping("/update")
    public User update() {
    	Optional<User> user = this.userService.find(2L);
    	user.ifPresent(u -> {
    		u.setFirstName("Houari");
    		this.userService.save(u);
    	});
    	return this.userService.find(2L).get();
    }
    
    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable Long id) {
    	return this.userService.find(id);
    }
}
