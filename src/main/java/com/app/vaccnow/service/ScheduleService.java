package com.app.vaccnow.service;

import com.app.vaccnow.service.dto.ScheduleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.app.vaccnow.domain.Schedule}.
 */
public interface ScheduleService {

    /**
     * Save a schedule.
     *
     * @param scheduleDTO the entity to save.
     * @return the persisted entity.
     */
    ScheduleDTO save(ScheduleDTO scheduleDTO);

    /**
     * Get all the schedules.
     *
     * @return the list of entities.
     */
    List<ScheduleDTO> findAll();

    /**
     * Get all available schedules.
     *
     * @return the list of entities.
     */
    List<ScheduleDTO> findAllAvailableSchedules();
    
    /**
     * Get all available schedules between period.
     * @param from
     * @param to
     * @return the list of entities.
     */
    List<ScheduleDTO> findAllAvailSchedulesBetween(LocalDateTime from, LocalDateTime to);
    
    /**
     * Get all applied schedules.
     *
     * @return the list of entities.
     */
    List<ScheduleDTO> findAllAppliedSchedules();
    
    /**
     * Get all applied schedules between period.
     * @param from
     * @param to
     * @return the list of entities.
     */
    List<ScheduleDTO> findAllAppliedSchedulesBetween(LocalDateTime from, LocalDateTime to);
    
    /**
     * Get the "id" schedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ScheduleDTO> findOne(String id);

    /**
     * Delete the "id" schedule.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
