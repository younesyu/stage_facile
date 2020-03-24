package com.stage_facile.stage_facile.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stage_facile.stage_facile.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	/**
	 * Trouver un utilisateur par son adresse mail.
	 * @param email
	 * @return L'utilisateur si présent en base.
	 */
	Optional<User> findByEmail(String email);
	
	/**
	 * Teste la présence d'un utilisateur ayant 
	 * l'adresse mail passée en paramètre.
	 * @param email
	 * @return true si l'utilisateur existe, false sinon.
	 */
	Boolean existsByEmail(String email);
	
	/**
	 * Retourne tous les utilisateurs ayant un rôle ROLE_USER.
	 * @return Le résultat en question sous forme de liste.
	 */
	@Query("SELECT u FROM User u WHERE u.role.id = 1")
	List<User> findStudents();
	

	/**
	 * Retourne tous les utilisateurs ayant un rôle ROLE_MODERATOR.
	 * @return Le résultat en question sous forme de liste.
	 */
	@Query("SELECT u FROM User u WHERE u.role.id = 2")
	List<User> findModerators();
	

	/**
	 * Retourne tous les utilisateurs ayant un rôle ROLE_ADMIN.
	 * @return Le résultat en question sous forme de liste.
	 */
	@Query("SELECT u FROM User u WHERE u.role.id = 3")
	List<User> findAdmins();
		

	/**
	 * Retourne la liste des utilisateurs ayant un rôle ROLE_MODERATOR
	 * qui ne sont pas validés.
	 */
	@Query("SELECT u "
			+ "FROM User u "
			+ "WHERE u.role.id = 2"
			+ "AND (u.validated = false OR u.validated IS NULL)")
	List<User> getNonValidatedUsers();
	
}
