package com.stage_facile.stage_facile.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stage_facile.stage_facile.models.Company;
import com.stage_facile.stage_facile.models.ERole;
import com.stage_facile.stage_facile.models.Industry;
import com.stage_facile.stage_facile.models.Internship;
import com.stage_facile.stage_facile.models.User;
import com.stage_facile.stage_facile.parser.OdsParser;
import com.stage_facile.stage_facile.repositories.CompanyRepository;
import com.stage_facile.stage_facile.repositories.IndustryRepository;
import com.stage_facile.stage_facile.repositories.InternshipRepository;
import com.stage_facile.stage_facile.repositories.RoleRepository;
import com.stage_facile.stage_facile.repositories.UserRepository;
import com.stage_facile.stage_facile.services.InternshipService;

@Service
public class InternshipServiceImpl implements InternshipService{

	@Autowired
	private InternshipRepository internshipRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private IndustryRepository industryRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public void loadInternships() {
		
		OdsParser.getInternships().forEach(internship -> {
			// Save student, company and industry if not present in database
			Industry industry = internship.getIndustry();
			Company company = internship.getCompany();
			User student = internship.getUser();
			student.setPassword(encoder.encode(student.getPassword()));
			student.setRole(roleRepository.findByName(ERole.ROLE_USER).get());
			
			if (student != null && company != null && industry != null) {
				if (!userRepository.findByEmail(student.getEmail()).isPresent()) {
					userRepository.save(internship.getUser());
				}
				
				if(industryRepository.findByName(industry.getName()).isEmpty()) {
					industryRepository.save(industry);
				}
				// Save industry as company's industry
				Industry industryInDb = industryRepository.findByName(industry.getName()).get(0);
				company.setIndustry(industryInDb);
			
				// Save company as internship's company
				if(companyRepository.findByName(company.getName()).isEmpty()) {
					companyRepository.save(company);
				}
				Company companyInDb = companyRepository.findByName(company.getName()).get(0);
				internship.setCompany(companyInDb);
				internship.setIndustry(industryInDb);
				internship.setUser(userRepository.findByEmail(student.getEmail()).get());
				internshipRepository.save(internship);
			}
		});
	}

	@Override
	public List<Internship> findAll() {
		return (List<Internship>) internshipRepository.findAll();
	}

	@Override
	public void save(Internship internship) {
		internshipRepository.save(internship);
	}

	@Override
	public Optional<Internship> find(Long id) {
		return internshipRepository.findById(id);
	}

	@Override
	public void delete(Internship internship) {
		internshipRepository.delete(internship);
		
	}

	/**
	 * Updates without modifying the id.
	 * @param id of the internship
	 * @param internship updated internship
	 */
	@Override
	public void update(Long id, Internship internship) {
		internshipRepository.findById(id)
			.ifPresent(i -> {
			i = internship;
			internshipRepository.save(i);
		});
	}

	@Override
	public List<Internship> findValidated() {
		return this.findAll().stream()
				.filter(Internship::getValidated)
				.collect(Collectors.toList());
	}

	@Override
	public List<Internship> findNonValidated() {
		return this.findAll().stream()
				.filter(i -> !i.getValidated())
				.collect(Collectors.toList());
	}

}
