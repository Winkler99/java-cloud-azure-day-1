package com.booleanuk.simpleapi.budgets;

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
@Table(name = "budgets")
@JsonIgnoreProperties({"games"})
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String grade;

    @Column
    private int budget;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budget", orphanRemoval = true)
    private List<Game> games;

    public Budget(String grade, int budget) {
        this.grade = grade;
        this.budget = budget;

    }

    public Budget(int id) {
        this.id = id;
    }
}
