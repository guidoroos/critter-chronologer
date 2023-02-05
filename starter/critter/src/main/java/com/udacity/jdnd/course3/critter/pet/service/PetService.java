package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.Exception.NoContentException;
import com.udacity.jdnd.course3.critter.Exception.NotFoundException;
import com.udacity.jdnd.course3.critter.Exception.PetBadRequestException;
import com.udacity.jdnd.course3.critter.pet.data.Pet;
import com.udacity.jdnd.course3.critter.pet.data.PetDTO;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.data.Customer;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerService customerService;

    public Pet savePet(Pet pet) {
        if (pet.getOwner() != null) {
            Pet newPat = petRepository.save(pet);

            Customer owner = newPat.getOwner();
            List<Pet> pets = owner.getPets() == null ? new ArrayList<>() : owner.getPets();
            pets.add(newPat);
            owner.setPets(pets);

            return newPat;
        } else {
            throw new PetBadRequestException();
        }

    }

    public Pet findPetById(Long petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        if (pet.isPresent()) {
            return pet.get();
        } else {
            throw new NotFoundException("No pet found with id: " + petId);
        }
    }

    public List<Pet> findPetsByOwner(Long ownerId) {
        Customer owner = customerService.findCustomerById(ownerId);

        List<Pet> pets = owner.getPets();
        if (pets == null || pets.size() == 0) {
            throw new NotFoundException("owner " + ownerId + " has no pets registered");
        } else {
            return pets;
        }

    }

    public List<Pet> getAllPets() {
        List<Pet> pets = petRepository.findAll();

        if (pets.size() == 0) {
            throw new NoContentException();
        } else {
            return pets;
        }
    }

    public PetDTO dtoFromPet(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getOwner() != null) {
            petDTO.setOwnerId(pet.getOwner().getId());
        }

        return petDTO;
    }

    public Pet petFromDto(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);

        pet.setOwner(customerService.findCustomerById(petDTO.getOwnerId()));

        return pet;
    }

}

