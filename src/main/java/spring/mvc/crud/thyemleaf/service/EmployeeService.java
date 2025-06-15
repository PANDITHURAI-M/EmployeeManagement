package spring.mvc.crud.thyemleaf.service;

import java.util.List;

import spring.mvc.crud.thyemleaf.entity.Employee;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(int theId);

    Employee save(Employee theEmployee);

    void deleteById(int theId);

}
