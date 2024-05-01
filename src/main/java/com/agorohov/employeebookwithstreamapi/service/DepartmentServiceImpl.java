package com.agorohov.employeebookwithstreamapi.service;

import com.agorohov.employeebookwithstreamapi.exception.EmployeeNotFoundException;
import com.agorohov.employeebookwithstreamapi.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public int getSumMonthSalaries(int departmentId) {
        if (employeeService.findAllEmployees().isEmpty()) {
            throw new EmployeeNotFoundException("Нет ни одного сотрудника в отделе " + departmentId);
        }
        return employeeService
                .findAllEmployees()
                .stream()
                .filter(d -> d.getDepartment() == departmentId)
                .mapToInt(Employee::getSalary)
                .sum();
    }

    @Override
    public Employee getEmployeeWithMinSalary(int departmentId) {
        return employeeService
                .findAllEmployees()
                .stream()
                .filter(d -> d.getDepartment() == departmentId)
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Нет ни одного сотрудника в отделе " + departmentId));
    }

    @Override
    public Employee getEmployeeWithMaxSalary(int departmentId) {
        return employeeService
                .findAllEmployees()
                .stream()
                .filter(d -> d.getDepartment() == departmentId)
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Нет ни одного сотрудника в отделе " + departmentId));
    }

    @Override
    public double getAverageMonthSalary(int departmentId) {
        return employeeService
                .findAllEmployees()
                .stream()
                .filter(e -> e.getDepartment() == departmentId)
                .mapToDouble(Employee::getSalary)
                .average()
                .orElseThrow(() -> new EmployeeNotFoundException("Нет ни одного сотрудника в отделе " + departmentId));
    }

    @Override
    public Collection<Employee> findAllEmployees(int departmentId) {
        Collection<Employee> result = employeeService
                .findAllEmployees()
                .stream()
                .filter(e -> e.getDepartment() == departmentId)
                .toList();
        if (result.isEmpty()) {
            throw new EmployeeNotFoundException("Нет ни одного сотрудника в отделе " + departmentId);
        }
        return result;
    }

    @Override
    public Map<Integer, List<Employee>> findAllEmployees() {
        Map<Integer, List<Employee>> result = employeeService.findAllEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.toList()));
//        if (result.isEmpty()) {
//            throw new EmployeeNotFoundException("Нет ни одного сотрудника");
//        }
        return result;
    }
}
