package spring.mvc.crud.thyemleaf.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.mvc.crud.thyemleaf.entity.Employee;
import spring.mvc.crud.thyemleaf.service.EmployeeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private Employee dbEmployee;  
	private EmployeeService employeeService;
	 
	// if only one constructor no @autowired is needed
	public EmployeeController(EmployeeService theEmployeeService) {
		this.employeeService=theEmployeeService;
	}
	
	@GetMapping("/list")
	public String getList(Model theModel) {
		
		List<Employee> list=employeeService.findAll();
		
		theModel.addAttribute("employee",list);
		
		return "employees/list-employee";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Employee theEmployee = new Employee();
		theModel.addAttribute("addEmployee" ,theEmployee);
		
		return "employees/employee-Add";
	}
	
	@PostMapping("/save")
	
	public String saveEmployee(@ModelAttribute("addEmployee") Employee theEmployee ,Model theModel) {
		
		employeeService.save(theEmployee);
		
//        List<Employee> list=employeeService.findAll();
//		
//		theModel.addAttribute("employee",list);
		//this prevent from multiple submissions , if client hits multiple times
		return "redirect:/employees/list";
	}
	
	@GetMapping("/update")
	public String updateEmployee(@RequestParam("id") int id,Model theModel) {
//		System.out.println(id);
	    dbEmployee = employeeService.findById(id);
		theModel.addAttribute("dbEmployee", dbEmployee);
		return "employees/employee-update";
	}
	
	@PostMapping("/saveUpdate")
	public String saveUpdate(@ModelAttribute("dbEmployee") Employee theEmployee) {
		employeeService.save(theEmployee);
		return "redirect:/employees/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		employeeService.deleteById(id);
		
		return "redirect:/employees/list";
	}
}
