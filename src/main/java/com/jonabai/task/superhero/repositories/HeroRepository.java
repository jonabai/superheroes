package com.jonabai.task.superhero.repositories;

import com.jonabai.task.superhero.domain.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repositories for the Hero entity.
 */
@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {

}
