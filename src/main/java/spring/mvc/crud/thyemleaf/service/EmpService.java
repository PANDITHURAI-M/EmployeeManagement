package spring.mvc.crud.thyemleaf.service;



import java.util.List;

import spring.mvc.crud.thyemleaf.entity.Employee;

public interface EmpService {

	List<Employee> findAll();
	
    Employee findEmployeeByID(int id);
	
	Employee save(Employee employee);
	
	void deleteEmployeeById(int theId);
}