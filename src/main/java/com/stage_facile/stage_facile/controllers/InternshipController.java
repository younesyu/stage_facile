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

import com.stage_facile.stage_facile.models.Internship;
import com.stage_facile.stage_facile.services.InternshipService;

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
	
	@GetMapping("/load")
	public ModelAndView loadInternshipsFromOds(Map<String, Object> model) {
		this.internshipService.loadInternships();
		return new ModelAndView("redirect:/internships/all", model);
	}
    
	@GetMapping("/all")
    public List<Internship> getInternships() {
        return this.internshipService.findAll();
    }
	
	@PostMapping("/add")
    void addInternship(@RequestBody Internship internship) {
		internshipService.save(internship);
    }
	
	@GetMapping("/update")
    public Internship update() {
    	Optional<Internship> internship = this.internshipService.find(2L);
    	internship.ifPresent(i -> {
    		this.internshipService.save(i);
    	});
    	return this.internshipService.find(2L).get();
    }
	
	@GetMapping("/{id}")
    public Optional<Internship> findById(@PathVariable Long id) {
    	return this.internshipService.find(id);
    }
	
	@GetMapping("/validated")
	public List<Internship> findValidated() {
		return this.internshipService.findValidated();
	}
}
