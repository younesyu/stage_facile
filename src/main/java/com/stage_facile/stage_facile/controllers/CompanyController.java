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

/**
 * Contrôleur pour l'API entreprises. 
 */
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
	
	/**
	 * Charge en base les entreprises depuis le fichier .ods fourni
	 * en annexe.
	 * @param model
	 * @return redirection vers la requête /all
	 */
	@GetMapping("/load")
	public ModelAndView loadCompaniesFromOds(Map<String, Object> model) {
		this.companyService.loadCompanies();
		return new ModelAndView("redirect:/companies/all", model);
	}
    
	/**
	 * Renvoie toutes les entreprises en base.
	 */
	@GetMapping("/all")
    public List<Company> getCompanies() {
        return this.companyService.findAll();
    }
	
	/**
	 * Ajoute l'entreprise en corps de requête à
	 * la base.
	 * @param company
	 */
	@PostMapping("/add")
    void addCompany(@RequestBody Company company) {
		companyService.save(company);
    }
	
	/**
	 * Supprime l'entreprise en corps de requête de
	 * la base.
	 * @param company
	 */
	@PostMapping("/delete")
	void deleteInternship(@RequestBody Company company) {
		companyService.delete(company);
    }
	
	@GetMapping("/update")
    public Company update() {
    	Optional<Company> company = this.companyService.findById(2L);
    	company.ifPresent(i -> {
    		this.companyService.save(i);
    	});
    	return this.companyService.findById(2L).get();
    }
	
	/**
	 * Renvoie l'entreprise d'identifiant id qui se trouve en base.
	 * @param id
	 * @return l'entreprise d'id id
	 */
	@GetMapping("/{id}")
    public Optional<Company> findById(@PathVariable Long id) {
		return this.companyService.findById(id);
    }
	
	/**
	 * Renvoie les stages effectués à l'entreprise dont l'id
	 * est passé dans la requête.
	 * @param id de l'entreprise 
	 * @return La liste des stages effectués dans cette entreprise.
	 */
	@GetMapping("/internships/{id}")
	public List<Internship> findRelatedInternships(@PathVariable Long id) {
		return this.companyService.findRelatedInternships(id);
	}
}
