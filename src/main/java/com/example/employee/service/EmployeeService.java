package com.example.employee.service;

import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.record.EmployeeRequest;
import com.example.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();

    public Collection<Employee> getAllEmployees(){
        return this.employees.values();
    }

    public Employee createEmployee(EmployeeRequest employeeRequest){
        if (employeeRequest.getFirstName() == null || employeeRequest.getLastName() == null) {
            throw new IllegalArgumentException("Не заполнены обязательные поля");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(), employeeRequest.getLastName(), employeeRequest.getDepartment(), employeeRequest.getSalary());

        employees.put(employee.getId(), employee);
        return employee;
    }

    public int getSummSalary(){
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public int getMinSalary(){
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .min()
                .orElseThrow(EmployeeNotFoundException:: new);
    }

    public int getMaxSalary(){
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .max()
                .orElseThrow(EmployeeNotFoundException :: new);
    }

    public double getAverageSalary(){
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .average()
                .orElseThrow(EmployeeNotFoundException :: new);
    }

    public Collection<Employee> getHighSalaryEmployees(){
        Double averageSalary = getAverageSalary();

            return employees.values().stream()
                    .filter(e-> e.getSalary() >= averageSalary)
                    .collect(Collectors.toList());

    }

    public void deleteEmployee(int id){
        employees.remove(id);
    }
}
