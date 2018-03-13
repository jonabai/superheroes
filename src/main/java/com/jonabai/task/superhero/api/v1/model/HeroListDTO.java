package com.jonabai.task.superhero.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * A Hero List DTO
 */
@Data
@AllArgsConstructor
public class HeroListDTO {

    List<HeroDTO> heroes;

}
