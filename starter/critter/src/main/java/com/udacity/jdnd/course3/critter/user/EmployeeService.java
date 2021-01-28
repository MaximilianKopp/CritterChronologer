package com.udacity.jdnd.course3.critter.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getById(Long id) {
        return employeeRepository.getOne(id);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long id) {
        Employee employee = getById(id);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(EmployeeRequest request) {
        Set<EmployeeSkill> skills = request.getSkills();
        DayOfWeek date = request.getDate().getDayOfWeek();

        List<Employee> allEmployees = employeeRepository.findAll();
        List<Employee> availableEmployees = new ArrayList<>();
        for (Employee employee : allEmployees) {
            if (employee.getDaysAvailable().contains(date) && employee.getSkills().containsAll(skills)) {
                availableEmployees.add(employee);
            }
        }
        return availableEmployees;
    }
}
