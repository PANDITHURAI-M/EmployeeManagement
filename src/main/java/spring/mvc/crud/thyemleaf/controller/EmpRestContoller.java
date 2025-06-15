package spring.mvc.crud.thyemleaf.controller;




import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import spring.mvc.crud.thyemleaf.entity.Employee;
import spring.mvc.crud.thyemleaf.service.EmpService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api")
public class EmpRestContoller {

	private EmpService employeeService;
	
	private ObjectMapper objectMapper;
	
	@Autowired
	public EmpRestContoller(EmpService employeeService,ObjectMapper objectMapper) {
		this.employeeService=employeeService;
		this.objectMapper=objectMapper;
	}
	
	//GET - List of Employees
	@GetMapping("/employees")
	public List<Employee> getEmployeees() {
		return employeeService.findAll();
	}
	
	//GET - Employee  by id
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployeeById(@PathVariable int employeeId) {
		 Employee emlpoyee = employeeService.findEmployeeByID(employeeId);
		 
		 if(emlpoyee==null) {
			 throw new RuntimeException("Employee id not found - "+employeeId);
		 }
		 
		 return emlpoyee;
	}
	
	//POST - employee
	
	@PostMapping("/employees")
	//@RequestBody - converts json to pojo
	public Employee addEmployee(@RequestBody Employee employee) {
		employee.setId(0); // because we used auto generate id in database
		Employee dbEmployee = employeeService.save(employee);
		return dbEmployee;
				
	}
	//PUT - Update an existing employee
	
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee employee) {
		
		Employee dbEmployee = employeeService.save(employee);
		
		return dbEmployee;
		
	}
	
	//DELETE - delete an existing employee
	
	@DeleteMapping("/employees/{employeeId}")
	public String deleteById(@PathVariable int employeeId) {
      //find employee
		Employee dbEmployee = employeeService.findEmployeeByID(employeeId);
		
		if(dbEmployee==null) {
			throw new RuntimeException("Employee id not found : "+employeeId);
		}
		
		employeeService.deleteEmployeeById(employeeId);
		
		return "Deleted Employee id : " +employeeId;
		
	
	}
	
	//Patch - partially updating an existing employee
	//Rest API convention - always use id as a path variable

	@PatchMapping("/employees/{employeeId}")
		public Employee patchEmployee(@PathVariable int employeeId , 
			                       @RequestBody Map<String, Object> patchingDetails) {
		
		Employee tempEmployee = employeeService.findEmployeeByID(employeeId);
		
		// throw exception if if not found
		if(tempEmployee==null) {
			throw new RuntimeException("Employee id not found : "+employeeId);
		}
		
		//throw exception if id is mentioned in request body
		if(patchingDetails.containsKey("id")) {
		    throw new RuntimeException("Employee Id not allowed in request body :"+employeeId);
		}
		
		Employee patchedEmployee = apply(patchingDetails , tempEmployee);
		Employee dbEmployee = employeeService.save(patchedEmployee);
		return dbEmployee;
	}
	
	private Employee apply(Map<String , Object> patchDetails , Employee employee) {
		//Convert both to JSON Object Nodes
		
		ObjectNode convertedEmployee = objectMapper.convertValue(employee,ObjectNode.class );
		
		ObjectNode convertedPatchDetails =  objectMapper.convertValue(patchDetails, ObjectNode.class);
		
		//set all values from patchdetails to employee
		JsonNode node= convertedEmployee.setAll(convertedPatchDetails);
		
		//convert back to employee
		return objectMapper.convertValue(node, Employee.class);
	}
	
	
}
