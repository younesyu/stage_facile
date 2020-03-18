package com.stage_facile.stage_facile.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "companies")
public @Data class Company {
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
	
	
}
