package com.example.demo.pet.app;

import com.example.demo.pet.domain.Pet;
import com.example.demo.pet.infra.PetRepository;
import com.example.demo.user.domain.User;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.security.SecurityUtils;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final EntityManager entityManager;

    public PetService(PetRepository petRepository, EntityManager entityManager) {
        this.petRepository = petRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public Pet createPet(String name, String type) {
        Long userId = SecurityUtils.currentUserId();

        User ownerRef = entityManager.getReference(User.class, userId);

        Pet pet = new Pet(name, type, ownerRef);
        return petRepository.save(pet);
    }

    @Transactional(readOnly = true)
    public Pet getMyPet(Long petId) {
        Long userId = SecurityUtils.currentUserId();
        return petRepository.findByIdAndOwnerId(petId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));
    }

    @Transactional(readOnly = true)
    public List<Pet> listMyPets() {
        Long userId = SecurityUtils.currentUserId();
        return petRepository.findByOwnerId(userId);
    }

    @Transactional
    public Pet updateMyPet(Long petId, String newName, String newType) {
        Long userId = SecurityUtils.currentUserId();

        Pet pet = petRepository.findByIdAndOwnerId(petId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));
        if (newName != null && !newName.isBlank()) {
            pet.setName(newName);
        }
        if (newType != null && !newType.isBlank()) {
            pet.setType(newType);
        }

        return pet;
    }

    @Transactional
    public void deleteMyPet(Long petId) {
        Long userId = SecurityUtils.currentUserId();

        int before = petRepository.existsByIdAndOwnerId(petId, userId) ? 1 : 0;
        if (before == 0) throw new IllegalArgumentException("Pet not found");

        petRepository.deleteByIdAndOwnerId(petId, userId);
    }
}
