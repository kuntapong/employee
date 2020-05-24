package com.kuntapong.employee.controller;

import com.kuntapong.employee.controller.request.EmployeeRequest;
import com.kuntapong.employee.entity.Employee;
import com.kuntapong.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "api/v1/employees")
public class EmployeeController {

    protected static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    @GetMapping("")
    public List<Employee> getAllEmployees() {
        log.info("Get all employees.");
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeByID(@PathVariable Long id) {
        log.info("Get employee by id({}).", id);
        return employeeService.getEmployeeByID(id);
    }

    @PostMapping("")
    public Employee addEmployee(@RequestBody EmployeeRequest req) {
        log.info("Add employee firstName({}), lastName({}).", req.getFirstName(), req.getLastName());
        return employeeService.addEmployee(req.getFirstName(), req.getLastName());
    }

    @PutMapping("/{id}")
    public Employee editEmployee(@PathVariable Long id, @RequestBody EmployeeRequest req) {
        log.info("Edit employee ID({}) firstName({}), lastName({}).", id, req.getFirstName(), req.getLastName());
        return employeeService.editEmployee(id, req.getFirstName(), req.getLastName());
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        log.info("Delete employee ID({}).", id);
        employeeService.deleteEmployeeByID(id);
    }
}
