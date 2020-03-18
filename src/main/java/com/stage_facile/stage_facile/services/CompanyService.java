package com.stage_facile.stage_facile.services;

import java.util.List;
import java.util.Optional;

import com.stage_facile.stage_facile.models.Company;
import com.stage_facile.stage_facile.models.Internship;

public interface CompanyService {
	public void loadCompanies();
	public List<Company> findAll();
	public void save(Company company);
	public Optional<Company> findById(Long id);
	public List<Company> findByName(String name);
	public void delete(Company company);
	public void update(Long id, Company company);
	/**
	 * Trouver les stages ayant été effectué chez 
	 * l'entreprise portant le numéro d'identification id
	 * @param id
	 * @return la liste des stages effectués chez l'entreprise
	 */
	public List<Internship> findRelatedInternships(Long id);
}
