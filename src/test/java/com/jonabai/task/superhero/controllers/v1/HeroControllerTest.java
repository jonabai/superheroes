package com.jonabai.task.superhero.controllers.v1;

import com.jonabai.task.superhero.api.v1.model.HeroDTO;
import com.jonabai.task.superhero.controllers.AbstractRestControllerTest;
import com.jonabai.task.superhero.controllers.RestResponseEntityExceptionHandler;
import com.jonabai.task.superhero.services.HeroService;
import com.jonabai.task.superhero.services.exceptions.ResourceNotFoundException;
import com.jonabai.task.superhero.utils.HeroTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * HeroController Unit Test
 */
public class HeroControllerTest extends AbstractRestControllerTest {

    @Mock
    private HeroService heroService;

    @InjectMocks
    private HeroController heroController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(heroController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testListHeroes() throws Exception {

        //given
        HeroDTO hero1 = HeroTestUtils.getHeroDTOTestInstance(1);
        HeroDTO hero2 = HeroTestUtils.getHeroDTOTestInstance(2);

        when(heroService.getAllHeroes()).thenReturn(Arrays.asList(hero1, hero2));

        mockMvc.perform(get(HeroController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.heroes", hasSize(2)));
    }

    @Test
    public void testGetHeroById() throws Exception {

        //given
        HeroDTO hero1 = HeroTestUtils.getHeroDTOTestInstance(3);

        when(heroService.getHeroById(anyLong())).thenReturn(hero1);

        //when
        mockMvc.perform(get(HeroController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(hero1.getName())))
                .andExpect(jsonPath("$.pseudonym", equalTo(hero1.getPseudonym())))
                .andExpect(jsonPath("$.publisher", equalTo(hero1.getPublisher())));
    }

    @Test
    public void createNewHero() throws Exception {
        //given
        HeroDTO hero = HeroTestUtils.getHeroDTOTestInstance(4);

        HeroDTO returnDTO = HeroTestUtils.getHeroDTOTestInstance(4);

        when(heroService.createNewHero(hero)).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(post(HeroController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(hero)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(returnDTO.getName())))
                .andExpect(jsonPath("$.pseudonym", equalTo(returnDTO.getPseudonym())))
                .andExpect(jsonPath("$.publisher", equalTo(returnDTO.getPublisher())));
    }

    @Test
    public void testUpdateHero() throws Exception {
        //given
        HeroDTO hero = HeroTestUtils.getHeroDTOTestInstance(5);
        HeroDTO returnDTO = HeroTestUtils.getHeroDTOTestInstance(5);

        when(heroService.saveHeroByDTO(anyLong(), any(HeroDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put(HeroController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(hero)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(returnDTO.getName())))
                .andExpect(jsonPath("$.pseudonym", equalTo(returnDTO.getPseudonym())))
                .andExpect(jsonPath("$.publisher", equalTo(returnDTO.getPublisher())));
    }

    @Test
    public void testPatchHero() throws Exception {

        //given
        HeroDTO hero = HeroTestUtils.getHeroDTOTestInstance(6);
        HeroDTO returnDTO = HeroTestUtils.getHeroDTOTestInstance(6);

        when(heroService.patchHero(anyLong(), any(HeroDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(HeroController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(hero)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(returnDTO.getName())))
                .andExpect(jsonPath("$.pseudonym", equalTo(returnDTO.getPseudonym())))
                .andExpect(jsonPath("$.publisher", equalTo(returnDTO.getPublisher())));
    }

    @Test
    public void testDeleteHero() throws Exception {

        mockMvc.perform(delete(HeroController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(heroService).deleteHeroById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(heroService.getHeroById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(HeroController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
