package com.stage_facile.stage_facile.services;

import java.util.List;
import java.util.Optional;

import com.stage_facile.stage_facile.models.User;

public interface UserService {
	public List<User> findAll();
	public void save(User user);
	public Optional<User> find(Long id);
	public void delete(User user);
	public void update(Long id, User user);
	public List<User> getNonValidatedMods();
	public List<User> findStudents();
	public List<User> findModerators();
	public List<User> findAdmins();
	
}
