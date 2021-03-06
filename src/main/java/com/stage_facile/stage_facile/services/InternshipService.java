package com.stage_facile.stage_facile.services;

import java.util.List;
import java.util.Optional;

import com.stage_facile.stage_facile.models.Internship;
import com.stage_facile.stage_facile.models.Review;


public interface InternshipService {
	public void loadInternships();
	public List<Internship> findAll();
	public Internship save(Internship internship);
	public Optional<Internship> find(Long id);
	public void delete(Internship internship);
	/**
	 * Updates without modifying the id.
	 * @param id of the internship
	 * @param internship updated internship
	 */
	public void update(Long id, Internship internship);
	/**
	 * Retourne la liste de tous les stages validés.
	 */
	public List<Internship> findValidated();
	
	/**
	 * Retourne la liste de tous les stages non validés.
	 */
	public List<Internship> findNonValidated();

	public List<Object[]> findIndustryCounts();
	
	public Integer findInternshipCountByGender(boolean gender);
	
	public void saveReview(Internship internship, Review review);
	
	public void deleteReview(Internship internship);
}
