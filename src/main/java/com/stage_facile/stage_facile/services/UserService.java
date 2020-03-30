package com.stage_facile.stage_facile.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.stage_facile.stage_facile.models.Internship;
import com.stage_facile.stage_facile.models.User;

public interface UserService {
	public void loadUsers();
	public List<User> findAll();
	public void save(User user);
	public Optional<User> find(Long id);
	public void delete(User user);
	public void update(Long id, User user);
	public Set<Internship> getInternships(Long id);
	public List<User> getNonValidatedMods();
	public List<User> findStudents();
	public List<User> findModerators();
	public List<User> findAdmins();
	public Optional<User> findByEmail(String email);
	public boolean hasRightsToAlter(Long userId);	
}
