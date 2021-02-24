package com.app.vaccnow.service.impl;

import com.app.vaccnow.service.BranchService;
import com.app.vaccnow.domain.Branch;
import com.app.vaccnow.repository.BranchRepository;
import com.app.vaccnow.service.dto.BranchDTO;
import com.app.vaccnow.service.mapper.BranchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Branch}.
 */
@Service
public class BranchServiceImpl implements BranchService {

    private final Logger log = LoggerFactory.getLogger(BranchServiceImpl.class);

    private final BranchRepository branchRepository;

    private final BranchMapper branchMapper;

    public BranchServiceImpl(BranchRepository branchRepository, BranchMapper branchMapper) {
        this.branchRepository = branchRepository;
        this.branchMapper = branchMapper;
    }

    @Override
    public BranchDTO save(BranchDTO branchDTO) {
        log.debug("Request to save Branch : {}", branchDTO);
        Branch branch = branchMapper.toEntity(branchDTO);
        branch = branchRepository.save(branch);
        return branchMapper.toDto(branch);
    }

    @Override
    public List<BranchDTO> findAll() {
        log.debug("Request to get all Branches");
        return branchRepository.findAll().stream()
            .map(branchMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Override
    public List<BranchDTO> findAvailableVacByBranch() {
        log.debug("Request to get all Branches");
        return branchRepository.findByVaccineStockGreaterThan("0").stream()
            .map(branchMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    public Optional<BranchDTO> findOne(String id) {
        log.debug("Request to get Branch : {}", id);
        return branchRepository.findById(id)
            .map(branchMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Branch : {}", id);
        branchRepository.deleteById(id);
    }
}
