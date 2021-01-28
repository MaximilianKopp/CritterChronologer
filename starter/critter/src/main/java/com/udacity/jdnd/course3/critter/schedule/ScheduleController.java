package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.save(convertDTOtoEntity(scheduleDTO));
        return convertEntityToDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAll();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        schedules.forEach(schedule -> scheduleDTOS.add(convertEntityToDTO(schedule)));
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        schedules.forEach(schedule -> scheduleDTOS.add(convertEntityToDTO(schedule)));
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        schedules.forEach(schedule -> scheduleDTOS.add(convertEntityToDTO(schedule)));
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        schedules.forEach(schedule -> scheduleDTOS.add(convertEntityToDTO(schedule)));
        return scheduleDTOS;
    }

    public static Schedule convertDTOtoEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        if (scheduleDTO.getEmployeeIds() != null && scheduleDTO.getEmployeeIds().size() > 0) {
            List<Employee> employees = new ArrayList<>();
            scheduleDTO.getEmployeeIds().forEach(id -> {
                Employee employee = new Employee();
                employee.setId(id);
                employees.add(employee);
            });
            schedule.setEmployees(employees);
        }

        if (scheduleDTO.getPetIds() != null && scheduleDTO.getPetIds().size() > 0) {
            List<Pet> pets = new ArrayList<>();
            scheduleDTO.getPetIds().forEach(id -> {
                Pet pet = new Pet();
                pet.setId(id);
                pets.add(pet);
            });
            schedule.setPets(pets);
        }
        return schedule;
    }

    public static ScheduleDTO convertEntityToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        if (schedule.getEmployees() != null && schedule.getEmployees().size() > 0) {
            List<Long> employeeIds = new ArrayList<>();
            schedule.getEmployees().forEach(employee -> employeeIds.add(employee.getId()));
            scheduleDTO.setEmployeeIds(employeeIds);
        }

        if (schedule.getPets() != null && schedule.getPets().size() > 0) {
            List<Long> petIds = new ArrayList<>();
            schedule.getPets().forEach(pet -> petIds.add(pet.getId()));
            scheduleDTO.setPetIds(petIds);
        }
        return scheduleDTO;
    }
}
