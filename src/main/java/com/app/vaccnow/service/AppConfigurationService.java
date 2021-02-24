package com.app.vaccnow.service;

import com.app.vaccnow.service.dto.AppConfigurationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.app.vaccnow.domain.AppConfiguration}.
 */
public interface AppConfigurationService {

    /**
     * Save a appConfiguration.
     *
     * @param appConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    AppConfigurationDTO save(AppConfigurationDTO appConfigurationDTO);

    /**
     * Get all the appConfigurations.
     *
     * @return the list of entities.
     */
    List<AppConfigurationDTO> findAll();


    /**
     * Get the "id" appConfiguration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppConfigurationDTO> findOne(String id);

    /**
     * Delete the "id" appConfiguration.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
