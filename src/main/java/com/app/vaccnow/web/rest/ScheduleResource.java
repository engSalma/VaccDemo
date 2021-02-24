package com.app.vaccnow.web.rest;

import com.app.vaccnow.service.ScheduleService;
import com.app.vaccnow.web.rest.errors.BadRequestAlertException;
import com.app.vaccnow.service.dto.ScheduleDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.app.vaccnow.domain.Schedule}.
 */
@RestController
@RequestMapping("/api")
public class ScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleResource.class);

    private static final String ENTITY_NAME = "schedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScheduleService scheduleService;

    public ScheduleResource(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * {@code POST  /schedules} : Create a new schedule.
     *
     * @param scheduleDTO the scheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scheduleDTO, or with status {@code 400 (Bad Request)} if the schedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schedules")
    public ResponseEntity<ScheduleDTO> createSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) throws URISyntaxException {
        log.debug("REST request to save Schedule : {}", scheduleDTO);
        if (scheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new schedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScheduleDTO result = scheduleService.save(scheduleDTO);
        return ResponseEntity.created(new URI("/api/schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /schedules} : Updates an existing schedule.
     *
     * @param scheduleDTO the scheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scheduleDTO,
     * or with status {@code 400 (Bad Request)} if the scheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schedules")
    public ResponseEntity<ScheduleDTO> updateSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) throws URISyntaxException {
        log.debug("REST request to update Schedule : {}", scheduleDTO);
        if (scheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScheduleDTO result = scheduleService.save(scheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scheduleDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /schedules} : get all the schedules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schedules in body.
     */
    @GetMapping("/schedules")
    public List<ScheduleDTO> getAllSchedules() {
        log.debug("REST request to get all Schedules");
        return scheduleService.findAll();
    }
    
    /**
     * {@code GET  /schedules} : get all the schedules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schedules in body.
     */
    @GetMapping("/available-schedules")
    public List<ScheduleDTO> getAvailableSchedules() {
        log.debug("REST request to get all Schedules");
        return scheduleService.findAllAvailableSchedules();
    }
    
    @GetMapping("/available-schedules-between/{from}/{to}")
    public List<ScheduleDTO> getAvailableSchedulesBetween(@PathVariable LocalDateTime from, @PathVariable LocalDateTime to) {
        log.debug("REST request to get all Schedules");
        return scheduleService.findAllAvailSchedulesBetween(from, to);
    }
    
    @GetMapping("/applied-schedules")
    public List<ScheduleDTO> getAppliedSchedules() {
        log.debug("REST request to get all Schedules");
        return scheduleService.findAllAppliedSchedules();
    }
    
    @GetMapping("/applied-schedules-between/{from}/{to}")
    public List<ScheduleDTO> findAllAppliedSchedulesBetween(@PathVariable LocalDateTime from, @PathVariable LocalDateTime to) {
        log.debug("REST request to get all Schedules");
        return scheduleService.findAllAppliedSchedulesBetween(from, to);
    }

    /**
     * {@code GET  /schedules/:id} : get the "id" schedule.
     *
     * @param id the id of the scheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleDTO> getSchedule(@PathVariable String id) {
        log.debug("REST request to get Schedule : {}", id);
        Optional<ScheduleDTO> scheduleDTO = scheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scheduleDTO);
    }

    /**
     * {@code DELETE  /schedules/:id} : delete the "id" schedule.
     *
     * @param id the id of the scheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable String id) {
        log.debug("REST request to delete Schedule : {}", id);
        scheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
