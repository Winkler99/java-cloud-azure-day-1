package com.booleanuk.simpleapi.companies;

import com.booleanuk.simpleapi.games.Game;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company")
@JsonIgnoreProperties({"games"})
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", orphanRemoval = true)
    private List<Game> games;

    public Company(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Company(int id) {
        this.id = id;
    }

}
