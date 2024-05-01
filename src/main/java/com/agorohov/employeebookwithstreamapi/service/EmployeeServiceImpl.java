package com.agorohov.employeebookwithstreamapi.service;

import com.agorohov.employeebookwithstreamapi.exception.EmployeeAlreadyAddedException;
import com.agorohov.employeebookwithstreamapi.exception.EmployeeNotFoundException;
import com.agorohov.employeebookwithstreamapi.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Map<String, Employee> employees;

    public EmployeeServiceImpl() {
        employees = new HashMap<>();
    }

    private String getFullName(Employee employee) {
        return employee.getFirstName() + ' ' + employee.getLastName();
    }

    private String getFullName(String firstName, String lastName) {
        return firstName + ' ' + lastName;
    }

    @Override
    public Collection<Employee> findAllEmployees() {
        Collection<Employee> result = Collections.unmodifiableCollection(employees.values());
        if (result.isEmpty()) {
            throw new EmployeeNotFoundException("Нет ни одного сотрудника");
        }
        return result;
    }

    @Override
    public Employee addEmployee(String firstName, String lastName, int salary, int department) {
        Employee employee = new Employee(firstName, lastName, salary, department);
        if (employees.containsKey(getFullName(employee))) {
            throw new EmployeeAlreadyAddedException(employee + " уже существует, добавление невозможно");
        }
        employees.put(getFullName(employee), employee);
        return employee;
    }

    @Override
    public Employee removeEmployee(String firstName, String lastName) {
        Employee employee = Optional.ofNullable(employees.get(getFullName(firstName, lastName)))
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Нет сотрудника с именем " + firstName + " и фамилией " + lastName));
        employees.remove(getFullName(employee));
        return employee;
    }

    @Override
    public Employee findEmployee(String firstName, String lastName) {
        return Optional.ofNullable(employees.get(getFullName(firstName, lastName)))
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Нет сотрудника с именем " + firstName + " и фамилией " + lastName));
    }
}