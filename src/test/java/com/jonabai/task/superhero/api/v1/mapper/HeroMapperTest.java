package com.jonabai.task.superhero.api.v1.mapper;

import com.jonabai.task.superhero.api.v1.model.HeroDTO;
import com.jonabai.task.superhero.api.v1.model.HeroListDTO;
import com.jonabai.task.superhero.domain.Hero;
import com.jonabai.task.superhero.utils.HeroTestUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * HeroMapper Test
 */
public class HeroMapperTest {

    private HeroMapper heroMapper = HeroMapper.INSTANCE;

    @Test
    public void heroToHeroDTO() throws Exception {
        //given
        Hero hero = new Hero();
        hero.setId(1L);
        hero.setName("hero 1");
        hero.setPseudonym("h1");
        hero.setPublisher("publisher");
        hero.setSkills(Collections.singletonList("skill1"));
        hero.setAllies(new ArrayList<>());

        Hero hero2 = new Hero();
        hero2.setId(2L);
        hero2.setName("hero 2");
        hero2.setPseudonym("h2");
        hero2.setPublisher("publisher2");
        hero2.setSkills(Collections.singletonList("skill2"));
        hero2.setAllies(new ArrayList<>());
        hero.getAllies().add(hero2);

        //when
        HeroDTO heroDTO = heroMapper.heroToHeroDTO(hero);

        //then
        assertEquals(hero.getId(), heroDTO.getId());
        assertEquals(hero.getName(), heroDTO.getName());
        assertEquals(hero.getPseudonym(), heroDTO.getPseudonym());
        assertEquals(hero.getPublisher(), heroDTO.getPublisher());
        assertEquals(1, heroDTO.getSkills().size());
        assertEquals(hero.getSkills().get(0), heroDTO.getSkills().get(0));
        assertEquals(1, heroDTO.getAllies().size());
        assertEquals(hero.getAllies().get(0).getId(), heroDTO.getAllies().get(0).getId());
    }

    @Test
    public void heroDtoToHero() throws Exception {
        //given
        HeroDTO hero = new HeroDTO();
        hero.setId(1L);
        hero.setName("hero 1");
        hero.setPseudonym("h1");
        hero.setPublisher("publisher");
        hero.setSkills(Collections.singletonList("skill1"));
        hero.setAllies(new ArrayList<>());

        HeroDTO hero2 = new HeroDTO();
        hero2.setId(2L);
        hero2.setName("hero 2");
        hero2.setPseudonym("h2");
        hero2.setPublisher("publisher2");
        hero2.setSkills(Collections.singletonList("skill2"));
        hero2.setAllies(new ArrayList<>());
        hero.getAllies().add(hero2);

        //when
        Hero heroDTO = heroMapper.heroDtoToHero(hero);

        //then
        assertEquals(hero.getId(), heroDTO.getId());
        assertEquals(hero.getName(), heroDTO.getName());
        assertEquals(hero.getPseudonym(), heroDTO.getPseudonym());
        assertEquals(hero.getPublisher(), heroDTO.getPublisher());
        assertEquals(1, heroDTO.getSkills().size());
        assertEquals(hero.getSkills().get(0), heroDTO.getSkills().get(0));
        assertEquals(1, heroDTO.getAllies().size());
        assertEquals(hero.getAllies().get(0).getId(), heroDTO.getAllies().get(0).getId());
    }

    @Test
    public void heroDtoListToHeroList() throws Exception {
        //given
        HeroDTO hero = new HeroDTO();
        hero.setId(1L);
        hero.setName("hero 1");
        hero.setPseudonym("h1");
        hero.setPublisher("publisher");
        hero.setSkills(Collections.singletonList("skill1"));
        hero.setAllies(new ArrayList<>());

        HeroDTO hero2 = new HeroDTO();
        hero2.setId(2L);
        hero2.setName("hero 2");
        hero2.setPseudonym("h2");
        hero2.setPublisher("publisher2");
        hero2.setSkills(Collections.singletonList("skill2"));
        hero2.setAllies(new ArrayList<>());

        HeroListDTO heroListDTO = new HeroListDTO(Arrays.asList(hero, hero2));

        List<Hero> heroes = heroMapper.heroDtoListToHeroList(heroListDTO.getHeroes());

        //then
        assertEquals(2, heroes.size());
        assertEquals(hero.getId(), heroes.get(0).getId());
        assertEquals(hero2.getId(), heroes.get(1).getId());
    }

    @Test
    public void cleanChildLists() throws Exception {
        Hero hero1 = HeroTestUtils.getHeroTestInstance(1);
        Hero hero2 = HeroTestUtils.getHeroTestInstance(1);
        hero1.setAllies(Collections.singletonList(hero2));
        hero2.setAllies(Collections.singletonList(hero1));

        heroMapper.cleanChildLists(Arrays.asList(hero1, hero2));

        assertEquals(null, hero1.getSkills());
        assertEquals(null, hero2.getSkills());
        assertEquals(null, hero1.getAllies());
        assertEquals(null, hero2.getAllies());
    }
}
