package com.stage_facile.stage_facile.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stage_facile.stage_facile.models.User;
import com.stage_facile.stage_facile.repositories.UserRepository;
import com.stage_facile.stage_facile.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	public void loadUsers() {
		User user1 = new User();
    	user1.setFirstName("John");
    	user1.setLastName("Doe");
    	
    	User user2 = new User();
    	user2.setFirstName("Jane");
    	user2.setLastName("Doe");
    	
    	Stream.of(user1, user2).forEach(userRepository::save);
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
}
