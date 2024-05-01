package com.agorohov.employeebookwithstreamapi.service;

import com.agorohov.employeebookwithstreamapi.model.Employee;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DepartmentService {
    int getSumMonthSalaries(int department);
    Employee getEmployeeWithMinSalary(int department);
    Employee getEmployeeWithMaxSalary(int department);
    double getAverageMonthSalary(int department);
    Collection<Employee> findAllEmployees(int departmentId);
    Map<Integer, List<Employee>> findAllEmployees();
}
