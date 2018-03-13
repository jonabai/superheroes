package com.jonabai.task.superhero.api.v1.mapper;

import com.jonabai.task.superhero.api.v1.model.HeroDTO;
import com.jonabai.task.superhero.domain.Hero;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Hero and its DTO called HeroDTO.
 */
@Mapper
public abstract class HeroMapper {

    public static final HeroMapper INSTANCE = Mappers.getMapper(HeroMapper.class);

    public abstract HeroDTO heroToHeroDTO(Hero hero);

    public abstract Hero heroDtoToHero(HeroDTO heroDTO);

    public abstract List<Hero> heroDtoListToHeroList(List<HeroDTO> heroDTOs);

    /**
     * Needed to avoid send the complete nested object list to the front end
     */
    @BeforeMapping
    void cleanChildLists(List<Hero> heroes) {
        if(heroes == null)
            return;
        heroes.forEach(h -> {
            h.setAllies(null);
            h.setSkills(null);
        });
    }
}
