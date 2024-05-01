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
        return Collections.unmodifiableCollection(employees.values());
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

    @Override
    public double getSumMonthSalaries() {
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("Нет ни одного сотрудника");
        }
        double monthSalaries = 0;
        for (Map.Entry<String, Employee> e : employees.entrySet()) {
            monthSalaries += e.getValue().getSalary();
        }
        return monthSalaries;
    }

    @Override
    public Employee getEmployeeWithMinSalary() {
        Employee empWithMinSalary = null;
        double minSalary = Double.MAX_VALUE;
        for (Map.Entry<String, Employee> e : employees.entrySet()) {
            if (e.getValue().getSalary() < minSalary) {
                minSalary = e.getValue().getSalary();
                empWithMinSalary = e.getValue();
            }
        }
        if (empWithMinSalary == null) {
            throw new EmployeeNotFoundException("Нет ни одного сотрудника");
        }
        return empWithMinSalary;
    }

    @Override
    public Employee getEmployeeWithMaxSalary() {
        Employee empWithMaxSalary = null;
        double maxSalary = Double.MIN_VALUE;
        for (Map.Entry<String, Employee> e : employees.entrySet()) {
            if (e.getValue().getSalary() > maxSalary) {
                maxSalary = e.getValue().getSalary();
                empWithMaxSalary = e.getValue();
            }
        }
        if (empWithMaxSalary == null) {
            throw new EmployeeNotFoundException("Нет ни одного сотрудника");
        }
        return empWithMaxSalary;
    }

    @Override
    public double getAverageMonthSalary() {
        return getSumMonthSalaries() / employees.size();
    }
}