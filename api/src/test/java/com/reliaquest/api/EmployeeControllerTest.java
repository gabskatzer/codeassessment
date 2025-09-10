package com.reliaquest.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reliaquest.api.controller.EmployeeController;
import com.reliaquest.api.data.Employee;
import com.reliaquest.api.model.EmployeeInputModel;
import com.reliaquest.api.service.IEmployeeService;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

	@Mock
	private IEmployeeService employeeService;
	
	@InjectMocks
	private EmployeeController employeeController;
	
	List<Employee> expectedEmployees = new ArrayList<Employee>();
	
	@BeforeEach
	void setUp() throws Exception{
		//Arrange
		expectedEmployees.add(new Employee(UUID.randomUUID(), "John Doe", 104000, 45, "Software Engineer",
				"johndoe@email.com"));
		expectedEmployees.add(new Employee(UUID.randomUUID(), "Mark Doe2", 106000, 34, "Software Engineer",
				"markdoe2@email.com"));
		expectedEmployees.add(new Employee(UUID.randomUUID(), "Lucy Doe3", 108000, 25, "Software Engineer",
				"lucydoe3@email.com"));
		expectedEmployees.add(new Employee(UUID.randomUUID(), "Marie Doe4", 101000, 60, "Software Engineer",
				"mariedoe4@email.com"));
		expectedEmployees.add(new Employee(UUID.randomUUID(), "Marcus Doe5", 103000, 30, "Software Engineer",
				"marcusdoe5@email.com"));

		
	}
	
    @Test
    void getAllEmployeeTest() throws Exception{
    	//Arrange
    	when(employeeService.getAll()).thenReturn(expectedEmployees);
    	//Act
    	List<Employee> result = employeeController.getAllEmployees().getBody();
    	
    	//Assert
    	assertEquals(expectedEmployees, result);
        
    }
    
    @Test
    void getEmployeesByNameSearchTest() throws Exception{
    	//Arrange
    	when(employeeService.getAll()).thenReturn(expectedEmployees);
    	//Act
    	List<Employee> result = employeeController.getEmployeesByNameSearch("Lucy").getBody();
    	
    	//Assert
    	assertEquals(expectedEmployees.get(2), result.get(0));
    	assertEquals(1, result.size());
    }
    
    @Test
    void getEmployeeByIdTest() throws Exception {
    	//Arrange
		when(employeeService.getById(expectedEmployees.get(4).getId()))
		.thenReturn(expectedEmployees.get(4));
		
		//Act
    	String id = expectedEmployees.get(4).getId().toString();
    	
    	Employee result = employeeController.getEmployeeById(id).getBody();
    	
    	//Assert
    	assertEquals(expectedEmployees.get(4), result);

    }
    
    @Test
    void getHighestSalaryOfEmployeesTest() throws Exception {
    	//Arrange
    	when(employeeService.getAll()).thenReturn(expectedEmployees);
    	//Act
    	Integer result = employeeController.getHighestSalaryOfEmployees().getBody();
    	
    	//Assert
    	assertEquals(expectedEmployees.get(2).getEmployee_salary(), result);
    	
    }
    
    @Test
    void getTopTenHighestEarningEmployeeNamesTest() throws Exception {
    	//Arrange
    	when(employeeService.getAll()).thenReturn(expectedEmployees);
    	
    	//Act
    	List<String> result = employeeController.getTopTenHighestEarningEmployeeNames().getBody();
    	
    	//Assert
    	List<String> expectedResult = new ArrayList<String>();
    	expectedResult.add("Lucy Doe3");
    	expectedResult.add("Mark Doe2");
    	expectedResult.add("John Doe");
    	expectedResult.add("Marcus Doe5");
    	expectedResult.add("Marie Doe4");
    	assertEquals(expectedResult, result);
    	
    	
    }
    
    @Test
    void deleteEmployeeByIdTest() throws Exception {
    	
    	UUID mockID = UUID.randomUUID();
    	//Act
    	String result = employeeController.deleteEmployeeById(mockID.toString()).getBody();
    	
    	//Assert
    	assertEquals(mockID.toString(), result);
    	verify(employeeService).delete(mockID);
    	
    }
    
    @Test
    void createEmployeeTest() throws Exception {
    	//Arrange
    	EmployeeInputModel employeeInputModel = new EmployeeInputModel();
    	employeeInputModel.setName("Anna");
    	employeeInputModel.setAge(56);
    	employeeInputModel.setSalary(56000);
    	employeeInputModel.setTitle("Assistant");
    	
    	//Act
    	Employee result = employeeController.createEmployee(employeeInputModel).getBody();
    	
    	//Assert
    	verify(employeeService).insert(any(Employee.class));
    	
    }
    
    
}
