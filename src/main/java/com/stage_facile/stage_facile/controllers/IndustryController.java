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

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/industries")
public class IndustryController {

    @Autowired
    private IndustryService industryService;

	public IndustryController(IndustryService industryService) {
		super();
		this.industryService = industryService;
	}
	
	@GetMapping("/load")
	public ModelAndView loadCompaniesFromOds(Map<String, Object> model) {
		this.industryService.loadIndustries();
		return new ModelAndView("redirect:/industries/all", model);
	}
    
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
	
	@GetMapping("/")
    public Optional<Industry> findById(@RequestParam String id) {
		return this.industryService.findById(Long.parseLong(id));
    }
	
	@GetMapping("/{name}")
	public Industry findByName(@PathVariable String name) {
		List<Industry> results = this.industryService.findByName(name); 
		if (results.isEmpty()) return null;
		return results.get(0);
	}
}
