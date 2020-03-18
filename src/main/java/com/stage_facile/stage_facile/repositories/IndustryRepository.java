package com.stage_facile.stage_facile.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stage_facile.stage_facile.models.Industry;

@Repository
public interface IndustryRepository extends CrudRepository<Industry, Long> {
	@Query("SELECT i FROM Industry i WHERE i.name=:name")
	public List<Industry> findByName(@Param("name") String name);
}
