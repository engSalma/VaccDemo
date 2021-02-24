package com.app.vaccnow.web.rest;

import com.app.vaccnow.VaccnowServiceApp;
import com.app.vaccnow.domain.Schedule;
import com.app.vaccnow.repository.ScheduleRepository;
import com.app.vaccnow.service.ScheduleService;
import com.app.vaccnow.service.dto.ScheduleDTO;
import com.app.vaccnow.service.mapper.ScheduleMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ScheduleResource} REST controller.
 */
@SpringBootTest(classes = VaccnowServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ScheduleResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_WORKING_DAY = false;
    private static final Boolean UPDATED_IS_WORKING_DAY = true;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private MockMvc restScheduleMockMvc;

    private Schedule schedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schedule createEntity() {
        Schedule schedule = new Schedule()
            .date(DEFAULT_DATE)
            .isWorkingDay(DEFAULT_IS_WORKING_DAY);
        return schedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schedule createUpdatedEntity() {
        Schedule schedule = new Schedule()
            .date(UPDATED_DATE)
            .isWorkingDay(UPDATED_IS_WORKING_DAY);
        return schedule;
    }

    @BeforeEach
    public void initTest() {
        scheduleRepository.deleteAll();
        schedule = createEntity();
    }

    @Test
    public void createSchedule() throws Exception {
        int databaseSizeBeforeCreate = scheduleRepository.findAll().size();
        // Create the Schedule
        ScheduleDTO scheduleDTO = scheduleMapper.toDto(schedule);
        restScheduleMockMvc.perform(post("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeCreate + 1);
        Schedule testSchedule = scheduleList.get(scheduleList.size() - 1);
        assertThat(testSchedule.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSchedule.isIsWorkingDay()).isEqualTo(DEFAULT_IS_WORKING_DAY);
    }

    @Test
    public void createScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scheduleRepository.findAll().size();

        // Create the Schedule with an existing ID
        schedule.setId("existing_id");
        ScheduleDTO scheduleDTO = scheduleMapper.toDto(schedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduleMockMvc.perform(post("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleRepository.findAll().size();
        // set the field null
        schedule.setDate(null);

        // Create the Schedule, which fails.
        ScheduleDTO scheduleDTO = scheduleMapper.toDto(schedule);


        restScheduleMockMvc.perform(post("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleDTO)))
            .andExpect(status().isBadRequest());

        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSchedules() throws Exception {
        // Initialize the database
        scheduleRepository.save(schedule);

        // Get all the scheduleList
        restScheduleMockMvc.perform(get("/api/schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schedule.getId())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].isWorkingDay").value(hasItem(DEFAULT_IS_WORKING_DAY.booleanValue())));
    }
    
    @Test
    public void getSchedule() throws Exception {
        // Initialize the database
        scheduleRepository.save(schedule);

        // Get the schedule
        restScheduleMockMvc.perform(get("/api/schedules/{id}", schedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schedule.getId()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.isWorkingDay").value(DEFAULT_IS_WORKING_DAY.booleanValue()));
    }
    @Test
    public void getNonExistingSchedule() throws Exception {
        // Get the schedule
        restScheduleMockMvc.perform(get("/api/schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSchedule() throws Exception {
        // Initialize the database
        scheduleRepository.save(schedule);

        int databaseSizeBeforeUpdate = scheduleRepository.findAll().size();

        // Update the schedule
        Schedule updatedSchedule = scheduleRepository.findById(schedule.getId()).get();
        updatedSchedule
            .date(UPDATED_DATE)
            .isWorkingDay(UPDATED_IS_WORKING_DAY);
        ScheduleDTO scheduleDTO = scheduleMapper.toDto(updatedSchedule);

        restScheduleMockMvc.perform(put("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleDTO)))
            .andExpect(status().isOk());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeUpdate);
        Schedule testSchedule = scheduleList.get(scheduleList.size() - 1);
        assertThat(testSchedule.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSchedule.isIsWorkingDay()).isEqualTo(UPDATED_IS_WORKING_DAY);
    }

    @Test
    public void updateNonExistingSchedule() throws Exception {
        int databaseSizeBeforeUpdate = scheduleRepository.findAll().size();

        // Create the Schedule
        ScheduleDTO scheduleDTO = scheduleMapper.toDto(schedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduleMockMvc.perform(put("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSchedule() throws Exception {
        // Initialize the database
        scheduleRepository.save(schedule);

        int databaseSizeBeforeDelete = scheduleRepository.findAll().size();

        // Delete the schedule
        restScheduleMockMvc.perform(delete("/api/schedules/{id}", schedule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
