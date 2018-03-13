package com.jonabai.task.superhero.services;

import com.jonabai.task.superhero.api.v1.model.HeroDTO;

import java.util.List;

/**
 * Service Interface for managing Hero.
 */
public interface HeroService {

    /**
     * Get all the heroes.
     *
     * @return the list of heroes
     */
    List<HeroDTO> getAllHeroes();

    /**
     * Get the "id" hero.
     *
     * @param id the id of the hero
     * @return the hero
     */
    HeroDTO getHeroById(Long id);

    /**
     * Create a new hero.
     *
     * @param heroDTO the hero to create
     * @return the persisted hero
     */
    HeroDTO createNewHero(HeroDTO heroDTO);

    /**
     * Update a hero.
     *
     * @param heroDTO the hero to update
     * @return the persisted hero
     */
    HeroDTO saveHeroByDTO(Long id, HeroDTO heroDTO);

    /**
     * Patch a hero.
     * Only the non null fields will be persisted
     *
     * @param heroDTO the hero to patch
     * @return the persisted hero
     */
    HeroDTO patchHero(Long id, HeroDTO heroDTO);

    /**
     * Delete the "id" hero.
     *
     * @param id the id of the entity
     */
    void deleteHeroById(Long id);

}
