package com.app.vaccnow.web.rest;

import com.app.vaccnow.service.BranchService;
import com.app.vaccnow.web.rest.errors.BadRequestAlertException;
import com.app.vaccnow.service.dto.BranchDTO;

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
 * REST controller for managing {@link com.app.vaccnow.domain.Branch}.
 */
@RestController
@RequestMapping("/api")
public class BranchResource {

    private final Logger log = LoggerFactory.getLogger(BranchResource.class);

    private static final String ENTITY_NAME = "branch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BranchService branchService;

    public BranchResource(BranchService branchService) {
        this.branchService = branchService;
    }

    /**
     * {@code POST  /branches} : Create a new branch.
     *
     * @param branchDTO the branchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new branchDTO, or with status {@code 400 (Bad Request)} if the branch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/branches")
    public ResponseEntity<BranchDTO> createBranch(@Valid @RequestBody BranchDTO branchDTO) throws URISyntaxException {
        log.debug("REST request to save Branch : {}", branchDTO);
        if (branchDTO.getId() != null) {
            throw new BadRequestAlertException("A new branch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BranchDTO result = branchService.save(branchDTO);
        return ResponseEntity.created(new URI("/api/branches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /branches} : Updates an existing branch.
     *
     * @param branchDTO the branchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated branchDTO,
     * or with status {@code 400 (Bad Request)} if the branchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the branchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/branches")
    public ResponseEntity<BranchDTO> updateBranch(@Valid @RequestBody BranchDTO branchDTO) throws URISyntaxException {
        log.debug("REST request to update Branch : {}", branchDTO);
        if (branchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BranchDTO result = branchService.save(branchDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, branchDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /branches} : get all the branches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of branches in body.
     */
    @GetMapping("/branches")
    public List<BranchDTO> getAllBranches() {
        log.debug("REST request to get all Branches");
        return branchService.findAll();
    }
    
    /**
     * {@code GET  /branches} : get all the branches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of branches in body.
     */
    @GetMapping("/available-vacc-branches")
    public List<BranchDTO> getAvailableVacByBranches() {
        log.debug("REST request to get all Branches");
        return branchService.findAvailableVacByBranch();
    }

    /**
     * {@code GET  /branches/:id} : get the "id" branch.
     *
     * @param id the id of the branchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the branchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/branches/{id}")
    public ResponseEntity<BranchDTO> getBranch(@PathVariable String id) {
        log.debug("REST request to get Branch : {}", id);
        Optional<BranchDTO> branchDTO = branchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(branchDTO);
    }

    /**
     * {@code DELETE  /branches/:id} : delete the "id" branch.
     *
     * @param id the id of the branchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/branches/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable String id) {
        log.debug("REST request to delete Branch : {}", id);
        branchService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
