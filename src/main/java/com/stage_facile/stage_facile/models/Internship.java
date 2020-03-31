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

import com.sun.istack.Nullable;
/**
 * Entité stage.
 * Tout stage est lié à un utilisateur (typiquement un étudiant).
 */
@Entity
@Table(name = "internships")
public class Internship {
	private @Id @GeneratedValue Long id;
	private @ManyToOne @Nullable User user;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Float getStipend() {
		return stipend;
	}

	public void setStipend(Float stipend) {
		this.stipend = stipend;
	}

	public Long getConventionReference() {
		return conventionReference;
	}

	public void setConventionReference(Long conventionReference) {
		this.conventionReference = conventionReference;
	}

	public String getExperienceLevel() {
		return experienceLevel;
	}

	public void setExperienceLevel(String experienceLevel) {
		this.experienceLevel = experienceLevel;
	}

	public List<String> getManagers() {
		return managers;
	}

	public void setManagers(List<String> managers) {
		this.managers = managers;
	}

	public Boolean getValidated() {
		return validated;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

	public String getFoundBy() {
		return foundBy;
	}

	public void setFoundBy(String foundBy) {
		this.foundBy = foundBy;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}
}
