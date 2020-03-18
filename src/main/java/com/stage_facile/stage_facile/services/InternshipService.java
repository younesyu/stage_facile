package com.stage_facile.stage_facile.services;

import java.util.List;
import java.util.Optional;

import com.stage_facile.stage_facile.models.Internship;


public interface InternshipService {
	public void loadInternships();
	public List<Internship> findAll();
	public void save(Internship internship);
	public Optional<Internship> find(Long id);
	public void delete(Internship internship);
	/**
	 * Updates without modifying the id.
	 * @param id of the internship
	 * @param internship updated internship
	 */
	public void update(Long id, Internship internship);
	/**
	 * Gets a list of all validated internships.
	 */
	public List<Internship> findValidated();
}
