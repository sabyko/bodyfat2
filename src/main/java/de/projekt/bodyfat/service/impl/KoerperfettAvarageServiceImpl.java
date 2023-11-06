package de.projekt.bodyfat.service.impl;

import de.projekt.bodyfat.domain.KoerperfettAvarage;
import de.projekt.bodyfat.repository.KoerperfettAvarageRepository;
import de.projekt.bodyfat.service.KoerperfettAvarageService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KoerperfettAvarage}.
 */
@Service
@Transactional
public class KoerperfettAvarageServiceImpl implements KoerperfettAvarageService {

    private final Logger log = LoggerFactory.getLogger(KoerperfettAvarageServiceImpl.class);

    private final KoerperfettAvarageRepository koerperfettAvarageRepository;

    public KoerperfettAvarageServiceImpl(KoerperfettAvarageRepository koerperfettAvarageRepository) {
        this.koerperfettAvarageRepository = koerperfettAvarageRepository;
    }

    @Override
    public KoerperfettAvarage save(KoerperfettAvarage koerperfettAvarage) {
        log.debug("Request to save KoerperfettAvarage : {}", koerperfettAvarage);
        return koerperfettAvarageRepository.save(koerperfettAvarage);
    }

    @Override
    public KoerperfettAvarage update(KoerperfettAvarage koerperfettAvarage) {
        log.debug("Request to update KoerperfettAvarage : {}", koerperfettAvarage);
        return koerperfettAvarageRepository.save(koerperfettAvarage);
    }

    @Override
    public Optional<KoerperfettAvarage> partialUpdate(KoerperfettAvarage koerperfettAvarage) {
        log.debug("Request to partially update KoerperfettAvarage : {}", koerperfettAvarage);

        return koerperfettAvarageRepository
            .findById(koerperfettAvarage.getId())
            .map(existingKoerperfettAvarage -> {
                if (koerperfettAvarage.getGeschlecht() != null) {
                    existingKoerperfettAvarage.setGeschlecht(koerperfettAvarage.getGeschlecht());
                }
                if (koerperfettAvarage.getAlter() != null) {
                    existingKoerperfettAvarage.setAlter(koerperfettAvarage.getAlter());
                }
                if (koerperfettAvarage.getKoerperfettanteil() != null) {
                    existingKoerperfettAvarage.setKoerperfettanteil(koerperfettAvarage.getKoerperfettanteil());
                }
                if (koerperfettAvarage.getDatumundZeit() != null) {
                    existingKoerperfettAvarage.setDatumundZeit(koerperfettAvarage.getDatumundZeit());
                }

                return existingKoerperfettAvarage;
            })
            .map(koerperfettAvarageRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KoerperfettAvarage> findAll(Pageable pageable) {
        log.debug("Request to get all KoerperfettAvarages");
        return koerperfettAvarageRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KoerperfettAvarage> findOne(Long id) {
        log.debug("Request to get KoerperfettAvarage : {}", id);
        return koerperfettAvarageRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KoerperfettAvarage : {}", id);
        koerperfettAvarageRepository.deleteById(id);
    }
}
