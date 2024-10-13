package com.challenge.adoption.api.controller;

import com.challenge.adoption.common.param.AnimalRequestParam;
import com.challenge.adoption.common.param.AnimalUpdateStatusParam;
import com.challenge.adoption.common.pojo.ErrorValidationData;
import com.challenge.adoption.common.presenter.AnimalPresenter;
import com.challenge.adoption.service.AnimalService;
import com.challenge.adoption.type.AdoptionStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "animals")
public class AnimalController {

    private final AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping(value = "/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AnimalPresenter> findAnimal(@PathVariable Long id) {

        return animalService.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<AnimalPresenter>> findAllAnimals() {
        return ResponseEntity.ok(animalService.findAll());
    }

    @PostMapping(value = "create")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AnimalPresenter> createAnimal(@Valid @RequestBody AnimalRequestParam animal) {

        AnimalPresenter savedAnimal = animalService.save(animal);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedAnimal);

    }

    @PatchMapping("{id}/status")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> updateAnimalStatus(@PathVariable Long id, @RequestBody AnimalUpdateStatusParam status) {

        AdoptionStatus statusEnum = AdoptionStatus.getStatusFromString(status.getStatus());
        if(Objects.isNull(statusEnum)) {

            ErrorValidationData errorMessage = new ErrorValidationData();
            errorMessage.setField("status");
            errorMessage.setMessage(String.format("Status inválido. Opções válidas: %s", Arrays.toString(AdoptionStatus.values())));
            return ResponseEntity.badRequest().body(errorMessage);
        }
        return animalService.updateStatus(id, statusEnum).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
}
