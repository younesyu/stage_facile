package com.stage_facile.stage_facile.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stage_facile.stage_facile.models.Internship;

@Repository
public interface InternshipRepository extends CrudRepository<Internship, Long>{
	@Query("SELECT i FROM Internship i WHERE i.company.id=:id")
	public List<Internship> findByCompanyId(@Param("id") Long id);
	

	@Query("SELECT i FROM Internship i WHERE i.user.id=:id")
	public Set<Internship> findInternships(@Param("id") Long id);
	
}
