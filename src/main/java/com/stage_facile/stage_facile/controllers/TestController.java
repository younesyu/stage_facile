package com.stage_facile.stage_facile.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stage_facile.stage_facile.models.ERole;
import com.stage_facile.stage_facile.models.Role;
import com.stage_facile.stage_facile.models.User;
import com.stage_facile.stage_facile.repositories.RoleRepository;
import com.stage_facile.stage_facile.repositories.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/omg")
	public void load() {
		Role role = new Role();
		role.setName(ERole.ROLE_USER);
		roleRepo.save(role);

		Role role2 = new Role();
		role2.setName(ERole.ROLE_MODERATOR);
		roleRepo.save(role2);

		Role role3 = new Role();
		role3.setName(ERole.ROLE_ADMIN);
		roleRepo.save(role3);
		
	}
	
	@GetMapping("/delete")
	public void deleteroles() {
		roleRepo.deleteAll();
		
		
	}
	
	@GetMapping("/deleteUsers")
	public void deleteUsers() {
		userRepo.deleteAll();
		
	}

	@GetMapping("/users")
	public List<User> users() {
//		userRepo.findById(74L).ifPresent(userRepo::delete);
		return (List<User>) userRepo.findAll();
		
	}
	
	
	
	@GetMapping("/roles")
	public List<Role> roles() {
		return roleRepo.findAll();
		
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}
	
	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
	
	
}
