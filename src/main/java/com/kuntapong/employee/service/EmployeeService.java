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
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByID(Long id) {
        Optional<Employee> result = employeeRepository.findById(id);

        if(result.isPresent()) {
            return result.get();
        }else {
            throw new EmployeeServiceNotfoundException(id);
        }
    }

    public Employee addEmployee(String firstName, String lastName) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);

        return employeeRepository.save(employee);
    }

    public Employee editEmployee(Long id, String firstName, String lastName) {
        Optional<Employee> result = employeeRepository.findById(id);

        if(result.isPresent()) {
            Employee employee = result.get();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);

            return employeeRepository.save(employee);
        }else {
            throw new EmployeeServiceNotfoundException(id);
        }
    }

    public void deleteEmployeeByID(Long id) {
        Optional<Employee> result = employeeRepository.findById(id);

        if(result.isPresent()) {
            employeeRepository.delete(result.get());
        }else {
            throw new EmployeeServiceNotfoundException(id);
        }
    }
}
