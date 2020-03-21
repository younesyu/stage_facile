package com.stage_facile.stage_facile.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stage_facile.stage_facile.models.User;
import com.stage_facile.stage_facile.repositories.UserRepository;
import com.stage_facile.stage_facile.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
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
	
	
}
