package com.stage_facile.stage_facile.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stage_facile.stage_facile.models.ERole;
import com.stage_facile.stage_facile.models.Internship;
import com.stage_facile.stage_facile.models.User;
import com.stage_facile.stage_facile.parser.OdsParser;
import com.stage_facile.stage_facile.repositories.InternshipRepository;
import com.stage_facile.stage_facile.repositories.RoleRepository;
import com.stage_facile.stage_facile.repositories.UserRepository;
import com.stage_facile.stage_facile.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	InternshipRepository internshipRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	public void loadUsers() {
		OdsParser.getStudents().forEach(student -> {
			if (userRepository.findByEmail(student.getEmail()).isPresent()) {
				return;
			}
			student.setRole(roleRepository.findByName(ERole.ROLE_USER).get());
			student.setValidated(true);
			userRepository.save(student);
			
		});
	}
	
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	public void save(User user) {
		userRepository.save(user);
		
	}

	@Override
	public Optional<User> find(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
		
	}

	@Override
	public void update(Long id, User user) {
		userRepository.findById(id)
			.ifPresent(u -> {
			u = user;
			userRepository.save(u);
		});
	}
	
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public Set<Internship> getInternships(Long id) {
		return internshipRepository.findInternshipsByUserId(id);
	}
	
	@Override
	public List<User> getNonValidatedMods() {
		return userRepository.getNonValidatedUsers();
	}

	@Override
	public List<User> findStudents() {
		return userRepository.findStudents();
	}
	
	@Override
	public List<User> findModerators() {
		return userRepository.findModerators();
	}

	@Override
	public List<User> findAdmins() {
		return userRepository.findAdmins();
	}

	@Override
	public boolean hasRightsToAlter(Long userId) {
		Optional<User> optionalCurrentUser = this.find(userId);
		if(!optionalCurrentUser.isPresent()) return false;
		
		User currentUser = optionalCurrentUser.get();
		
		ERole role = currentUser.getRole().getName();
		return (role == ERole.ROLE_ADMIN
				|| role == ERole.ROLE_MODERATOR);
	}
	
	
}
