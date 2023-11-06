package de.projekt.bodyfat.repository;

import de.projekt.bodyfat.domain.KoerperfettAvarage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the KoerperfettAvarage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KoerperfettAvarageRepository
    extends JpaRepository<KoerperfettAvarage, Long>, JpaSpecificationExecutor<KoerperfettAvarage> {}
