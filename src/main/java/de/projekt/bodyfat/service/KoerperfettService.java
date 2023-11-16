package de.projekt.bodyfat.service;

import de.projekt.bodyfat.domain.Koerperfett;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Koerperfett}.
 */
public interface KoerperfettService {
    /**
     * Save a koerperfett.
     *
     * @param koerperfett the entity to save.
     * @return the persisted entity.
     */
    Koerperfett save(Koerperfett koerperfett);

    /**
     * Updates a koerperfett.
     *
     * @param koerperfett the entity to update.
     * @return the persisted entity.
     */
    Koerperfett update(Koerperfett koerperfett);

    /**
     * Partially updates a koerperfett.
     *
     * @param koerperfett the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Koerperfett> partialUpdate(Koerperfett koerperfett);

    /**
     * Get all the koerperfetts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Koerperfett> findAll(Pageable pageable);

    /**
     * Get the "id" koerperfett.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Koerperfett> findOne(Long id);

    /**
     * Delete the "id" koerperfett.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Koerperfett calculateKoerperfettanteil(Koerperfett koerperfett);

    Koerperfett saveKoerperfett(Koerperfett koerperfett);
}
