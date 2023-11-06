package de.projekt.bodyfat.repository;

import de.projekt.bodyfat.domain.Koerperfett;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Koerperfett entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KoerperfettRepository extends JpaRepository<Koerperfett, Long>, JpaSpecificationExecutor<Koerperfett> {}
