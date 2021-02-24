package com.app.vaccnow.web.rest;

import com.app.vaccnow.service.TimeslotService;
import com.app.vaccnow.web.rest.errors.BadRequestAlertException;
import com.app.vaccnow.service.dto.TimeslotDTO;

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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.app.vaccnow.domain.Timeslot}.
 */
@RestController
@RequestMapping("/api")
public class TimeslotResource {

    private final Logger log = LoggerFactory.getLogger(TimeslotResource.class);

    private static final String ENTITY_NAME = "timeslot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeslotService timeslotService;

    public TimeslotResource(TimeslotService timeslotService) {
        this.timeslotService = timeslotService;
    }

    /**
     * {@code POST  /timeslots} : Create a new timeslot.
     *
     * @param timeslotDTO the timeslotDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timeslotDTO, or with status {@code 400 (Bad Request)} if the timeslot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/timeslots")
    public ResponseEntity<TimeslotDTO> createTimeslot(@Valid @RequestBody TimeslotDTO timeslotDTO) throws URISyntaxException {
        log.debug("REST request to save Timeslot : {}", timeslotDTO);
        if (timeslotDTO.getId() != null) {
            throw new BadRequestAlertException("A new timeslot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeslotDTO result = timeslotService.save(timeslotDTO);
        return ResponseEntity.created(new URI("/api/timeslots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /timeslots} : Updates an existing timeslot.
     *
     * @param timeslotDTO the timeslotDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeslotDTO,
     * or with status {@code 400 (Bad Request)} if the timeslotDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timeslotDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/timeslots")
    public ResponseEntity<TimeslotDTO> updateTimeslot(@Valid @RequestBody TimeslotDTO timeslotDTO) throws URISyntaxException {
        log.debug("REST request to update Timeslot : {}", timeslotDTO);
        if (timeslotDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeslotDTO result = timeslotService.save(timeslotDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, timeslotDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /timeslots} : get all the timeslots.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeslots in body.
     */
    @GetMapping("/timeslots")
    public List<TimeslotDTO> getAllTimeslots() {
        log.debug("REST request to get all Timeslots");
        return timeslotService.findAll();
    }
    
    /**
     * {@code GET  /timeslots} : get all the timeslots.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeslots in body.
     */
    @GetMapping("/available-timeslots-by-branch")
    public List<TimeslotDTO> getAvailableTimeslotsBy() {
        log.debug("REST request to get all Timeslots");
        return timeslotService.findAll();
    }

    /**
     * {@code GET  /timeslots/:id} : get the "id" timeslot.
     *
     * @param id the id of the timeslotDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timeslotDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/timeslots/{id}")
    public ResponseEntity<TimeslotDTO> getTimeslot(@PathVariable String id) {
        log.debug("REST request to get Timeslot : {}", id);
        Optional<TimeslotDTO> timeslotDTO = timeslotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timeslotDTO);
    }

    /**
     * {@code DELETE  /timeslots/:id} : delete the "id" timeslot.
     *
     * @param id the id of the timeslotDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/timeslots/{id}")
    public ResponseEntity<Void> deleteTimeslot(@PathVariable String id) {
        log.debug("REST request to delete Timeslot : {}", id);
        timeslotService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
