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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ftn.upp.enums.MemebershipPayment;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Magazine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "issn", nullable = false)
	private String issn;
	
	@Column(name = "openAccess", nullable = false)
	private  boolean openAccess;

	@Column(name = "active")
	private boolean active;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User mainEditor;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
	        name = "Magazine_Areas", 
	        joinColumns = { @JoinColumn(name = "magazine_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "magazineScientificArea_id") }
	  )
	@JsonIgnore
	private Set<MagazineScientificArea> magazineAreas = new HashSet<MagazineScientificArea>();
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
	        name = "Magazine_Reviewer", 
	        joinColumns = { @JoinColumn(name = "magazine_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "user_id") }
	  )
	@JsonIgnore
	private Set<User> reviewerMagazine = new HashSet<User>();
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
	        name = "Magazine_Editor", 
	        joinColumns = { @JoinColumn(name = "magazine_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "user_id") }
	  )
	@JsonIgnore
	private Set<User> editorMagazine = new HashSet<User>();
	

	public Magazine(){
		this.active = false;
	}

	public Magazine(Long id) {
		super();
		this.id = id;
	}
	

	

	public Magazine(Long id, String title, String issn, boolean openAccess, boolean active,
			Set<MagazineScientificArea> magazineAreas, Set<User> reviewerMagazine, Set<User> editorMagazine) {
		super();
		this.id = id;
		this.title = title;
		this.issn = issn;
		this.openAccess = openAccess;
		this.active = active;
		this.magazineAreas = magazineAreas;
		this.reviewerMagazine = reviewerMagazine;
		this.editorMagazine = editorMagazine;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}


	public Set<MagazineScientificArea> getMagazineAreas() {
		return magazineAreas;
	}

	public void setMagazineAreas(Set<MagazineScientificArea> magazineAreas) {
		this.magazineAreas = magazineAreas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<User> getReviewerMagazine() {
		return reviewerMagazine;
	}

	public void setReviewerMagazine(Set<User> reviewerMagazine) {
		this.reviewerMagazine = reviewerMagazine;
	}

	public Set<User> getEditorMagazine() {
		return editorMagazine;
	}

	public void setEditorMagazine(Set<User> editorMagazine) {
		this.editorMagazine = editorMagazine;
	}

	public boolean isOpenAccess() {
		return openAccess;
	}

	public void setOpenAccess(boolean openAccess) {
		this.openAccess = openAccess;
	}

	public User getMainEditor() {
		return mainEditor;
	}

	public void setMainEditor(User mainEditor) {
		this.mainEditor = mainEditor;
	}
	
	
	

}
