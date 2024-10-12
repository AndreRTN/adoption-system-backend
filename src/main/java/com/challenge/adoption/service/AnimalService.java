package com.challenge.adoption.service;


import com.challenge.adoption.common.entity.Animal;
import com.challenge.adoption.common.param.AnimalRequestParam;
import com.challenge.adoption.common.presenter.AnimalPresenter;
import com.challenge.adoption.data.repository.AnimalRepository;
import com.challenge.adoption.type.AdoptionStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {


    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<AnimalPresenter> findAll() {
        return animalRepository.findAll().stream().map(this::convertToPresenter).toList();
    }

    public Optional<AnimalPresenter> findById(Long id) {
        return animalRepository.findById(id).map(this::convertToPresenter);
    }

    public AnimalPresenter save(AnimalRequestParam requestParam) {
        Animal animal = new Animal();
        int birthYear = requestParam.getBirthDate().getYear();
        int currentYear = java.time.LocalDate.now().getYear();
        int age = currentYear - birthYear;
        animal.setAge(age);

        BeanUtils.copyProperties(requestParam, animal);
        return convertToPresenter(animalRepository.save(animal));
    }

    public Optional<AnimalPresenter> updateStatus(Long id, AdoptionStatus status) {
        Optional<Animal> animal = animalRepository.findById(id);
        if (animal.isEmpty()) return Optional.empty();

        animal.get().setStatus(status);
        animalRepository.save(animal.get());
        return Optional.of(convertToPresenter(animal.get()));
    }

    private AnimalPresenter convertToPresenter(Animal animal) {
        AnimalPresenter presenter = new AnimalPresenter();
        BeanUtils.copyProperties(animal, presenter);
        return presenter;
    }
}
