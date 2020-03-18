package com.stage_facile.stage_facile.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stage_facile.stage_facile.models.Industry;
import com.stage_facile.stage_facile.parser.OdsParser;
import com.stage_facile.stage_facile.repositories.IndustryRepository;
import com.stage_facile.stage_facile.services.IndustryService;

@Service
public class IndustryServiceImpl implements IndustryService {

	@Autowired
	IndustryRepository industryRepository;

	@Override
	public void loadIndustries() {
		OdsParser.getIndustries().forEach(industryString -> {
			Industry industry = new Industry();
			industry.setName(industryString);
			if(industryRepository.findByName(industryString).isEmpty()) {
				industryRepository.save(industry);
			}
		});
		
	}
	
	@Override
	public List<Industry> findAll() {
		return (List<Industry>) industryRepository.findAll();
	}

	@Override
	public void save(Industry industry) {
		industryRepository.save(industry);
	}

	@Override
	public Optional<Industry> findById(Long id) {
		return industryRepository.findById(id);
	}
	
	@Override
	public List<Industry> findByName(String name) {
		return industryRepository.findByName(name);
	}

	@Override
	public void delete(Industry industry) {
		industryRepository.delete(industry);
	}

	@Override
	public void update(Long id, Industry industry) {
		industryRepository.findById(id).ifPresent(i -> {
			i = industry;
			industryRepository.save(i);
		});
	}
}
