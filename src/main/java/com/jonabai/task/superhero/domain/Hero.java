package com.jonabai.task.superhero.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * A Hero.
 */
@Data
@Entity
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String pseudonym;
    private String publisher;

    @ElementCollection(targetClass = String.class)
    private List<String> skills;

    @ManyToMany
    @JoinTable(name = "hero_allies",
            joinColumns = @JoinColumn(name="hero_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="ally_id", referencedColumnName="id"))
    private List<Hero> allies;

}
