package com.kuntapong.employee.service.exception;

public class EmployeeServiceNotfoundException extends RuntimeException {

    public EmployeeServiceNotfoundException(Long id) {
        super("Could not found the employee ID: " + id);
    }
}
