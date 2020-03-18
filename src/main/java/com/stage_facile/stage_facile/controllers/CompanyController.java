package com.stage_facile.stage_facile.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stage_facile.stage_facile.models.Company;
import com.stage_facile.stage_facile.models.Internship;
import com.stage_facile.stage_facile.services.CompanyService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		super();
		this.companyService = companyService;
	}
	
	@GetMapping("/load")
	public ModelAndView loadCompaniesFromOds(Map<String, Object> model) {
		this.companyService.loadCompanies();
		return new ModelAndView("redirect:/companies/all", model);
	}
    
	@GetMapping("/all")
    public List<Company> getCompanies() {
        return this.companyService.findAll();
    }
	
	@PostMapping("/add")
    void addCompany(@RequestBody Company company) {
		companyService.save(company);
    }
	
	@GetMapping("/update")
    public Company update() {
    	Optional<Company> company = this.companyService.findById(2L);
    	company.ifPresent(i -> {
    		this.companyService.save(i);
    	});
    	return this.companyService.findById(2L).get();
    }
	
	@GetMapping("/{id}")
    public Optional<Company> findById(@PathVariable Long id) {
		return this.companyService.findById(id);
    }
	
	@GetMapping("/internships/{id}")
	public List<Internship> findRelatedInternships(@PathVariable Long id) {
		return this.companyService.findRelatedInternships(id);
	}
	
//	@GetMapping("/{name}")
//	public Company findByName(@PathVariable String name) {
//		List<Company> results = this.companyService.findByName(name); 
//		if (results.isEmpty()) return null;
//		return results.get(0);
//	}
}
