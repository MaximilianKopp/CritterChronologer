package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public UserController(CustomerService customerService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerService.save(convertDTOtoEntity(customerDTO));
        return convertEntityToDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerService.getAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customers.forEach(customer -> customerDTOS.add(convertEntityToDTO(customer)));
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Customer customer = customerService.getOwnerByPet(petId);
        return convertEntityToDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.save(convertDTOtoEntity(employeeDTO));
        return convertEntityToDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getById(employeeId);
        return convertEntityToDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable,employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        EmployeeRequest request = convertDTOtoEntity(employeeDTO);
        List<Employee> employees = employeeService.findEmployeesForService(request);
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employees.forEach(employee -> employeeDTOS.add(convertEntityToDTO(employee)));
        return employeeDTOS;
    }

    public static Customer convertDTOtoEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        if (customerDTO.getPetIds() != null && customerDTO.getPetIds().size() > 0) {
            List<Pet> pets = new ArrayList<>();
            customerDTO.getPetIds().forEach(petId -> {
                Pet pet = new Pet();
                pet.setId(petId);
                pets.add(pet);
            });
            customer.setPets(pets);
        }
        return customer;
    }

    public static CustomerDTO convertEntityToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if (customer.getPets() != null && customer.getPets().size() > 0) {
            List<Long> petIds = new ArrayList<>();
            customer.getPets().forEach(pet -> petIds.add(pet.getId()));
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    public static Employee convertDTOtoEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    public static EmployeeDTO convertEntityToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    public static EmployeeRequest convertDTOtoEntity(EmployeeRequestDTO requestDTO) {
        EmployeeRequest request = new EmployeeRequest();
        BeanUtils.copyProperties(requestDTO, request);
        return request;
    }
}
