package com.app.vaccnow.service.impl;

import com.app.vaccnow.service.TimeslotService;
import com.app.vaccnow.config.Constants;
import com.app.vaccnow.domain.Timeslot;
import com.app.vaccnow.repository.TimeslotRepository;
import com.app.vaccnow.service.dto.TimeslotDTO;
import com.app.vaccnow.service.mapper.TimeslotMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Timeslot}.
 */
@Service
public class TimeslotServiceImpl implements TimeslotService {

    private final Logger log = LoggerFactory.getLogger(TimeslotServiceImpl.class);

    private final TimeslotRepository timeslotRepository;

    private final TimeslotMapper timeslotMapper;

    public TimeslotServiceImpl(TimeslotRepository timeslotRepository, TimeslotMapper timeslotMapper) {
        this.timeslotRepository = timeslotRepository;
        this.timeslotMapper = timeslotMapper;
    }

    @Override
    public TimeslotDTO save(TimeslotDTO timeslotDTO) {
        log.debug("Request to save Timeslot : {}", timeslotDTO);
        Timeslot timeslot = timeslotMapper.toEntity(timeslotDTO);
        timeslot = timeslotRepository.save(timeslot);
        return timeslotMapper.toDto(timeslot);
    }

    @Override
    public List<TimeslotDTO> findAll() {
        log.debug("Request to get all Timeslots");
        return timeslotRepository.findAll().stream()
            .map(timeslotMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Override
    public List<TimeslotDTO> findAvailTimeslots() {
        log.debug("Request to get all Timeslots");
        return timeslotRepository.findByStatus(Constants.DEFAULT_TIMESLOT_STATUS).stream()
            .map(timeslotMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    public Optional<TimeslotDTO> findOne(String id) {
        log.debug("Request to get Timeslot : {}", id);
        return timeslotRepository.findById(id)
            .map(timeslotMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Timeslot : {}", id);
        timeslotRepository.deleteById(id);
    }
}
