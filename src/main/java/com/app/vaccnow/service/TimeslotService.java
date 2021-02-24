package com.app.vaccnow.service;

import com.app.vaccnow.service.dto.TimeslotDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.app.vaccnow.domain.Timeslot}.
 */
public interface TimeslotService {

    /**
     * Save a timeslot.
     *
     * @param timeslotDTO the entity to save.
     * @return the persisted entity.
     */
    TimeslotDTO save(TimeslotDTO timeslotDTO);

    /**
     * Get all the timeslots.
     *
     * @return the list of entities.
     */
    List<TimeslotDTO> findAll();
    
    /**
     * Get all available timeslots.
     *
     * @return the list of entities.
     */
    List<TimeslotDTO> findAvailTimeslots();


    /**
     * Get the "id" timeslot.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TimeslotDTO> findOne(String id);

    /**
     * Delete the "id" timeslot.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
