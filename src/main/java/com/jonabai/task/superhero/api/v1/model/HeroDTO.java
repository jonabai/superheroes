package com.jonabai.task.superhero.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A hero DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeroDTO {
    private Long id;
    private String name;
    private String pseudonym;
    private String publisher;
    private List<String> skills;
    private List<HeroDTO> allies;
}
