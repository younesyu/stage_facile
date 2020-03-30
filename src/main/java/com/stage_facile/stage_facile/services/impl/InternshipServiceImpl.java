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
import com.stage_facile.stage_facile.models.Review;
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
	public Internship save(Internship internship) {
		Industry industry = internship.getIndustry();
		Company company = internship.getCompany();

		if(industry.getId() == null) {
			industry = this.industryRepository.save(industry);
			internship.setIndustry(industry);
		}
		if (company.getId() == null) {
			if (company.getIndustry().getId() == null) {
				Industry cIndustry = company.getIndustry();
				cIndustry = this.industryRepository.save(cIndustry);
				company.setIndustry(cIndustry);
			}
			company = this.companyRepository.save(company);
			internship.setCompany(company);
			
		}
		
		return internshipRepository.save(internship);
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
		return (List<Internship>) internshipRepository.findValidated();
	}

	@Override
	public List<Internship> findNonValidated() {
		return this.findAll().stream()
				.filter(i -> !i.getValidated())
				.collect(Collectors.toList());
	}

	@Override
	public List<Object[]> findIndustryCounts() {
		return internshipRepository.findIndustryCounts();
	}

	@Override
	public Integer findInternshipCountByGender(boolean gender) {
		return internshipRepository.findInternshipCountByGender(gender);
	}

	@Override
	public void saveReview(Internship internship, Review review) {
//		Review savedReview = reviewRepository.save(review);
//		internship.setReview(savedReview);
//		internshipRepository.save(internship);
	}

	@Override
	public void deleteReview(Internship internship) {
//		try {
//			reviewRepository.delete(internship.getReview());
//			saveReview(internship, null);
//		} catch (IllegalArgumentException e) {
//			System.err.println(e);
//		}
	}

}
