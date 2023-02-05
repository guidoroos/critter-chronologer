package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.Exception.NotFoundException;
import com.udacity.jdnd.course3.critter.user.data.Employee;
import com.udacity.jdnd.course3.critter.user.data.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.data.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAvailableEmployeesForGivenDateAndSkills(LocalDate date, Set<EmployeeSkill> skills) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        List<Employee> employees = employeeRepository.findByDayOfWeekAndSkills(dayOfWeek, skills, skills.size());

        if (employees == null || employees.size() == 0) {
            throw new NotFoundException("no employees available with skills: " + skills + " on " + date);
        } else {
            return employees;
        }
    }

    public Employee setAvailableDaysForEmployee(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Employee employee = findEmployeeById(employeeId);

        employee.setDaysAvailable(daysAvailable);
        return employee;
    }

    public Employee findEmployeeById(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new NotFoundException("No employee found with id: " + employeeId);
        }
    }

    public EmployeeDTO dtoFromEmployee(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);

        return employeeDTO;
    }

    public Employee employeeFromDTO(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        return employee;
    }
}

