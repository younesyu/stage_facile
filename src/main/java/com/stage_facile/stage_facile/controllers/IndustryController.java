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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.stage_facile.stage_facile.models.Industry;
import com.stage_facile.stage_facile.services.IndustryService;
import com.stage_facile.stage_facile.services.InternshipService;

/**
 * Contrôleur pour l'API secteurs d'activités. 
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/industries")
public class IndustryController {

    @Autowired
    private IndustryService industryService;
    
    @Autowired
    private InternshipService internshipService;

	public IndustryController(IndustryService industryService) {
		super();
		this.industryService = industryService;
	}
	
	/**
	 * Charge en base les secteurs depuis le fichier .ods fourni
	 * en annexe.
	 * @param model
	 * @return redirection vers la requête /all
	 */
	@GetMapping("/load")
	public ModelAndView loadIndustriesFromOds(Map<String, Object> model) {
		this.industryService.loadIndustries();
		return new ModelAndView("redirect:/industries/all", model);
	}
    
	/**
	 * Renvoie tous les secteurs d'activités en base.
	 */
	@GetMapping("/all")
    public List<Industry> getIndustries() {
        return this.industryService.findAll();
    }
	
	@PostMapping("/add")
    void addIndustry(@RequestBody Industry industry) {
		industryService.save(industry);
    }
	
	@GetMapping("/update")
    public Industry update() {
    	Optional<Industry> industry = this.industryService.findById(2L);
    	industry.ifPresent(i -> {
    		this.industryService.save(i);
    	});
    	return this.industryService.findById(2L).get();
    }
	
	/**
	 * Renvoie le secteur d'activités d'identifiant id passé en paramètre
	 * qui se trouve en base.
	 * @param id
	 * @return le secteur d'id id.
	 */
	@GetMapping("/")
    public Optional<Industry> findById(@RequestParam String id) {
		return this.industryService.findById(Long.parseLong(id));
    }
	
	/**
	 * Renvoie le secteur d'activité portant le nom passé dans l'url.
	 * @param name nom du secteur.
	 * @return le secteur d'activité si présent.
	 */
	@GetMapping("/{name}")
	public Industry findByName(@PathVariable String name) {
		List<Industry> results = this.industryService.findByName(name); 
		if (results.isEmpty()) return null;
		return results.get(0);
	}
	
	@GetMapping("/count")
	public List<Object[]> count() {
		return this.internshipService.findIndustryCounts();
	}
}
