package com.ftn.upp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class Coauthor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
	
	private String firstName; 
	
	private String lastName;
	
	private String email;
	
	private String state;
	
	private String city;
	
	@ManyToMany(mappedBy = "coauthors")
	private Set<Article> coauthoredArticles = new HashSet<Article>();

}
