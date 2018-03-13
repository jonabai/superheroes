package com.jonabai.task.superhero.services.impl;

import com.jonabai.task.superhero.api.v1.mapper.HeroMapper;
import com.jonabai.task.superhero.api.v1.model.HeroDTO;
import com.jonabai.task.superhero.domain.Hero;
import com.jonabai.task.superhero.repositories.HeroRepository;
import com.jonabai.task.superhero.services.HeroService;
import com.jonabai.task.superhero.services.exceptions.ResourceNotFoundException;
import com.jonabai.task.superhero.utils.HeroTestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

/**
 * HeroServiceImpl IT
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class HeroServiceImplIT {

    @Autowired
    private HeroRepository heroRepository;

    private HeroService heroService;

    private Long hero2Id;

    @Before
    public void setUp() throws Exception {
        System.out.println("Loading Hero Data");
        System.out.println(heroRepository.findAll().size());

        //setup data for testing
        Hero hero1 = HeroTestUtils.getHeroTestInstance(1);
        Hero hero2 = HeroTestUtils.getHeroTestInstance(2);
        Hero hero3 = HeroTestUtils.getHeroTestInstance(3);

        heroRepository.save(hero1);
        hero2Id = heroRepository.save(hero2).getId();
        heroRepository.save(hero3);

        heroService = new HeroServiceImpl(HeroMapper.INSTANCE, heroRepository);
    }

    @Test
    public void createNewHeroOk() throws Exception {
        HeroDTO heroDTO1 = HeroTestUtils.getHeroDTOTestInstance(4);
        heroDTO1.setId(null);
        HeroDTO heroDTO2 = heroService.getHeroById(hero2Id);
        heroDTO1.setAllies(Collections.singletonList(heroDTO2));

        HeroDTO savedHero = heroService.createNewHero(heroDTO1);
        Hero createdHero = heroRepository.findById(savedHero.getId()).get();

        assertNotNull(createdHero);

        Assert.assertEquals(savedHero.getId(), createdHero.getId());
        Assert.assertEquals(heroDTO1.getName(), createdHero.getName());
        Assert.assertEquals(heroDTO1.getPseudonym(), createdHero.getPseudonym());
        Assert.assertEquals(heroDTO1.getPublisher(), createdHero.getPublisher());
        Assert.assertEquals(1, createdHero.getSkills().size());
        Assert.assertEquals(heroDTO1.getSkills().get(0), createdHero.getSkills().get(0));
        Assert.assertEquals(1, createdHero.getAllies().size());
        Assert.assertEquals(heroDTO1.getAllies().get(0).getId(), createdHero.getAllies().get(0).getId());
    }

    @Test
    public void saveHeroByDTO() throws Exception {
        long id = getHeroIdValue();
        HeroDTO originalHero = heroService.getHeroById(id);
        HeroDTO heroAlly = heroService.getHeroById(hero2Id);
        HeroDTO heroDTONewValues = HeroTestUtils.getHeroDTOTestInstance(7);
        heroDTONewValues.setId(id);
        heroDTONewValues.setAllies(Collections.singletonList(heroAlly));

        heroService.saveHeroByDTO(id, heroDTONewValues);
        Hero updatedHero = heroRepository.findById(id).get();

        assertNotNull(updatedHero);
        Assert.assertEquals(originalHero.getId(), updatedHero.getId());
        Assert.assertEquals(heroDTONewValues.getName(), updatedHero.getName());
        Assert.assertEquals(heroDTONewValues.getPseudonym(), updatedHero.getPseudonym());
        Assert.assertEquals(heroDTONewValues.getPublisher(), updatedHero.getPublisher());
        Assert.assertEquals(heroDTONewValues.getSkills().size(), updatedHero.getSkills().size());
        Assert.assertEquals(heroDTONewValues.getSkills().get(0), updatedHero.getSkills().get(0));
        Assert.assertEquals(heroDTONewValues.getAllies().size(), updatedHero.getAllies().size());
        Assert.assertEquals(heroDTONewValues.getAllies().get(0).getId(), updatedHero.getAllies().get(0).getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteHeroById() throws Exception {
        Long id = 3L;
        HeroDTO heroDTO = heroService.getHeroById(id);
        assertNotNull(heroDTO);

        heroService.deleteHeroById(id);

        heroService.getHeroById(id);
    }

    private Long getHeroIdValue(){
        List<Hero> heroes = heroRepository.findAll();

        System.out.println("Heroes Found: " + heroes.size());

        //return first id
        return heroes.get(0).getId();
    }
}
