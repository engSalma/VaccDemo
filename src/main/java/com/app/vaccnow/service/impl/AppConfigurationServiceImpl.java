package com.app.vaccnow.service.impl;

import com.app.vaccnow.service.AppConfigurationService;
import com.app.vaccnow.domain.AppConfiguration;
import com.app.vaccnow.repository.AppConfigurationRepository;
import com.app.vaccnow.service.dto.AppConfigurationDTO;
import com.app.vaccnow.service.mapper.AppConfigurationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AppConfiguration}.
 */
@Service
public class AppConfigurationServiceImpl implements AppConfigurationService {

    private final Logger log = LoggerFactory.getLogger(AppConfigurationServiceImpl.class);

    private final AppConfigurationRepository appConfigurationRepository;

    private final AppConfigurationMapper appConfigurationMapper;

    public AppConfigurationServiceImpl(AppConfigurationRepository appConfigurationRepository, AppConfigurationMapper appConfigurationMapper) {
        this.appConfigurationRepository = appConfigurationRepository;
        this.appConfigurationMapper = appConfigurationMapper;
    }

    @Override
    public AppConfigurationDTO save(AppConfigurationDTO appConfigurationDTO) {
        log.debug("Request to save AppConfiguration : {}", appConfigurationDTO);
        AppConfiguration appConfiguration = appConfigurationMapper.toEntity(appConfigurationDTO);
        appConfiguration = appConfigurationRepository.save(appConfiguration);
        return appConfigurationMapper.toDto(appConfiguration);
    }

    @Override
    public List<AppConfigurationDTO> findAll() {
        log.debug("Request to get all AppConfigurations");
        return appConfigurationRepository.findAll().stream()
            .map(appConfigurationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    public Optional<AppConfigurationDTO> findOne(String id) {
        log.debug("Request to get AppConfiguration : {}", id);
        return appConfigurationRepository.findById(id)
            .map(appConfigurationMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete AppConfiguration : {}", id);
        appConfigurationRepository.deleteById(id);
    }
}
