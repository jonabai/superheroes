package com.jonabai.task.superhero.services.impl;

import com.jonabai.task.superhero.api.v1.mapper.HeroMapper;
import com.jonabai.task.superhero.api.v1.model.HeroDTO;
import com.jonabai.task.superhero.domain.Hero;
import com.jonabai.task.superhero.repositories.HeroRepository;
import com.jonabai.task.superhero.services.HeroService;

import com.google.common.base.Preconditions;
import com.jonabai.task.superhero.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing super heroes.
 */
@Service
public class HeroServiceImpl implements HeroService {
    private final Logger log = LoggerFactory.getLogger(HeroServiceImpl.class);
    private static final String ID_PARAM_CANNOT_BE_NULL = "Id param cannot be null.";
    private static final String HERO_PARAM_CANNOT_BE_NULL = "Hero param cannot be null.";

    private final HeroMapper heroMapper;
    private final HeroRepository heroRepository;

    public HeroServiceImpl(HeroMapper heroMapper, HeroRepository heroRepository) {
        this.heroMapper = heroMapper;
        this.heroRepository = heroRepository;
    }

    @Override
    public List<HeroDTO> getAllHeroes() {
        log.debug("Request to get all Heroes");
        return heroRepository
                .findAll()
                .stream()
                .map(heroMapper::heroToHeroDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HeroDTO getHeroById(Long id) {
        log.debug("Request to get Hero : {}", id);
        Preconditions.checkNotNull(id, ID_PARAM_CANNOT_BE_NULL);

        return heroRepository.findById(id)
                .map(heroMapper::heroToHeroDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public HeroDTO createNewHero(HeroDTO heroDTO) {
        log.debug("Request to create Hero : {}", heroDTO);
        Preconditions.checkNotNull(heroDTO, HERO_PARAM_CANNOT_BE_NULL);
        Preconditions.checkArgument(heroDTO.getId() == null, "Hero Id param must be null.");
        validateAlliesList(heroDTO);

        return saveAndReturnDTO(heroMapper.heroDtoToHero(heroDTO));
    }

    private HeroDTO saveAndReturnDTO(Hero hero) {
        Hero savedHero = heroRepository.save(hero);

        return heroMapper.heroToHeroDTO(savedHero);
    }

    @Override
    public HeroDTO saveHeroByDTO(Long id, HeroDTO heroDTO) {
        log.debug("Request to save Hero : id={} {}", id, heroDTO);
        Preconditions.checkNotNull(heroDTO, HERO_PARAM_CANNOT_BE_NULL);
        Preconditions.checkNotNull(id, ID_PARAM_CANNOT_BE_NULL);
        validateAlliesList(heroDTO);

        Hero hero = heroMapper.heroDtoToHero(heroDTO);
        hero.setId(id);

        return saveAndReturnDTO(hero);
    }

    @Override
    public HeroDTO patchHero(Long id, HeroDTO heroDTO) {
        log.debug("Request to patch Hero : id={} {}", id, heroDTO);
        Preconditions.checkNotNull(id, ID_PARAM_CANNOT_BE_NULL);
        Preconditions.checkNotNull(heroDTO, HERO_PARAM_CANNOT_BE_NULL);

        return heroRepository.findById(id).map(hero -> {

            if(heroDTO.getName() != null){
                hero.setName(heroDTO.getName());
            }

            if(heroDTO.getPseudonym() != null){
                hero.setPseudonym(heroDTO.getPseudonym());
            }

            if(heroDTO.getPublisher() != null){
                hero.setPublisher(heroDTO.getPublisher());
            }

            if(heroDTO.getSkills() != null){
                hero.setSkills(heroDTO.getSkills());
            }

            if(heroDTO.getAllies() != null){
                validateAlliesList(heroDTO);
                hero.setAllies(heroMapper.heroDtoListToHeroList(heroDTO.getAllies()));
            }

            return heroMapper.heroToHeroDTO(heroRepository.save(hero));

        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteHeroById(Long id) {
        log.debug("Request to delete Hero : {}", id);
        Preconditions.checkNotNull(id, ID_PARAM_CANNOT_BE_NULL);

        heroRepository.deleteById(id);
    }

    private void validateAlliesList(HeroDTO heroDTO) {
        if(heroDTO.getAllies() == null || heroDTO.getAllies().isEmpty())
            return;
        for(HeroDTO ally : heroDTO.getAllies()) {
            if (ally.getId() == null)
                throw new ResourceNotFoundException("Hero ally Id is null");
            if (Objects.equals(ally.getId(), heroDTO.getId()))
                throw new IllegalArgumentException("Hero ally cannot be the same instance");
        }
    }
}
