package com.challenge.adoption.service;

import com.challenge.adoption.common.entity.Animal;
import com.challenge.adoption.common.param.AnimalRequestParam;
import com.challenge.adoption.common.presenter.AnimalPresenter;
import com.challenge.adoption.data.repository.AnimalRepository;
import com.challenge.adoption.type.AdoptionStatus;
import com.challenge.adoption.type.AnimalCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AnimalServiceTests  {

    @Mock
    AnimalRepository animalRepository;

    @InjectMocks
    AnimalService animalService;

    @Test
    public void when_save_animal_it_should_return_animal() {
        AnimalRequestParam requestParam = defaultAnimal();
        Animal animal = new Animal();
        animal.setBirthDate(LocalDate.now());
        animal.setName("Mock");
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);

        AnimalPresenter created = animalService.save(requestParam);
        assertNotNull(created);
        assertSame(created.getName(), requestParam.getName());
    }

    @Test
    void it_should_update_animal_status() {
        AnimalRequestParam requestParam = defaultAnimal();

        Animal animal = new Animal();
        animal.setBirthDate(LocalDate.now());
        animal.setName("Mock");
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);

        Optional<AnimalPresenter> created = animalService.updateStatus(1L, AdoptionStatus.ADOPTED);
        assertTrue(created.isPresent(), "AnimalPresenter should be present");
        assertNotSame(created.get().getStatus(), requestParam.getStatus());
    }

    private AnimalRequestParam defaultAnimal() {
        AnimalRequestParam requestParam = new AnimalRequestParam();
        requestParam.setName("Mock");
        requestParam.setCategory(AnimalCategory.DOG);
        requestParam.setDescription("Mock");
        requestParam.setBirthDate(LocalDate.now());
        requestParam.setUrlImage("Mock");
        requestParam.setStatus(AdoptionStatus.AVAILABLE);
        return requestParam;
    }
}
