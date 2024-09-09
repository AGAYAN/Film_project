package com.example.Film_project_new.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "films")
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

}