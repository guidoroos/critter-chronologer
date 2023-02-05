package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.Exception.NoContentException;
import com.udacity.jdnd.course3.critter.Exception.NotFoundException;
import com.udacity.jdnd.course3.critter.pet.data.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.schedule.data.Schedule;
import com.udacity.jdnd.course3.critter.schedule.data.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.data.Employee;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();

        if (schedules.size() == 0) {
            throw new NoContentException();
        } else {
            return schedules;
        }
    }

    public List<ScheduleDTO> findSchedulesByEmployee(Long employeeId) {
        Employee employee = employeeService.findEmployeeById(employeeId);

        Set<Schedule> schedules = scheduleRepository.findAllByEmployeesContaining(employee);

        if (schedules == null || schedules.size() == 0) {
            throw new NotFoundException("owner " + employeeId + " has no schedules registered");
        } else {
            return schedules
                    .stream()
                    .map(this::dtoFromSchedule)
                    .collect(Collectors.toList());
        }
    }

    public List<ScheduleDTO> findSchedulesByPet(Long petId) {
        Pet pet = petService.findPetById(petId);

        Set<Schedule> schedules = scheduleRepository.findAllByPetsContaining(pet);

        if (schedules == null || schedules.size() == 0) {
            throw new NotFoundException("no schedules found for" + petId);
        } else {
            return scheduleRepository.findAllByPetsContaining(pet)
                    .stream()
                    .map(this::dtoFromSchedule)
                    .collect(Collectors.toList());
        }
    }

    public List<ScheduleDTO> findSchedulesByCustomer(Long customerId) {
        Customer customer = customerService.findCustomerById(customerId);

        Set<Schedule> schedules = scheduleRepository.findByCustomer(customer);

        if (schedules == null || schedules.size() == 0) {
            throw new NotFoundException("no schedules found for customerId: " + customerId);
        } else {
            return schedules
                    .stream().map(this::dtoFromSchedule)
                    .collect(Collectors.toList());
        }
    }

    public ScheduleDTO dtoFromSchedule(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        if (schedule.getPets() != null &&
                schedule.getPets().size() != 0) {

            scheduleDTO.setPetIds(schedule.getPets()
                    .stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList())
            );
        }

        if (schedule.getEmployees() != null &&
                schedule.getEmployees().size() != 0) {

            scheduleDTO.setEmployeeIds(schedule.getEmployees()
                    .stream()
                    .map(Employee::getId)
                    .collect(Collectors.toList())
            );
        }

        return scheduleDTO;
    }

    public Schedule scheduleFromDTO(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();

        BeanUtils.copyProperties(scheduleDTO, schedule);

        if (scheduleDTO.getPetIds() != null &&
                scheduleDTO.getPetIds().size() != 0) {

            schedule.setPets(scheduleDTO.getPetIds()
                    .stream()
                    .map(petId -> petService.findPetById(petId))
                    .collect(Collectors.toList())
            );
        }

        if (scheduleDTO.getEmployeeIds() != null &&
                scheduleDTO.getEmployeeIds().size() != 0) {

            schedule.setEmployees(scheduleDTO.getEmployeeIds()
                    .stream()
                    .map(employeeId -> employeeService.findEmployeeById(employeeId))
                    .collect(Collectors.toList())
            );

        }

        return schedule;
    }

}
