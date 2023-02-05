package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.Exception.NoContentException;
import com.udacity.jdnd.course3.critter.Exception.NotFoundException;
import com.udacity.jdnd.course3.critter.pet.data.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.data.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetService petService;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        if (customers.size() == 0) {
            throw new NoContentException();
        } else {
            return customers;
        }
    }

    public Customer findCustomerByPet(Long petId) {
        Pet pet = petService.findPetById(petId);

        Customer customer = customerRepository.findByPetsContaining(pet);

        if (customer != null) {
            return customer;
        } else {
            throw new NotFoundException("No owners found for pet with id: " + petId);
        }
    }

    public Customer findCustomerById(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new NotFoundException("No customer found with id: " + customerId);
        }
    }

    public CustomerDTO dtoFromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        if (customer.getPets() != null &&
                customer.getPets().size() != 0) {

            customerDTO.setPetIds(customer.getPets()
                    .stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList())
            );
        }

        return customerDTO;
    }

    public Customer customerFromDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();

        BeanUtils.copyProperties(customerDTO, customer);

        if (customerDTO.getPetIds() != null &&
                customerDTO.getPetIds().size() != 0) {

            customer.setPets(customerDTO.getPetIds()
                    .stream()
                    .map(petId -> petService.findPetById(petId))
                    .collect(Collectors.toList())
            );
        }

        return customer;
    }

}
