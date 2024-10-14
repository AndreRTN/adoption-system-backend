package com.challenge.adoption.controller;

import com.challenge.adoption.api.controller.AnimalController;
import com.challenge.adoption.common.entity.Animal;
import com.challenge.adoption.common.param.AnimalRequestParam;
import com.challenge.adoption.common.param.AnimalUpdateStatusParam;
import com.challenge.adoption.common.presenter.AnimalPresenter;
import com.challenge.adoption.service.AnimalService;
import com.challenge.adoption.type.AdoptionStatus;
import com.challenge.adoption.type.AnimalCategory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@Import(AnimalController.class)
@WebMvcTest(AnimalService.class)
class AnimalControllerTests {

    @MockBean
    AnimalService animalService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;


    @Test
    void it_should_return_created_animal() throws Exception {
        mapper.registerModule(new JavaTimeModule());
        AnimalRequestParam requestParam = defaultAnimal();
        AnimalPresenter presenter = new AnimalPresenter();
        presenter.setName(requestParam.getName());
        Mockito.when(animalService.save(any(AnimalRequestParam.class))).thenReturn(presenter);

        mockMvc.perform(MockMvcRequestBuilders.post("/animals/create")
                        .content(mapper.writeValueAsString(requestParam))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(requestParam.getName()));
    }

    @Test
    void it_should_return_bad_request_when_missing_params() throws Exception {
        mapper.registerModule(new JavaTimeModule());
        AnimalRequestParam requestParam = defaultAnimal();
        requestParam.setName(null);
        AnimalPresenter presenter = new AnimalPresenter();
        Mockito.when(animalService.save(any(AnimalRequestParam.class))).thenReturn(presenter);

        mockMvc.perform(MockMvcRequestBuilders.post("/animals/create")
                        .content(mapper.writeValueAsString(requestParam))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void it_should_return_not_found_animal() throws Exception {
        mapper.registerModule(new JavaTimeModule());
        AnimalRequestParam requestParam = defaultAnimal();
        requestParam.setName(null);
        Mockito.when(animalService.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/animals/1")
                        .content(mapper.writeValueAsString(requestParam))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void it_should_return_found_animal() throws Exception {
        mapper.registerModule(new JavaTimeModule());
        Animal animal = new Animal();
        animal.setBirthDate(LocalDate.now());
        animal.setName("Mock");
        AnimalPresenter presenter = new AnimalPresenter();
        presenter.setName(animal.getName());
        Mockito.when(animalService.findById(1L)).thenReturn(Optional.of(presenter));

        mockMvc.perform(MockMvcRequestBuilders.get("/animals/{id}", 1L)

                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void it_should_return_updated_animal() throws Exception {
        mapper.registerModule(new JavaTimeModule());
        AnimalUpdateStatusParam requestParam = new AnimalUpdateStatusParam();
        requestParam.setStatus(AdoptionStatus.ADOPTED.name());

        AnimalPresenter presenter = new AnimalPresenter();
        Mockito.when(animalService.updateStatus(1L, AdoptionStatus.ADOPTED)).thenReturn(Optional.of(presenter));

        mockMvc.perform(MockMvcRequestBuilders.patch("/animals/{id}/status", 1L)
                        .content(mapper.writeValueAsString(requestParam))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void it_should_return_bad_request_when_updated_animal_with_invalid_status() throws Exception {
        mapper.registerModule(new JavaTimeModule());
        AnimalUpdateStatusParam requestParam = new AnimalUpdateStatusParam();
        requestParam.setStatus("Mock");

        Mockito.when(animalService.updateStatus(1L, AdoptionStatus.ADOPTED)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.patch("/animals/{id}/status", 1L)
                        .content(mapper.writeValueAsString(requestParam))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

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
