package com.challenge.adoption.common.entity;

import com.challenge.adoption.type.AdoptionStatus;
import com.challenge.adoption.type.AnimalCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "ANIMAL")
@Data
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String urlImage;

    @Enumerated(EnumType.STRING)

    private AnimalCategory category;

    private LocalDate birthDate;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private AdoptionStatus status;

}