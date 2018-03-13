package com.jonabai.task.superhero.services.impl;

import com.jonabai.task.superhero.api.v1.mapper.HeroMapper;
import com.jonabai.task.superhero.api.v1.model.HeroDTO;
import com.jonabai.task.superhero.domain.Hero;
import com.jonabai.task.superhero.repositories.HeroRepository;
import com.jonabai.task.superhero.services.HeroService;
import com.jonabai.task.superhero.utils.HeroTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * HeroServiceImpl Tests
 */
public class HeroServiceImplTest {

    @Mock
    private HeroRepository heroRepository;

    private HeroMapper heroMapper = HeroMapper.INSTANCE;

    private HeroService heroService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        heroService = new HeroServiceImpl(heroMapper, heroRepository);
    }

    @Test
    public void getAllHeroes() throws Exception {
        //given
        Hero hero1 = HeroTestUtils.getHeroTestInstance(1);
        Hero hero2 = HeroTestUtils.getHeroTestInstance(2);

        when(heroRepository.findAll()).thenReturn(Arrays.asList(hero1, hero2));

        //when
        List<HeroDTO> heroDTOS = heroService.getAllHeroes();

        //then
        assertEquals(2, heroDTOS.size());

    }

    @Test
    public void getHeroById() throws Exception {
        //given
        Hero hero1 = HeroTestUtils.getHeroTestInstance(1);
        Hero hero2 = HeroTestUtils.getHeroTestInstance(2);
        hero1.setAllies(Collections.singletonList(hero2));

        when(heroRepository.findById(anyLong())).thenReturn(java.util.Optional.of(hero1));

        //when
        HeroDTO heroDTO = heroService.getHeroById(1L);

        assertEquals(hero1.getId(), heroDTO.getId());
        assertEquals(hero1.getName(), heroDTO.getName());
        assertEquals(hero1.getPseudonym(), heroDTO.getPseudonym());
        assertEquals(hero1.getPublisher(), heroDTO.getPublisher());
        assertEquals(1, heroDTO.getSkills().size());
        assertEquals(hero1.getSkills().get(0), heroDTO.getSkills().get(0));
        assertEquals(1, heroDTO.getAllies().size());
        assertEquals(hero1.getAllies().get(0).getId(), heroDTO.getAllies().get(0).getId());
    }

    @Test
    public void createNewHero() throws Exception {

        //given
        HeroDTO heroDTO = HeroTestUtils.getHeroDTOTestInstance(3);
        HeroDTO heroDTO2 = HeroTestUtils.getHeroDTOTestInstance(4);
        heroDTO.setId(null);
        heroDTO.setAllies(Collections.singletonList(heroDTO2));

        Hero savedHero = HeroTestUtils.getHeroTestInstance(3);
        Hero savedHero2 = HeroTestUtils.getHeroTestInstance(4);
        savedHero.setAllies(Collections.singletonList(savedHero2));

        when(heroRepository.save(any(Hero.class))).thenReturn(savedHero);

        //when
        HeroDTO savedDto = heroService.createNewHero(heroDTO);

        //then
        assertEquals(savedHero.getId(), savedDto.getId());
        assertEquals(heroDTO.getName(), savedDto.getName());
        assertEquals(heroDTO.getPseudonym(), savedDto.getPseudonym());
        assertEquals(heroDTO.getPublisher(), savedDto.getPublisher());
        assertEquals(heroDTO.getSkills().size(), savedDto.getSkills().size());
        assertEquals(heroDTO.getSkills().get(0), savedDto.getSkills().get(0));
        assertEquals(heroDTO.getAllies().size(), savedDto.getAllies().size());
        assertEquals(heroDTO.getAllies().get(0).getId(), savedDto.getAllies().get(0).getId());
    }

    @Test
    public void saveHeroByDTO() throws Exception {

        //given
        HeroDTO heroDTO = HeroTestUtils.getHeroDTOTestInstance(5);
        HeroDTO heroDTO2 = HeroTestUtils.getHeroDTOTestInstance(6);
        heroDTO.setAllies(Collections.singletonList(heroDTO2));

        Hero savedHero = HeroTestUtils.getHeroTestInstance(5);
        Hero savedHero2 = HeroTestUtils.getHeroTestInstance(6);
        savedHero.setAllies(Collections.singletonList(savedHero2));

        when(heroRepository.save(any(Hero.class))).thenReturn(savedHero);

        //when
        HeroDTO savedDto = heroService.saveHeroByDTO(5L, heroDTO);

        //then
        assertEquals(heroDTO.getId(), savedDto.getId());
        assertEquals(heroDTO.getName(), savedDto.getName());
        assertEquals(heroDTO.getPseudonym(), savedDto.getPseudonym());
        assertEquals(heroDTO.getPublisher(), savedDto.getPublisher());
        assertEquals(heroDTO.getSkills().size(), savedDto.getSkills().size());
        assertEquals(heroDTO.getSkills().get(0), savedDto.getSkills().get(0));
        assertEquals(heroDTO.getAllies().size(), savedDto.getAllies().size());
        assertEquals(heroDTO.getAllies().get(0).getId(), savedDto.getAllies().get(0).getId());
    }

    @Test
    public void patchHeroByDTO() throws Exception {

        //given
        HeroDTO heroDTO = HeroTestUtils.getHeroDTOTestInstance(7);
        HeroDTO heroDTO2 = HeroTestUtils.getHeroDTOTestInstance(8);
        heroDTO.setAllies(Collections.singletonList(heroDTO2));

        Hero originalHero = HeroTestUtils.getHeroTestInstance(7);
        Hero savedHero = HeroTestUtils.getHeroTestInstance(7);
        Hero savedHero2 = HeroTestUtils.getHeroTestInstance(8);
        savedHero.setAllies(Collections.singletonList(savedHero2));

        when(heroRepository.findById(anyLong())).thenReturn(java.util.Optional.of(originalHero));
        when(heroRepository.save(any(Hero.class))).thenReturn(savedHero);

        //when
        HeroDTO savedDto = heroService.patchHero(7L, heroDTO);

        //then
        assertEquals(heroDTO.getId(), savedDto.getId());
        assertEquals(heroDTO.getName(), savedDto.getName());
        assertEquals(heroDTO.getPseudonym(), savedDto.getPseudonym());
        assertEquals(heroDTO.getPublisher(), savedDto.getPublisher());
        assertEquals(heroDTO.getSkills().size(), savedDto.getSkills().size());
        assertEquals(heroDTO.getSkills().get(0), savedDto.getSkills().get(0));
        assertEquals(heroDTO.getAllies().size(), savedDto.getAllies().size());
        assertEquals(heroDTO.getAllies().get(0).getId(), savedDto.getAllies().get(0).getId());
    }

    @Test
    public void deleteHeroById() throws Exception {

        Long id = 1L;

        heroService.deleteHeroById(id);

        verify(heroRepository, times(1)).deleteById(anyLong());
    }

}
