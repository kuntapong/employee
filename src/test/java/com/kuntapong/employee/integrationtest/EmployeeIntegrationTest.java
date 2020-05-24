package com.kuntapong.employee.integrationtest;

import com.kuntapong.employee.controller.request.EmployeeRequest;
import com.kuntapong.employee.controller.request.LoginRequest;
import com.kuntapong.employee.controller.response.AuthResponse;
import com.kuntapong.employee.entity.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void AddNewEmployeeTest() {
        deleteAllEmployee();
        //Login
        LoginRequest loginReq = new LoginRequest();
        loginReq.setUsername("admin1");
        loginReq.setPassword("password1");

        ResponseEntity<AuthResponse> loginResponseEntity =
                restTemplate.postForEntity("/api/v1/auth/login", loginReq, AuthResponse.class);
        AuthResponse authResponse = loginResponseEntity.getBody();

        //Post add
        EmployeeRequest empReq = new EmployeeRequest();
        empReq.setFirstName("Michael");
        empReq.setLastName("lol");

        HttpHeaders headers = new HttpHeaders();
        headers.set("app-auth", authResponse.getToken());
        HttpEntity<EmployeeRequest> request = new HttpEntity<>(empReq, headers);

        ResponseEntity<Employee> responseEntity =
                restTemplate.postForEntity("/api/v1/employees", request, Employee.class);
        Employee response = responseEntity.getBody();

        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals("Michael", response.getFirstName());
        Assert.assertEquals("lol", response.getLastName());
    }

    @Test
    public void GetAllEmployeeTest() {
        deleteAllEmployee();
        //Login
        LoginRequest loginReq = new LoginRequest();
        loginReq.setUsername("admin1");
        loginReq.setPassword("password1");

        ResponseEntity<AuthResponse> loginResponseEntity =
                restTemplate.postForEntity("/api/v1/auth/login", loginReq, AuthResponse.class);
        AuthResponse authResponse = loginResponseEntity.getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.set("app-auth", authResponse.getToken());

        //Post add
        EmployeeRequest empReq = new EmployeeRequest();
        empReq.setFirstName("Michael");
        empReq.setLastName("lol");
        HttpEntity<EmployeeRequest> request = new HttpEntity<>(empReq, headers);
        restTemplate.postForEntity("/api/v1/employees", request, Employee.class);

        //Post add 2
        EmployeeRequest empReq2 = new EmployeeRequest();
        empReq2.setFirstName("firstname2");
        empReq2.setLastName("lastname2");
        HttpEntity<EmployeeRequest> request2 = new HttpEntity<>(empReq2, headers);
        restTemplate.postForEntity("/api/v1/employees", request2, Employee.class);

        //get all employees
        ParameterizedTypeReference<List<Employee>> responseType = new ParameterizedTypeReference<List<Employee>>() {};
        HttpEntity<EmployeeRequest> request3 = new HttpEntity<>(null, headers);
        ResponseEntity<List<Employee>> responseEntity =
                restTemplate.exchange("/api/v1/employees", HttpMethod.GET, request3, responseType);

        List<Employee> resultBody = responseEntity.getBody();
        Assert.assertEquals(2, resultBody.size());
        Assert.assertEquals("Michael", resultBody.get(0).getFirstName());
        Assert.assertEquals("lol", resultBody.get(0).getLastName());
        Assert.assertEquals("firstname2", resultBody.get(1).getFirstName());
        Assert.assertEquals("lastname2", resultBody.get(1).getLastName());
    }

    @Test
    public void getEmployeeByIDTest() {
        deleteAllEmployee();
        //Login
        LoginRequest loginReq = new LoginRequest();
        loginReq.setUsername("admin1");
        loginReq.setPassword("password1");

        ResponseEntity<AuthResponse> loginResponseEntity =
                restTemplate.postForEntity("/api/v1/auth/login", loginReq, AuthResponse.class);
        AuthResponse authResponse = loginResponseEntity.getBody();

        //Post add
        EmployeeRequest empReq = new EmployeeRequest();
        empReq.setFirstName("Michael");
        empReq.setLastName("lol");

        HttpHeaders headers = new HttpHeaders();
        headers.set("app-auth", authResponse.getToken());
        HttpEntity<EmployeeRequest> request = new HttpEntity<>(empReq, headers);

        ResponseEntity<Employee> responseEntity =
                restTemplate.postForEntity("/api/v1/employees", request, Employee.class);
        Employee response = responseEntity.getBody();

        //Get employee by ID
        HttpEntity<EmployeeRequest> request2 = new HttpEntity<>(null, headers);
        ResponseEntity<Employee> responseGetEntity =
                restTemplate.exchange("/api/v1/employees/" + response.getId(), HttpMethod.GET, request2, Employee.class);

        Employee result = responseGetEntity.getBody();

        Assert.assertEquals("Michael", result.getFirstName());
        Assert.assertEquals("lol", result.getLastName());

    }

    @Test
    public void editEmployeeByIDTest() {
        deleteAllEmployee();
        //Login
        LoginRequest loginReq = new LoginRequest();
        loginReq.setUsername("admin1");
        loginReq.setPassword("password1");

        ResponseEntity<AuthResponse> loginResponseEntity =
                restTemplate.postForEntity("/api/v1/auth/login", loginReq, AuthResponse.class);
        AuthResponse authResponse = loginResponseEntity.getBody();

        //Post add
        EmployeeRequest empReq = new EmployeeRequest();
        empReq.setFirstName("Michael");
        empReq.setLastName("lol");

        HttpHeaders headers = new HttpHeaders();
        headers.set("app-auth", authResponse.getToken());
        HttpEntity<EmployeeRequest> request = new HttpEntity<>(empReq, headers);

        ResponseEntity<Employee> responseEntity =
                restTemplate.postForEntity("/api/v1/employees", request, Employee.class);
        Employee response = responseEntity.getBody();

        //Put to edit
        EmployeeRequest empReqPut = new EmployeeRequest();
        empReqPut.setFirstName("Michael edited");
        empReqPut.setLastName("lol edited");
        HttpEntity<EmployeeRequest> request2 = new HttpEntity<>(empReqPut, headers);
        ResponseEntity<Employee> responsePutEntity =
                restTemplate.exchange("/api/v1/employees/" + response.getId(), HttpMethod.PUT, request2, Employee.class);

        Employee result = responsePutEntity.getBody();

        Assert.assertEquals("Michael edited", result.getFirstName());
        Assert.assertEquals("lol edited", result.getLastName());
    }

    @Test
    public void deleteEmployeeByIDTest() {
        deleteAllEmployee();
        //Login
        LoginRequest loginReq = new LoginRequest();
        loginReq.setUsername("admin1");
        loginReq.setPassword("password1");

        ResponseEntity<AuthResponse> loginResponseEntity =
                restTemplate.postForEntity("/api/v1/auth/login", loginReq, AuthResponse.class);
        AuthResponse authResponse = loginResponseEntity.getBody();

        //Post add
        EmployeeRequest empReq = new EmployeeRequest();
        empReq.setFirstName("Michael");
        empReq.setLastName("lol");

        HttpHeaders headers = new HttpHeaders();
        headers.set("app-auth", authResponse.getToken());
        HttpEntity<EmployeeRequest> request = new HttpEntity<>(empReq, headers);

        ResponseEntity<Employee> responseEntity =
                restTemplate.postForEntity("/api/v1/employees", request, Employee.class);
        Employee response = responseEntity.getBody();

        //Delete
        HttpEntity<EmployeeRequest> request2 = new HttpEntity<>(null, headers);
        restTemplate.exchange("/api/v1/employees/" + response.getId(), HttpMethod.DELETE, request2, Employee.class);

        //get all employees to check remaining
        ParameterizedTypeReference<List<Employee>> responseType = new ParameterizedTypeReference<List<Employee>>() {};
        HttpEntity<EmployeeRequest> request3 = new HttpEntity<>(null, headers);
        ResponseEntity<List<Employee>> responseGetAllEntity =
                restTemplate.exchange("/api/v1/employees", HttpMethod.GET, request3, responseType);

        List<Employee> resultBody = responseGetAllEntity.getBody();
        Assert.assertEquals(0, resultBody.size());
    }

    private void deleteAllEmployee() {
        LoginRequest loginReq = new LoginRequest();
        loginReq.setUsername("admin1");
        loginReq.setPassword("password1");

        ResponseEntity<AuthResponse> loginResponseEntity =
                restTemplate.postForEntity("/api/v1/auth/login", loginReq, AuthResponse.class);
        AuthResponse authResponse = loginResponseEntity.getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.set("app-auth", authResponse.getToken());

        ParameterizedTypeReference<List<Employee>> responseType = new ParameterizedTypeReference<List<Employee>>() {};
        HttpEntity<EmployeeRequest> request3 = new HttpEntity<>(null, headers);
        ResponseEntity<List<Employee>> responseGetAllEntity =
                restTemplate.exchange("/api/v1/employees", HttpMethod.GET, request3, responseType);

        List<Employee> resultBody = responseGetAllEntity.getBody();

        for (Employee employee: resultBody) {
            HttpEntity<EmployeeRequest> request2 = new HttpEntity<>(null, headers);
            restTemplate.exchange("/api/v1/employees/" + employee.getId(), HttpMethod.DELETE, request2, Employee.class);

        }
    }

}
