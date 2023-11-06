package de.projekt.bodyfat.service.impl;

import de.projekt.bodyfat.domain.Koerperfett;
import de.projekt.bodyfat.repository.KoerperfettRepository;
import de.projekt.bodyfat.service.KoerperfettService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Koerperfett}.
 */
@Service
@Transactional
public class KoerperfettServiceImpl implements KoerperfettService {

    private final Logger log = LoggerFactory.getLogger(KoerperfettServiceImpl.class);

    private final KoerperfettRepository koerperfettRepository;

    public KoerperfettServiceImpl(KoerperfettRepository koerperfettRepository) {
        this.koerperfettRepository = koerperfettRepository;
    }

    @Override
    public Koerperfett save(Koerperfett koerperfett) {
        log.debug("Request to save Koerperfett : {}", koerperfett);
        return koerperfettRepository.save(koerperfett);
    }

    @Override
    public Koerperfett update(Koerperfett koerperfett) {
        log.debug("Request to update Koerperfett : {}", koerperfett);
        return koerperfettRepository.save(koerperfett);
    }

    @Override
    public Optional<Koerperfett> partialUpdate(Koerperfett koerperfett) {
        log.debug("Request to partially update Koerperfett : {}", koerperfett);

        return koerperfettRepository
            .findById(koerperfett.getId())
            .map(existingKoerperfett -> {
                if (koerperfett.getPrivatoderfirma() != null) {
                    existingKoerperfett.setPrivatoderfirma(koerperfett.getPrivatoderfirma());
                }
                if (koerperfett.getKoerpergroesse() != null) {
                    existingKoerperfett.setKoerpergroesse(koerperfett.getKoerpergroesse());
                }
                if (koerperfett.getNackenumfang() != null) {
                    existingKoerperfett.setNackenumfang(koerperfett.getNackenumfang());
                }
                if (koerperfett.getBauchumfang() != null) {
                    existingKoerperfett.setBauchumfang(koerperfett.getBauchumfang());
                }
                if (koerperfett.getHueftumfang() != null) {
                    existingKoerperfett.setHueftumfang(koerperfett.getHueftumfang());
                }
                if (koerperfett.getGeschlecht() != null) {
                    existingKoerperfett.setGeschlecht(koerperfett.getGeschlecht());
                }
                if (koerperfett.getAge() != null) {
                    existingKoerperfett.setAge(koerperfett.getAge());
                }
                if (koerperfett.getKoerperfettanteil() != null) {
                    existingKoerperfett.setKoerperfettanteil(koerperfett.getKoerperfettanteil());
                }
                if (koerperfett.getDatumundZeit() != null) {
                    existingKoerperfett.setDatumundZeit(koerperfett.getDatumundZeit());
                }
                if (koerperfett.getUrl() != null) {
                    existingKoerperfett.setUrl(koerperfett.getUrl());
                }
                if (koerperfett.getSuccess() != null) {
                    existingKoerperfett.setSuccess(koerperfett.getSuccess());
                }
                if (koerperfett.getErrorMessage() != null) {
                    existingKoerperfett.setErrorMessage(koerperfett.getErrorMessage());
                }

                return existingKoerperfett;
            })
            .map(koerperfettRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Koerperfett> findAll(Pageable pageable) {
        log.debug("Request to get all Koerperfetts");
        return koerperfettRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Koerperfett> findOne(Long id) {
        log.debug("Request to get Koerperfett : {}", id);
        return koerperfettRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Koerperfett : {}", id);
        koerperfettRepository.deleteById(id);
    }
}
