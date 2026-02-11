package com.example.demo.pet.api;

import com.example.demo.pet.domain.Pet;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.demo.pet.app.PetService;

import com.example.demo.pet.api.dto.CreatePetRequest;
import com.example.demo.pet.api.dto.CreatePetResponce;
import com.example.demo.pet.api.dto.UpdatePetRequest;


@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    private CreatePetResponce toResponse(Pet pet) {
        return new CreatePetResponce(
            pet.getId(),
            pet.getName(),
            pet.getType(),
            pet.getOwnerId()
        );
    }


    @PostMapping
    public CreatePetResponce create(@RequestBody CreatePetRequest req) {
        return toResponse(petService.createPet(req.name(), req.type()));
    }

    @GetMapping
    public List<CreatePetResponce> listMine() {
        return petService.listMyPets()
        .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public CreatePetResponce get(@PathVariable Long id) {
        return toResponse(petService.getMyPet(id));
    }

    @PutMapping("/{id}")
    public CreatePetResponce update(@PathVariable Long id, @RequestBody UpdatePetRequest req) {
        return toResponse(petService.updateMyPet(id, req.name(), req.type()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        petService.deleteMyPet(id);
    }
}
