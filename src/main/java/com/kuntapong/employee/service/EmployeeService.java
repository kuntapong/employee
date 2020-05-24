package com.kuntapong.employee.service;

import com.kuntapong.employee.entity.Employee;
import com.kuntapong.employee.repository.EmployeeRepository;
import com.kuntapong.employee.service.exception.EmployeeServiceNotfoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        //Find all employee
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByID(Long id) {
        //Find employee by ID.
        Optional<Employee> result = employeeRepository.findById(id);

        if(result.isPresent()) {
            //Return result.
            return result.get();
        }else {
            //Not found.
            throw new EmployeeServiceNotfoundException(id);
        }
    }

    public Employee addEmployee(String firstName, String lastName) {
        //Create new employee.
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);

        // return saved result.
        return employeeRepository.save(employee);
    }

    public Employee editEmployee(Long id, String firstName, String lastName) {
        //Find employee.
        Optional<Employee> result = employeeRepository.findById(id);

        if(result.isPresent()) {
            //If exist, update info.
            Employee employee = result.get();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            // Return saved result.
            return employeeRepository.save(employee);
        }else {
            //Not found.
            throw new EmployeeServiceNotfoundException(id);
        }
    }

    public void deleteEmployeeByID(Long id) {
        //Find employee.
        Optional<Employee> result = employeeRepository.findById(id);

        if(result.isPresent()) {
            //Do delete.
            employeeRepository.delete(result.get());
        }else {
            //Not found.
            throw new EmployeeServiceNotfoundException(id);
        }
    }
}
