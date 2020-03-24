package com.stage_facile.stage_facile.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stage_facile.stage_facile.models.Internship;
import com.stage_facile.stage_facile.services.InternshipService;

/**
 * Contrôleur pour l'API Stages
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/internships")
public class InternshipController {

    @Autowired
    private InternshipService internshipService;

	public InternshipController(InternshipService internshipService) {
		super();
		this.internshipService = internshipService;
	}
	
	/**
	 * Charge en base les stages depuis le fichier .ods fourni
	 * en annexe.
	 * @param model
	 * @return redirection vers la requête /all
	 */
	@GetMapping("/load")
	public ModelAndView loadInternshipsFromOds(Map<String, Object> model) {
		this.internshipService.loadInternships();
		return new ModelAndView("redirect:/internships/all", model);
	}
    
	/**
	 * Renvoie tous les stages en base.
	 */
	@GetMapping("/all")
    public List<Internship> getInternships() {
        return this.internshipService.findAll();
    }
	
	/**
	 * Ajoute le stage en corps de requête à
	 * la base. 
	 * @param internship
	 */
	@PostMapping("/add")
    void addInternship(@RequestBody Internship internship) {
		internshipService.save(internship);
    }
	
	/**
	 * Supprime le stage en corps de requête de
	 * la base.
	 * @param internship
	 */
	@PostMapping("/delete")
	void deleteInternship(@RequestBody Internship internship) {
		internshipService.delete(internship);
    }
	
	/**
	 * Supprime tous les stages. (Admin uniquement)
	 */
	@GetMapping("/deleteAll")
    @PreAuthorize("hasRole('ADMIN')")
	void deleteAll() {
		internshipService.findAll().forEach(internshipService::delete);
	}
	
	
	@GetMapping("/update")
    public Internship update() {
    	Optional<Internship> internship = this.internshipService.find(2L);
    	internship.ifPresent(i -> {
    		this.internshipService.save(i);
    	});
    	return this.internshipService.find(2L).get();
    }
	
	/**
	 * Renvoie le stage d'identifiant id qui se trouve en base.
	 * @param id
	 * @return le stage d'id id
	 */
	@GetMapping("/{id}")
    public Optional<Internship> findById(@PathVariable Long id) {
    	return this.internshipService.find(id);
    }
	
	/**
	 * Renvoie la liste des stages validés par un gestionnaire
	 * de conventions.
	 */
	@GetMapping("/validated")
	public List<Internship> findValidated() {
		return this.internshipService.findValidated();
	}
	
    /**
	 * Renvoie la liste des stages non validés par un gestionnaire
	 * de conventions.
     */
    @GetMapping("/nonValidated")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Internship> nonValidated() {
    	return this.internshipService.findNonValidated();
    }
    
    /**
     * Valide le stage passé en corps de requête.
     * @param internship
     */
    @PostMapping("/validate")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public void validateMod(@RequestBody Internship internship) {
    	this.internshipService.find(internship.getId()).ifPresent(intshp -> {
			intshp.setValidated(true);
			internshipService.save(intshp);
    	});
    }
    
    @GetMapping("/setval")
    public void setval() {
    	for (Internship internship : this.internshipService.findAll()) {
    		internship.supressNullValidate();
    		internshipService.save(internship);
    	}
    }
}
