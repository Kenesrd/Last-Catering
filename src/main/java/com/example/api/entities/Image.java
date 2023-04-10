package com.example.api.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "images")
public class Image implements Serializable {
    @Id
    @SequenceGenerator(name = "imageSeq", sequenceName = "image_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imageSeq")
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String originalFileName;
    @NotNull
    private Long size;
    @NotBlank
    private String contentType;
    @NotNull
    @Lob
    private byte[] bytes;

}
