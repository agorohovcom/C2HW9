package com.agorohov.employeebookwithstreamapi.controller;

import com.agorohov.employeebookwithstreamapi.exception.EmployeeAlreadyAddedException;
import com.agorohov.employeebookwithstreamapi.exception.EmployeeNotFoundException;
import com.agorohov.employeebookwithstreamapi.model.Employee;
import com.agorohov.employeebookwithstreamapi.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController()
@RequestMapping(value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/add")
    public Employee addEmployee(@RequestParam String firstName,
                                @RequestParam String lastName) {
        return employeeService.addEmployee(firstName, lastName);
    }

    @GetMapping("/remove")
    public Employee removeEmployee(@RequestParam String firstName,
                                   @RequestParam String lastName) {
        return employeeService.removeEmployee(firstName, lastName);
    }

    @GetMapping("/find")
    public Employee findEmployee(@RequestParam String firstName,
                                 @RequestParam String lastName) {
        return employeeService.findEmployee(firstName, lastName);
    }

    @GetMapping()
    public Collection<Employee> findAllEmployees() {
        return employeeService.findAllEmployees();
    }

    // Перехват указанных исключений с целью вывода в браузер сообщений из исключений
    // Перекрывает @ResponseStatus
    @ExceptionHandler({EmployeeAlreadyAddedException.class, EmployeeNotFoundException.class})
    public String handleEmployeeNotFoundException(RuntimeException e) {
        e.printStackTrace();
        return e.getMessage();
    }
}