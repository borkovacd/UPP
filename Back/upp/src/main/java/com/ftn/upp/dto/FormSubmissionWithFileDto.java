package com.ftn.upp.dto;

import java.util.List;

import com.ftn.upp.model.ExtendedFormSubmissionDto;

import lombok.Data;

@Data
public class FormSubmissionWithFileDto {
	
	private List<ExtendedFormSubmissionDto> form;
	private String file;
	private String fileName;

}
