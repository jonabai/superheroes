package com.jonabai.task.superhero.controllers.v1;

import com.jonabai.task.superhero.api.v1.model.HeroDTO;
import com.jonabai.task.superhero.api.v1.model.HeroListDTO;
import com.jonabai.task.superhero.services.HeroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing superheroes.
 */
@RestController
@RequestMapping(HeroController.BASE_URL)
public class HeroController {
    private final Logger log = LoggerFactory.getLogger(HeroController.class);
    static final String BASE_URL = "/api/v1/heroes";

    private final HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    /**
     * GET  /heroes : get all the heroes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of heroes in body
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public HeroListDTO getListOfHeroes(){
        log.debug("REST request to get all Heroes");
        return new HeroListDTO(heroService.getAllHeroes());
    }

    /**
     * GET  /heroes/:id : get the "id" hero.
     *
     * @param id the id of the hero to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hero, or with status 404 (Not Found)
     */
    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public HeroDTO getHeroById(@PathVariable Long id){
        log.debug("REST request to get Hero {}", id);
        return heroService.getHeroById(id);
    }

    /**
     * POST  /heroes : Create a new hero.
     *
     * @param heroDTO the hero to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hero, or with status 40X if error occurred
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HeroDTO createNewHero(@RequestBody HeroDTO heroDTO){
        log.debug("REST request to create Hero : {}", heroDTO);
        return heroService.createNewHero(heroDTO);
    }

    /**
     * PUT  /heroes : Updates an existing hero.
     *
     * @param id the id hero to be updated
     * @param heroDTO the hero to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hero,
     * or with status 400 (Bad Request) if the hero is not valid,
     * or with status 500 (Internal Server Error) if the hero couldn't be updated
     */
    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public HeroDTO updateHero(@PathVariable Long id, @RequestBody HeroDTO heroDTO){
        log.debug("REST request to update Hero {}: {}", id, heroDTO);
        return heroService.saveHeroByDTO(id, heroDTO);
    }

    /**
     * PATCH  /heroes : Updates the non null fields for an existing hero.
     *
     * @param id the id hero to be updated
     * @param heroDTO the hero to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hero,
     * or with status 400 (Bad Request) if the hero is not valid,
     * or with status 500 (Internal Server Error) if the hero couldn't be updated
     */
    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public HeroDTO patchHero(@PathVariable Long id, @RequestBody HeroDTO heroDTO){
        log.debug("REST request to patch Hero {}: {}", id, heroDTO);
        return heroService.patchHero(id, heroDTO);
    }

    /**
     * DELETE  /heroes/:id : delete the "id" hero.
     *
     * @param id the id of the hero to delete
     */
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteHero(@PathVariable Long id){
        log.debug("REST request to delete Hero {}", id);
        heroService.deleteHeroById(id);
    }
}
