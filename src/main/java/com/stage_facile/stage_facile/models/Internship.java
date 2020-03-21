package com.stage_facile.stage_facile.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "internships")
public @Data class Internship {
	private @Id @GeneratedValue Long id;
	private @ManyToOne User user;
	private LocalDate beginDate;
	private LocalDate endDate;
	private String function;
	private @Column(columnDefinition="TEXT") String description;
	private String location;
	private Float stipend;
	private Long conventionReference;
	private String experienceLevel;
	private @ElementCollection(targetClass = String.class) List<String> managers;
	private Boolean validated;
	private String foundBy;
	private @ManyToOne Company company;
	private @ManyToOne Industry industry;
	
	public Internship() {}

	public Internship(User user, LocalDate beginDate, LocalDate endDate, String function, String description, String location, Float stipend,
			Long conventionReference, String experienceLevel, List<String> managers, Boolean validated, String foundBy,
			Company company, Industry industry) {
		super();
		this.user = user;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.function = function;
		this.description = description;
		this.location = location;
		this.stipend = stipend;
		this.conventionReference = conventionReference;
		this.experienceLevel = experienceLevel;
		this.managers = managers;
		this.validated = validated;
		this.foundBy = foundBy;
		this.company = company;
		this.industry = industry;
	}
	
}
