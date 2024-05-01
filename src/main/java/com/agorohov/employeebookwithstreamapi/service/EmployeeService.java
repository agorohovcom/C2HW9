package com.agorohov.employeebookwithstreamapi.service;

import com.agorohov.employeebookwithstreamapi.model.Employee;

import java.util.Collection;

public interface EmployeeService {
    Employee addEmployee(String firstName, String lastName, int salary, int department);
    Employee removeEmployee(String firstName, String lastName);
    Employee findEmployee(String firstName, String lastName);
    Collection<Employee> findAllEmployees();

    double getSumMonthSalaries();

    Employee getEmployeeWithMinSalary();

    Employee getEmployeeWithMaxSalary();

    double getAverageMonthSalary();
}