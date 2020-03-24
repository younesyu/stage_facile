package com.stage_facile.stage_facile.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stage_facile.stage_facile.models.Industry;

@Repository
public interface IndustryRepository extends CrudRepository<Industry, Long> {
	/**
	 * Trouver des secteurs d'activité par nom.
	 * @param name
	 * @return La liste des secteurs d'activité portant ce nom.
	 */
	@Query("SELECT i FROM Industry i WHERE i.name=:name")
	public List<Industry> findByName(@Param("name") String name);
}
