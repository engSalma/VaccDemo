package com.app.vaccnow.web.rest;

import com.app.vaccnow.VaccnowServiceApp;
import com.app.vaccnow.domain.AppConfiguration;
import com.app.vaccnow.repository.AppConfigurationRepository;
import com.app.vaccnow.service.AppConfigurationService;
import com.app.vaccnow.service.dto.AppConfigurationDTO;
import com.app.vaccnow.service.mapper.AppConfigurationMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.vaccnow.domain.enumeration.DataType;
/**
 * Integration tests for the {@link AppConfigurationResource} REST controller.
 */
@SpringBootTest(classes = VaccnowServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AppConfigurationResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final DataType DEFAULT_DATA_TYPE = DataType.STRING;
    private static final DataType UPDATED_DATA_TYPE = DataType.NUMBER;

    private static final String DEFAULT_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_PATTERN = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private AppConfigurationRepository appConfigurationRepository;

    @Autowired
    private AppConfigurationMapper appConfigurationMapper;

    @Autowired
    private AppConfigurationService appConfigurationService;

    @Autowired
    private MockMvc restAppConfigurationMockMvc;

    private AppConfiguration appConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppConfiguration createEntity() {
        AppConfiguration appConfiguration = new AppConfiguration()
            .key(DEFAULT_KEY)
            .dataType(DEFAULT_DATA_TYPE)
            .pattern(DEFAULT_PATTERN)
            .value(DEFAULT_VALUE);
        return appConfiguration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppConfiguration createUpdatedEntity() {
        AppConfiguration appConfiguration = new AppConfiguration()
            .key(UPDATED_KEY)
            .dataType(UPDATED_DATA_TYPE)
            .pattern(UPDATED_PATTERN)
            .value(UPDATED_VALUE);
        return appConfiguration;
    }

    @BeforeEach
    public void initTest() {
        appConfigurationRepository.deleteAll();
        appConfiguration = createEntity();
    }

    @Test
    public void createAppConfiguration() throws Exception {
        int databaseSizeBeforeCreate = appConfigurationRepository.findAll().size();
        // Create the AppConfiguration
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);
        restAppConfigurationMockMvc.perform(post("/api/app-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        AppConfiguration testAppConfiguration = appConfigurationList.get(appConfigurationList.size() - 1);
        assertThat(testAppConfiguration.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testAppConfiguration.getDataType()).isEqualTo(DEFAULT_DATA_TYPE);
        assertThat(testAppConfiguration.getPattern()).isEqualTo(DEFAULT_PATTERN);
        assertThat(testAppConfiguration.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void createAppConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appConfigurationRepository.findAll().size();

        // Create the AppConfiguration with an existing ID
        appConfiguration.setId("existing_id");
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppConfigurationMockMvc.perform(post("/api/app-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setKey(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);


        restAppConfigurationMockMvc.perform(post("/api/app-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setDataType(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);


        restAppConfigurationMockMvc.perform(post("/api/app-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setValue(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);


        restAppConfigurationMockMvc.perform(post("/api/app-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAppConfigurations() throws Exception {
        // Initialize the database
        appConfigurationRepository.save(appConfiguration);

        // Get all the appConfigurationList
        restAppConfigurationMockMvc.perform(get("/api/app-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appConfiguration.getId())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pattern").value(hasItem(DEFAULT_PATTERN)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    public void getAppConfiguration() throws Exception {
        // Initialize the database
        appConfigurationRepository.save(appConfiguration);

        // Get the appConfiguration
        restAppConfigurationMockMvc.perform(get("/api/app-configurations/{id}", appConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appConfiguration.getId()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.pattern").value(DEFAULT_PATTERN))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    public void getNonExistingAppConfiguration() throws Exception {
        // Get the appConfiguration
        restAppConfigurationMockMvc.perform(get("/api/app-configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAppConfiguration() throws Exception {
        // Initialize the database
        appConfigurationRepository.save(appConfiguration);

        int databaseSizeBeforeUpdate = appConfigurationRepository.findAll().size();

        // Update the appConfiguration
        AppConfiguration updatedAppConfiguration = appConfigurationRepository.findById(appConfiguration.getId()).get();
        updatedAppConfiguration
            .key(UPDATED_KEY)
            .dataType(UPDATED_DATA_TYPE)
            .pattern(UPDATED_PATTERN)
            .value(UPDATED_VALUE);
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(updatedAppConfiguration);

        restAppConfigurationMockMvc.perform(put("/api/app-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO)))
            .andExpect(status().isOk());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeUpdate);
        AppConfiguration testAppConfiguration = appConfigurationList.get(appConfigurationList.size() - 1);
        assertThat(testAppConfiguration.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testAppConfiguration.getDataType()).isEqualTo(UPDATED_DATA_TYPE);
        assertThat(testAppConfiguration.getPattern()).isEqualTo(UPDATED_PATTERN);
        assertThat(testAppConfiguration.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    public void updateNonExistingAppConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = appConfigurationRepository.findAll().size();

        // Create the AppConfiguration
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppConfigurationMockMvc.perform(put("/api/app-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteAppConfiguration() throws Exception {
        // Initialize the database
        appConfigurationRepository.save(appConfiguration);

        int databaseSizeBeforeDelete = appConfigurationRepository.findAll().size();

        // Delete the appConfiguration
        restAppConfigurationMockMvc.perform(delete("/api/app-configurations/{id}", appConfiguration.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
