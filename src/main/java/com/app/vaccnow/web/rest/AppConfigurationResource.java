package com.app.vaccnow.web.rest;

import com.app.vaccnow.service.AppConfigurationService;
import com.app.vaccnow.web.rest.errors.BadRequestAlertException;
import com.app.vaccnow.service.dto.AppConfigurationDTO;

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
 * REST controller for managing {@link com.app.vaccnow.domain.AppConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class AppConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(AppConfigurationResource.class);

    private static final String ENTITY_NAME = "appConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppConfigurationService appConfigurationService;

    public AppConfigurationResource(AppConfigurationService appConfigurationService) {
        this.appConfigurationService = appConfigurationService;
    }

    /**
     * {@code POST  /app-configurations} : Create a new appConfiguration.
     *
     * @param appConfigurationDTO the appConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appConfigurationDTO, or with status {@code 400 (Bad Request)} if the appConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-configurations")
    public ResponseEntity<AppConfigurationDTO> createAppConfiguration(@Valid @RequestBody AppConfigurationDTO appConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to save AppConfiguration : {}", appConfigurationDTO);
        if (appConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new appConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppConfigurationDTO result = appConfigurationService.save(appConfigurationDTO);
        return ResponseEntity.created(new URI("/api/app-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /app-configurations} : Updates an existing appConfiguration.
     *
     * @param appConfigurationDTO the appConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the appConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-configurations")
    public ResponseEntity<AppConfigurationDTO> updateAppConfiguration(@Valid @RequestBody AppConfigurationDTO appConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to update AppConfiguration : {}", appConfigurationDTO);
        if (appConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppConfigurationDTO result = appConfigurationService.save(appConfigurationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appConfigurationDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /app-configurations} : get all the appConfigurations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appConfigurations in body.
     */
    @GetMapping("/app-configurations")
    public List<AppConfigurationDTO> getAllAppConfigurations() {
        log.debug("REST request to get all AppConfigurations");
        return appConfigurationService.findAll();
    }

    /**
     * {@code GET  /app-configurations/:id} : get the "id" appConfiguration.
     *
     * @param id the id of the appConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-configurations/{id}")
    public ResponseEntity<AppConfigurationDTO> getAppConfiguration(@PathVariable String id) {
        log.debug("REST request to get AppConfiguration : {}", id);
        Optional<AppConfigurationDTO> appConfigurationDTO = appConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appConfigurationDTO);
    }

    /**
     * {@code DELETE  /app-configurations/:id} : delete the "id" appConfiguration.
     *
     * @param id the id of the appConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-configurations/{id}")
    public ResponseEntity<Void> deleteAppConfiguration(@PathVariable String id) {
        log.debug("REST request to delete AppConfiguration : {}", id);
        appConfigurationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
