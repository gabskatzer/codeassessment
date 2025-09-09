package com.reliaquest.api.dto;

public class DeleteEmployeeDto {
	
	private String name;
	

	public DeleteEmployeeDto(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
