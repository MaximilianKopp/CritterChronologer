package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;
    private final CustomerService customerService;

    public PetService(PetRepository petRepository, CustomerService customerService) {
        this.petRepository = petRepository;
        this.customerService = customerService;
    }

    public Pet save(Pet pet) {
        Pet savedPet = petRepository.save(pet);
        customerService.addPetToCustomer(savedPet, savedPet.getCustomer());
        return savedPet;
    }

    public Pet getById(Long id) {
        return petRepository.getOne(id);
    }

    public List<Pet> getAll() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(Long id) {
        return petRepository.findAllByCustomer_Id(id);
    }
}
