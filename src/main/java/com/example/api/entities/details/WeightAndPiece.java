package com.example.api.entities.details;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



public enum WeightAndPiece {
    WEIGHT("Вес"),
    AMOUNT("Количество/штук"),
    LITER("Литр"),
    KILOGRAM("кг"),
    GRAMM("гр");

    private String name;

    WeightAndPiece(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
