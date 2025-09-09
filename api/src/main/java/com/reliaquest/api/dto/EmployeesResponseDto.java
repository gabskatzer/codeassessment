package com.reliaquest.api.dto;

import java.util.List;

import com.reliaquest.api.data.Employee;

public class EmployeesResponseDto {

	private List<Employee> data;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Employee> getData() {
		return data;
	}

	public void setData(List<Employee> data) {
		this.data = data;
	} 
}
