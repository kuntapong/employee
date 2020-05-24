package com.kuntapong.employee.service;

import com.kuntapong.employee.entity.Employee;
import com.kuntapong.employee.entity.User;
import com.kuntapong.employee.repository.EmployeeRepository;
import com.kuntapong.employee.service.exception.EmployeeServiceNotfoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    Employee mockEmployee1;
    Employee mockEmployee2;

    @Before
    public void initData() {
        employeeRepository.deleteAll();
        mockEmployee1 = new Employee();
        mockEmployee1.setFirstName("mock name 1");
        mockEmployee1.setLastName("mock lastname 1");

        mockEmployee1 = employeeRepository.save(mockEmployee1);

        mockEmployee2 = new Employee();
        mockEmployee2.setFirstName("mock name 2");
        mockEmployee2.setLastName("mock lastname 2");

        mockEmployee2 = employeeRepository.save(mockEmployee2);
    }

    @Test
    public void getAllEmployeesSuccessTest() {
        List<Employee> result = employeeService.getAllEmployees();

        Assert.assertEquals(2, result.size());

        Assert.assertEquals(mockEmployee1.getId(), result.get(0).getId());
        Assert.assertEquals(mockEmployee1.getFirstName(), result.get(0).getFirstName());
        Assert.assertEquals(mockEmployee1.getLastName(), result.get(0).getLastName());

        Assert.assertEquals(mockEmployee2.getId(), result.get(1).getId());
        Assert.assertEquals(mockEmployee2.getFirstName(), result.get(1).getFirstName());
        Assert.assertEquals(mockEmployee2.getLastName(), result.get(1).getLastName());
    }

    @Test
    public void getEmployeeByIDSuccessTest() {
        Employee result = employeeService.getEmployeeByID(mockEmployee1.getId());

        Assert.assertEquals(mockEmployee1.getId(), result.getId());
        Assert.assertEquals(mockEmployee1.getFirstName(), result.getFirstName());
        Assert.assertEquals(mockEmployee1.getLastName(), result.getLastName());

        Employee result2 = employeeService.getEmployeeByID(mockEmployee2.getId());

        Assert.assertEquals(mockEmployee2.getId(), result2.getId());
        Assert.assertEquals(mockEmployee2.getFirstName(), result2.getFirstName());
        Assert.assertEquals(mockEmployee2.getLastName(), result2.getLastName());

    }

    @Test
    public void getEmployeeByIDNotFoundTest() {
        try {
            Employee result = employeeService.getEmployeeByID(1111l);
            Assert.fail("Should throw an exception");
        }catch(EmployeeServiceNotfoundException e) {

        }
    }

    @Test
    public void addEmployeeSuccessTest() {
        Employee result = employeeService.addEmployee("addedFirstName", "addedLastName");

        Assert.assertEquals("addedFirstName", result.getFirstName());
        Assert.assertEquals("addedLastName", result.getLastName());

        List<Employee> resultList = employeeService.getAllEmployees();
        Assert.assertEquals(3, resultList.size());
    }

    @Test
    public void editEmployeeSuccessTest() {
        Employee result = employeeService.getEmployeeByID(mockEmployee1.getId());

        Assert.assertEquals(mockEmployee1.getId(), result.getId());
        Assert.assertEquals(mockEmployee1.getFirstName(), result.getFirstName());
        Assert.assertEquals(mockEmployee1.getLastName(), result.getLastName());

        Employee resultEdited =employeeService.editEmployee(mockEmployee1.getId(), "name edited", "last edited");

        Assert.assertEquals("name edited", resultEdited.getFirstName());
        Assert.assertEquals("last edited", resultEdited.getLastName());

        Employee resultQuery = employeeService.getEmployeeByID(resultEdited.getId());

        Assert.assertEquals(resultEdited.getId(), resultQuery.getId());
        Assert.assertEquals(resultEdited.getFirstName(), resultQuery.getFirstName());
        Assert.assertEquals(resultEdited.getLastName(), resultQuery.getLastName());

    }

    @Test
    public void editEmployeeNotFoundTest() {
        try {
            Employee result = employeeService.editEmployee(1111l, "firstname", "lastname");
            Assert.fail("Should throw an exception");
        }catch(EmployeeServiceNotfoundException e) {

        }
    }

    @Test
    public void deleteEmployeeByIDSuccessTest() {
        List<Employee> resultList = employeeService.getAllEmployees();
        Assert.assertEquals(2, resultList.size());

        employeeService.deleteEmployeeByID(mockEmployee1.getId());

        List<Employee> resultList2 = employeeService.getAllEmployees();
        Assert.assertEquals(1, resultList2.size());

        Assert.assertEquals(mockEmployee2.getId(), resultList2.get(0).getId());
        Assert.assertEquals(mockEmployee2.getFirstName(), resultList2.get(0).getFirstName());
        Assert.assertEquals(mockEmployee2.getLastName(), resultList2.get(0).getLastName());

    }

    @Test
    public void deleteEmployeeByIDNotFoundTest() {
        try {
            employeeService.deleteEmployeeByID(1111l);
            Assert.fail("Should throw an exception");
        }catch(EmployeeServiceNotfoundException e) {

        }
    }
}
