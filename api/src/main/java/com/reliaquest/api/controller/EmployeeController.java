package com.reliaquest.api.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.reliaquest.api.data.Employee;
import com.reliaquest.api.model.EmployeeInputModel;
import com.reliaquest.api.service.IEmployeeService;

@RestController
public class EmployeeController implements IEmployeeController<Employee, EmployeeInputModel>{
	
	@Autowired
	private IEmployeeService employeeService;
	
	private static final Logger logger = Logger.getLogger(EmployeeController.class.getName());

	@Override
	public ResponseEntity<List<Employee>> getAllEmployees(){
		try {
			List<Employee> employees = employeeService.getAll();
			return new ResponseEntity<>(employees, HttpStatus.OK);
		}catch(Exception e) {
			List<Employee> list = new ArrayList<Employee>();
			logger.warning("Error: " + e.getMessage());
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		
	}

	@Override
	public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
		try {
			List<Employee> employees = employeeService.getAll();
			List<Employee> filteredEmployees = employees.stream().filter(e -> e.getEmployee_name().contains(searchString))
					.collect(Collectors.toList());
			
			return new ResponseEntity<>(filteredEmployees, HttpStatus.OK);
		}catch(Exception e) {
			List<Employee> list = new ArrayList<Employee>();
			logger.warning("Error: " + e.getMessage());
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Employee> getEmployeeById(String id) {
		try {
			Employee employee = employeeService.getById(UUID.fromString(id));
			return new ResponseEntity<>(employee, HttpStatus.OK);
		}catch(Exception e) {
			logger.warning("Error: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
		try {
			List<Employee> employees = employeeService.getAll();
			Integer highestSalary = employees.stream()
					.map(e -> e.getEmployee_salary()).max(Integer::compare).get();
			
			return new ResponseEntity<>(highestSalary, HttpStatus.OK);
		}catch(Exception e) {
			logger.warning("Error: " + e.getMessage());
			return new ResponseEntity<>(0, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
		try {
			List<Employee> employees = employeeService.getAll();
			List<String> highestSalaryNames = employees.stream()
					.sorted(Comparator.comparingInt(Employee::getEmployee_salary).reversed())
					.map(e -> e.getEmployee_name())
					.limit(10)
					.collect(Collectors.toList());
			
			return new ResponseEntity<>(highestSalaryNames, HttpStatus.OK);
		}catch(Exception e) {
			List<String> list = new ArrayList<String>();
			logger.warning(e.getMessage());
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Employee> createEmployee(EmployeeInputModel employeeInput) {
		try {
			Employee employee = new Employee(UUID.randomUUID(), employeeInput.getName(), employeeInput.getSalary(),employeeInput.getAge(),
					employeeInput.getTitle(), null);
			employeeService.insert(employee);
			
			return new ResponseEntity<>(employee, HttpStatus.OK);
		}catch(Exception e) {
			logger.warning("Error: " + e.getMessage());
			return null;
		}

		
	}

	@Override
	public ResponseEntity<String> deleteEmployeeById(String id) {
		try {
			employeeService.delete(UUID.fromString(id));
			return new ResponseEntity<>(id, HttpStatus.OK);
		}catch(Exception e) {
			logger.warning("Error: " + e.getMessage());

			return null;
		}
		
	}

}
