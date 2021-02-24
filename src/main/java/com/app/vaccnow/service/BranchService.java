package com.app.vaccnow.service;

import com.app.vaccnow.service.dto.BranchDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.app.vaccnow.domain.Branch}.
 */
public interface BranchService {

    /**
     * Save a branch.
     *
     * @param branchDTO the entity to save.
     * @return the persisted entity.
     */
    BranchDTO save(BranchDTO branchDTO);

    /**
     * Get all the branches.
     *
     * @return the list of entities.
     */
    List<BranchDTO> findAll();
    
    /**
     * Get all available vacc by branches.
     *
     * @return the list of entities.
     */
    List<BranchDTO> findAvailableVacByBranch();


    /**
     * Get the "id" branch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BranchDTO> findOne(String id);

    /**
     * Delete the "id" branch.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
