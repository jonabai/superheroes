package com.jonabai.task.superhero.utils;

import com.jonabai.task.superhero.api.v1.model.HeroDTO;
import com.jonabai.task.superhero.domain.Hero;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Abstract class with useful methods to manage hero entities and DTOs
 */
public abstract class HeroTestUtils {

    public static Hero getHeroTestInstance(int seed) {
        Hero hero = new Hero();
        hero.setId((long)seed);
        hero.setName("hero " + seed);
        hero.setPseudonym("h" + seed);
        hero.setPublisher("publisher" + seed);
        hero.setSkills(Collections.singletonList("skill" + seed));
        hero.setAllies(new ArrayList<>());

        return hero;
    }

    public static HeroDTO getHeroDTOTestInstance(int seed) {
        HeroDTO hero = new HeroDTO();
        hero.setId((long)seed);
        hero.setName("hero " + seed);
        hero.setPseudonym("h" + seed);
        hero.setPublisher("publisher" + seed);
        hero.setSkills(Collections.singletonList("skill" + seed));
        hero.setAllies(new ArrayList<>());

        return hero;
    }
}
