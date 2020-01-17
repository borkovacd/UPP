package com.ftn.upp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ExtendedFormSubmissionDto implements Serializable{
	String fieldId;
	String fieldValue;
	ArrayList<String> categories = new ArrayList<String>();
	
	public ExtendedFormSubmissionDto(){}
	
	public ExtendedFormSubmissionDto(String fieldId, String fieldValue) {
		super();
		this.fieldId = fieldId;
		this.fieldValue = fieldValue;
	}
	
	
	public ExtendedFormSubmissionDto(String fieldId, String fieldValue,
			ArrayList<String> categories) {
		super();
		this.fieldId = fieldId;
		this.fieldValue = fieldValue;
		this.categories = categories;
	}

	public ArrayList<String> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
	}

	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	
}

