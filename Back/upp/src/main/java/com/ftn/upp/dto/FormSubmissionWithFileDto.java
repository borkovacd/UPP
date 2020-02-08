package com.ftn.upp.dto;

import java.util.List;

import com.ftn.upp.model.FormSubmissionDto;

import lombok.Data;

@Data
public class FormSubmissionWithFileDto {
	
	private List<FormSubmissionDto> form;
	private String file;
	private String fileName;

}
