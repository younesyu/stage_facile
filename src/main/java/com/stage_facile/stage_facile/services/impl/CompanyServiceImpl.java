package com.stage_facile.stage_facile.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stage_facile.stage_facile.models.Company;
import com.stage_facile.stage_facile.models.Industry;
import com.stage_facile.stage_facile.models.Internship;
import com.stage_facile.stage_facile.parser.OdsParser;
import com.stage_facile.stage_facile.repositories.CompanyRepository;
import com.stage_facile.stage_facile.repositories.IndustryRepository;
import com.stage_facile.stage_facile.repositories.InternshipRepository;
import com.stage_facile.stage_facile.services.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	InternshipRepository internshipRepository;
	
	@Autowired
	IndustryRepository industryRepository;
	
	@Override
	public void loadCompanies() {
		OdsParser.getCompanies().forEach(company -> {
			List<Company> resultInDb = companyRepository.findByName(company.getName()); 
			// If no company of the same name is stored in the db or no company with the same name AND same industry
			if (resultInDb.isEmpty() ||
					!resultInDb.stream().anyMatch(dbCompany -> 
					dbCompany.getIndustry().getName().equals(company.getIndustry().getName()))) {
				// Save industry before saving company because I don't know how cascades work
				Industry industry = company.getIndustry();
				Industry industryInDb;
				if (industry != null) {
					List<Industry> industryInDbList = industryRepository.findByName(industry.getName()); 
					if (!industryInDbList.isEmpty()) industryInDb = industryInDbList.get(0);
					else industryInDb = null;
					company.setIndustry(industryInDb);
				}
				companyRepository.save(company);
			}
		});
	}

	@Override
	public List<Company> findAll() {
		List<Company> all = (List<Company>) companyRepository.findAll();
		Collections.reverse(all);
		return all;
	}

	@Override
	public void save(Company company) {
		companyRepository.save(company);
	}

	@Override
	public Optional<Company> findById(Long id) {
		return companyRepository.findById(id);
	}
	
	public List<Company> findByName(String name) {
		return companyRepository.findByName(name);
	}

	@Override
	public void delete(Company company) {
		companyRepository.delete(company);
		
	}

	@Override
	public void update(Long id, Company company) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Internship> findRelatedInternships(Long id) {
		return this.internshipRepository.findByCompanyId(id);
	}


}
