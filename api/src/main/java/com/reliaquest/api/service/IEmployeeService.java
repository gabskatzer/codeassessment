package com.reliaquest.api.service;

import java.util.List;
import java.util.UUID;

import com.reliaquest.api.data.Employee;

public interface IEmployeeService {
	
	List<Employee> getAll() throws Exception;
	void insert(Employee employee) throws Exception;
	Employee getById(UUID id) throws Exception;
	void delete(UUID id) throws Exception;
	
}
