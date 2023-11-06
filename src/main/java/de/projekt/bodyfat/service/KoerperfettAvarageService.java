package de.projekt.bodyfat.service;

import de.projekt.bodyfat.domain.KoerperfettAvarage;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link KoerperfettAvarage}.
 */
public interface KoerperfettAvarageService {
    /**
     * Save a koerperfettAvarage.
     *
     * @param koerperfettAvarage the entity to save.
     * @return the persisted entity.
     */
    KoerperfettAvarage save(KoerperfettAvarage koerperfettAvarage);

    /**
     * Updates a koerperfettAvarage.
     *
     * @param koerperfettAvarage the entity to update.
     * @return the persisted entity.
     */
    KoerperfettAvarage update(KoerperfettAvarage koerperfettAvarage);

    /**
     * Partially updates a koerperfettAvarage.
     *
     * @param koerperfettAvarage the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KoerperfettAvarage> partialUpdate(KoerperfettAvarage koerperfettAvarage);

    /**
     * Get all the koerperfettAvarages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KoerperfettAvarage> findAll(Pageable pageable);

    /**
     * Get the "id" koerperfettAvarage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KoerperfettAvarage> findOne(Long id);

    /**
     * Delete the "id" koerperfettAvarage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
