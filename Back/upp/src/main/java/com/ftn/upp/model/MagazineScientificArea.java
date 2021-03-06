package com.ftn.upp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class MagazineScientificArea {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

	@Column(name="name", nullable = false)
	private String name;
	
	@ManyToMany(mappedBy = "interestedAreas")
	private Set<User> users = new HashSet<User>();
	
	@ManyToMany(mappedBy = "magazineAreas")
	private Set<Magazine> magazines = new HashSet<Magazine>();
	
	@ManyToMany(mappedBy = "articleAreas")
	private Set<Article> articles = new HashSet<Article>();
	 
	public MagazineScientificArea(){}

	public MagazineScientificArea(String name){
		this.name =  name;
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
	

}
