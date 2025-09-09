package com.reliaquest.api.service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

import com.reliaquest.api.data.Employee;
import com.reliaquest.api.dto.EmployeesResponseDto;
import com.reliaquest.api.dto.SingleEmployeeResponseDto;
import com.reliaquest.api.dto.CreateEmployeeDto;
import com.reliaquest.api.dto.DeleteEmployeeDto;

@Service
public class EmployeeService implements IEmployeeService{

	private static final String baseUrl = "http://localhost:8112/api/";
	
	@Override
	public List<Employee> getAll() throws Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(baseUrl + "v1/employee"))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		ObjectMapper objectMapper = new ObjectMapper();
		EmployeesResponseDto employeeResponseDto = objectMapper.readValue(response.body(), EmployeesResponseDto.class);
		
		return employeeResponseDto.getData();
	}

	@Override
	public void insert(Employee employee) throws Exception{
		CreateEmployeeDto createEmployeeDto = new CreateEmployeeDto(employee.getEmployee_name()
				, employee.getEmployee_salary(), employee.getEmployee_age(), employee.getEmployee_title());
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(createEmployeeDto);
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "v1/employee"))
				.header("Content-Type", "application/json")
				.method("POST", BodyPublishers.ofString(jsonString))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
	}

	@Override
	public void delete(UUID id) throws Exception{
		Employee employee = getById(id);
		DeleteEmployeeDto deleteEmployeeDto = new DeleteEmployeeDto(employee.getEmployee_name());
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(deleteEmployeeDto);
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "v1/employee"))
				.header("Content-Type", "application/json")
				.method("DELETE", BodyPublishers.ofString(jsonString))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
	}

	@Override
	public Employee getById(UUID id) throws Exception{
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(baseUrl + "v1/employee/" + id))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		ObjectMapper objectMapper = new ObjectMapper();
		SingleEmployeeResponseDto singleEmployeeResponseDto = objectMapper.readValue(response.body(), SingleEmployeeResponseDto.class);
		
		return singleEmployeeResponseDto.getData();

	}

}
