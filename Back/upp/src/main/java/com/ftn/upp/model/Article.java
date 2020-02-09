package com.ftn.upp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User author;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
	        name = "Article_Coauthors", 
	        joinColumns = { @JoinColumn(name = "article_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "coauthor_id") }
	  )
	@JsonIgnore
	private Set<Coauthor> coauthors = new HashSet<Coauthor>();
	
	@Column(name = "keyTerms", nullable = false)
	private String keyTerms;
	
	@Column(name = "abstract", nullable = false)
	private String articleAbstract;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
	        name = "Article_Areas", 
	        joinColumns = { @JoinColumn(name = "article_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "magazineScientificArea_id") }
	  )
	@JsonIgnore
	private Set<MagazineScientificArea> articleAreas = new HashSet<MagazineScientificArea>();

}
