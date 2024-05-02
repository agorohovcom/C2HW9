package com.agorohov.employeebookwithstreamapi.model;

import java.util.Objects;

public class Employee {
    private final String firstName;
    private final String lastName;
    private final int salary;
    private final int department;

    public Employee(String firstName, String lastName, int salary, int department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.department = department;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSalary() {
        return salary;
    }

    public int getDepartment() {
        return department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;
        return salary == employee.salary && department == employee.department && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(firstName);
        result = 31 * result + Objects.hashCode(lastName);
        result = 31 * result + salary;
        result = 31 * result + department;
        return result;
    }

    @Override
    public String toString() {
        return "Сотрудник {"
                + "имя: " + firstName
                + ", фамилия: " + lastName
                + ", зарплата: " + salary
                + ", отдел: " + department
                + '}';
    }
}
