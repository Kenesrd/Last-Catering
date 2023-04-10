package com.example.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@EqualsAndHashCode
public class User implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "userSeq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Cart cart;

}
