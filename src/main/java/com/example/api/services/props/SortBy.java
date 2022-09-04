package com.example.api.services.props;

import lombok.Data;
import org.springframework.context.annotation.Bean;

import javax.persistence.Enumerated;
import javax.persistence.MapKeyEnumerated;


public enum SortBy {
    IDDESC,
    IDASC,
    TITLEDESC,
    TITLEASC,
    CREATEDATEDESC,
    CREATEDATEASC,
    PRICEDESC,
    PRICEASC;

    SortBy() {
    }
}
