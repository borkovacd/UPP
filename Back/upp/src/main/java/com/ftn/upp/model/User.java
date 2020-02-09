package com.ftn.upp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User implements Serializable {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String city;
	
	private String state;
	
	private String title;
	
	private String email;
	
	private boolean reviewer;
	
	private boolean activated;
	
	private String userType;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
	        name = "User_Areas", 
	        joinColumns = { @JoinColumn(name = "user_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "scientificArea_id") }
	  )
	private Set<MagazineScientificArea> interestedAreas = new HashSet<MagazineScientificArea>();
	
	@ManyToMany(mappedBy = "reviewerMagazine")
	private Set<Magazine> reviewedMagazines = new HashSet<Magazine>();
	
	@ManyToMany(mappedBy = "editorMagazine")
	private Set<Magazine> editedMagazines = new HashSet<Magazine>();
	
	public User() {
		activated = false;
	}

	public User(Long id, String username, String password, String firstName, String lastName, String city,
			String state, String title, String email, boolean reviewer, boolean activated, String userType) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.state = state;
		this.title = title;
		this.email = email;
		this.reviewer = reviewer;
		this.activated = activated;
		this.userType = userType;
	}
	
	public User(String username, String password, String firstName, String lastName, String city,
			String state, String title, String email, boolean reviewer, boolean activated, String userType) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.state = state;
		this.title = title;
		this.email = email;
		this.reviewer = reviewer;
		this.activated = activated;
		this.userType = userType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isReviewer() {
		return reviewer;
	}

	public void setReviewer(boolean reviewer) {
		this.reviewer = reviewer;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Set<Magazine> getReviewedMagazines() {
		return reviewedMagazines;
	}

	public void setReviewedMagazines(Set<Magazine> reviewedMagazines) {
		this.reviewedMagazines = reviewedMagazines;
	}

	public Set<Magazine> getEditedMagazines() {
		return editedMagazines;
	}

	public void setEditedMagazines(Set<Magazine> editedMagazines) {
		this.editedMagazines = editedMagazines;
	}

	public Set<MagazineScientificArea> getInterestedAreas() {
		return interestedAreas;
	}

	public void setInterestedAreas(Set<MagazineScientificArea> interestedAreas) {
		this.interestedAreas = interestedAreas;
	}
	
	


}

