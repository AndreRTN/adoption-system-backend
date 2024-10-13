package com.challenge.adoption.common.presenter;


import com.challenge.adoption.type.AdoptionStatus;
import com.challenge.adoption.type.AnimalCategory;
import lombok.Data;


@Data
public class AnimalPresenter {

    private Long id;

    private String name;

    private String description;

    private String urlImage;

    private AnimalCategory category;

    private String birthDate;

    private Integer age;

    private AdoptionStatus status;
}
