package com.stage_facile.stage_facile.services;

import java.util.List;
import java.util.Optional;

import com.stage_facile.stage_facile.models.Industry;

public interface IndustryService {
	public void loadIndustries();
	public List<Industry> findAll();
	public void save(Industry industry);
	public Optional<Industry> findById(Long id);
	public List<Industry> findByName(String name);
	public void delete(Industry industry);
	public void update(Long id, Industry industry);
}
