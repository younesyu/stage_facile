package com.stage_facile.stage_facile.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stage_facile.stage_facile.models.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
	/**
	 * Trouver des entreprises par nom.
	 * @param name
	 * @return La liste des entreprises portant ce nom.
	 */
	@Query("SELECT c FROM Company c WHERE c.name=:name")
	public List<Company> findByName(@Param("name") String name);
	
	/**
	 * Trouver des entreprises par nom dans le secteur spécifié.
	 * @param name nom de l'entreprise
	 * @param industry nom du secteur d'activités
	 * @return La liste des entreprises portant ce nom dans ce secteur.
	 */
	@Query("SELECT c FROM Company c WHERE c.name=:name AND c.industry.name=:industry")
	public List<Company> findByNameAndIndustry(@Param("name") String name, @Param("industry") String industry);
	
}
