package com.booleanuk.simpleapi.games;

import com.booleanuk.simpleapi.budgets.Budget;
import com.booleanuk.simpleapi.companies.Company;
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
@Table(name = "games")
//@JsonIgnoreProperties({""})
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String rating;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name="budget")
    private Budget budget;

    @ManyToOne
    @JoinColumn(name="company")
    private Company company;

    public Game(String title, String rating, String description) {
        this.title = title;
        this.rating = rating;
        this.description = description;


    }

    public Game(int id) {
        this.id = id;
    }
}
