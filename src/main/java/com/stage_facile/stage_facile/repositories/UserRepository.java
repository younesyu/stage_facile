package com.stage_facile.stage_facile.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stage_facile.stage_facile.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Boolean existsByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.role.id = 1")
	List<User> findStudents();
	
	@Query("SELECT u FROM User u WHERE u.role.id = 2")
	List<User> findModerators();
	
	@Query("SELECT u FROM User u WHERE u.role.id = 3")
	List<User> findAdmins();
		
	
	@Query("SELECT u "
			+ "FROM User u "
			+ "WHERE u.role.id = 2"
			+ "AND (u.validated = false OR u.validated IS NULL)")
	List<User> getNonValidatedUsers();
}
