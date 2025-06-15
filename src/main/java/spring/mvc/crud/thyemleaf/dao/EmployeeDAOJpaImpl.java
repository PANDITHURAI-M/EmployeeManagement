package spring.mvc.crud.thyemleaf.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import spring.mvc.crud.thyemleaf.entity.Employee;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO{

	private EntityManager entityManager;
	
	@Autowired
	public EmployeeDAOJpaImpl(EntityManager entityManager) {
		this.entityManager=entityManager;
	}
	
	@Override
	public List<Employee> findAll() {
	
	  TypedQuery<Employee> theQuery =	entityManager.createQuery("from Employee" , Employee.class);
	   List<Employee> employees= theQuery.getResultList();
		return employees ;
	}

	@Override
	public Employee findEmployeeByID(int id) {
//	    Employee employee = entityManager.find(Employee.class, id);
	    TypedQuery<Employee> employee = entityManager.createQuery("from Employee where id=:data",Employee.class).setParameter("data", id);
		return employee.getSingleResult();
	}

	@Override
	public Employee save(Employee employee) {
		Employee Dbemployee = entityManager.merge(employee);
		return Dbemployee;
	}

	@Override
	public void deleteEmployeeById(int theId) {
//	    Employee delEmployee = findEmployeeByID(theId);
		Employee delEmployee = entityManager.find(Employee.class, theId);
		if(delEmployee==null) {
			throw new RuntimeException("Desired Employee Id not found : "+theId);
		}
		entityManager.remove(delEmployee);
		
	}

}