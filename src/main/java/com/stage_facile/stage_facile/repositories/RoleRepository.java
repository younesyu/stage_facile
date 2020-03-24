package com.stage_facile.stage_facile.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stage_facile.stage_facile.models.ERole;
import com.stage_facile.stage_facile.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	/**
	 * Trouver un rôle par son nom.
	 * @param name
	 * @return Le rôle si présent en base.
	 */
	Optional<Role> findByName(ERole name);
}
