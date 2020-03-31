package com.stage_facile.stage_facile.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * Entité entreprise, chaque entreprise travaille sur un secteur (Industry) défini.
 *
 */
@Entity
@Table(name = "companies")
public class Company {
	private @Id @GeneratedValue Long id;
	private String name;
	private @ManyToOne Industry industry;
	private String headOfficeAddress;
	private String postalCode;
	private String city;
	private String country;
	
	public Company() {}
	
	public Company(String name, Industry industry, String headOfficeAddress, String postalCode, String city,
			String country) {
		super();
		this.name = name;
		this.industry = industry;
		this.headOfficeAddress = headOfficeAddress;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public String getHeadOfficeAddress() {
		return headOfficeAddress;
	}

	public void setHeadOfficeAddress(String headOfficeAddress) {
		this.headOfficeAddress = headOfficeAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
