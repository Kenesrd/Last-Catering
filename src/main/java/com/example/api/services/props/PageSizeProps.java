package com.example.api.services.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Component
@ConfigurationProperties("products.size")
@Data
@Validated
public class PageSizeProps {
    @Min(value = 1, message = "min size should be 5")
    @Max(value = 20, message = "Max size should be 20")
    private int pageSize = 2;

}
