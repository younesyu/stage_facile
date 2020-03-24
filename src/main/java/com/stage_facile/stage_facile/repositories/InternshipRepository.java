package com.stage_facile.stage_facile.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stage_facile.stage_facile.models.Internship;

@Repository
public interface InternshipRepository extends CrudRepository<Internship, Long> {
	/**
	 * Retourne les stages ayant pour entreprise celle dont l'identifiant est passé
	 * en paramètre.
	 * 
	 * @param id
	 * @return Le résultat en question sous forme de liste.
	 */
	@Query("SELECT i FROM Internship i WHERE i.company.id=:id")
	public List<Internship> findByCompanyId(@Param("id") Long id);

	/**
	 * Retourne les stages ayant été effectués par l'utilisateur dont l'identifiant
	 * est passé en paramètre.
	 * 
	 * @param id
	 * @return Le résultat en question sous forme de liste.
	 */
	@Query("SELECT i FROM Internship i WHERE i.user.id=:id")
	public Set<Internship> findInternshipsByUserId(@Param("id") Long id);

	/**
	 * Retourne la liste des stages qui ne sont pas validés.
	 */
	@Query("SELECT i " 
	 + "FROM Internship i "
	+ "WHERE (i.validated = false OR i.validated IS NULL)")
	List<Internship> getNonValidatedInternships();
}
