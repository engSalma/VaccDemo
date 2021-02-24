package com.app.vaccnow.web.rest;

import com.app.vaccnow.VaccnowServiceApp;
import com.app.vaccnow.domain.Timeslot;
import com.app.vaccnow.repository.TimeslotRepository;
import com.app.vaccnow.service.TimeslotService;
import com.app.vaccnow.service.dto.TimeslotDTO;
import com.app.vaccnow.service.mapper.TimeslotMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.app.vaccnow.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.vaccnow.domain.enumeration.PaymentMethod;
/**
 * Integration tests for the {@link TimeslotResource} REST controller.
 */
@SpringBootTest(classes = VaccnowServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TimeslotResourceIT {

    private static final ZonedDateTime DEFAULT_FROM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_TO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final PaymentMethod DEFAULT_PAYMENT = PaymentMethod.CASH;
    private static final PaymentMethod UPDATED_PAYMENT = PaymentMethod.CREDIT;

    @Autowired
    private TimeslotRepository timeslotRepository;

    @Autowired
    private TimeslotMapper timeslotMapper;

    @Autowired
    private TimeslotService timeslotService;

    @Autowired
    private MockMvc restTimeslotMockMvc;

    private Timeslot timeslot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timeslot createEntity() {
        Timeslot timeslot = new Timeslot()
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .status(DEFAULT_STATUS)
            .payment(DEFAULT_PAYMENT);
        return timeslot;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timeslot createUpdatedEntity() {
        Timeslot timeslot = new Timeslot()
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .status(UPDATED_STATUS)
            .payment(UPDATED_PAYMENT);
        return timeslot;
    }

    @BeforeEach
    public void initTest() {
        timeslotRepository.deleteAll();
        timeslot = createEntity();
    }

    @Test
    public void createTimeslot() throws Exception {
        int databaseSizeBeforeCreate = timeslotRepository.findAll().size();
        // Create the Timeslot
        TimeslotDTO timeslotDTO = timeslotMapper.toDto(timeslot);
        restTimeslotMockMvc.perform(post("/api/timeslots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeslotDTO)))
            .andExpect(status().isCreated());

        // Validate the Timeslot in the database
        List<Timeslot> timeslotList = timeslotRepository.findAll();
        assertThat(timeslotList).hasSize(databaseSizeBeforeCreate + 1);
        Timeslot testTimeslot = timeslotList.get(timeslotList.size() - 1);
        assertThat(testTimeslot.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testTimeslot.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testTimeslot.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTimeslot.getPayment()).isEqualTo(DEFAULT_PAYMENT);
    }

    @Test
    public void createTimeslotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeslotRepository.findAll().size();

        // Create the Timeslot with an existing ID
        timeslot.setId("existing_id");
        TimeslotDTO timeslotDTO = timeslotMapper.toDto(timeslot);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeslotMockMvc.perform(post("/api/timeslots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeslotDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Timeslot in the database
        List<Timeslot> timeslotList = timeslotRepository.findAll();
        assertThat(timeslotList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeslotRepository.findAll().size();
        // set the field null
        timeslot.setFrom(null);

        // Create the Timeslot, which fails.
        TimeslotDTO timeslotDTO = timeslotMapper.toDto(timeslot);


        restTimeslotMockMvc.perform(post("/api/timeslots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeslotDTO)))
            .andExpect(status().isBadRequest());

        List<Timeslot> timeslotList = timeslotRepository.findAll();
        assertThat(timeslotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkToIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeslotRepository.findAll().size();
        // set the field null
        timeslot.setTo(null);

        // Create the Timeslot, which fails.
        TimeslotDTO timeslotDTO = timeslotMapper.toDto(timeslot);


        restTimeslotMockMvc.perform(post("/api/timeslots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeslotDTO)))
            .andExpect(status().isBadRequest());

        List<Timeslot> timeslotList = timeslotRepository.findAll();
        assertThat(timeslotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTimeslots() throws Exception {
        // Initialize the database
        timeslotRepository.save(timeslot);

        // Get all the timeslotList
        restTimeslotMockMvc.perform(get("/api/timeslots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeslot.getId())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(sameInstant(DEFAULT_FROM))))
            .andExpect(jsonPath("$.[*].to").value(hasItem(sameInstant(DEFAULT_TO))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].payment").value(hasItem(DEFAULT_PAYMENT.toString())));
    }
    
    @Test
    public void getTimeslot() throws Exception {
        // Initialize the database
        timeslotRepository.save(timeslot);

        // Get the timeslot
        restTimeslotMockMvc.perform(get("/api/timeslots/{id}", timeslot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timeslot.getId()))
            .andExpect(jsonPath("$.from").value(sameInstant(DEFAULT_FROM)))
            .andExpect(jsonPath("$.to").value(sameInstant(DEFAULT_TO)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.payment").value(DEFAULT_PAYMENT.toString()));
    }
    @Test
    public void getNonExistingTimeslot() throws Exception {
        // Get the timeslot
        restTimeslotMockMvc.perform(get("/api/timeslots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTimeslot() throws Exception {
        // Initialize the database
        timeslotRepository.save(timeslot);

        int databaseSizeBeforeUpdate = timeslotRepository.findAll().size();

        // Update the timeslot
        Timeslot updatedTimeslot = timeslotRepository.findById(timeslot.getId()).get();
        updatedTimeslot
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .status(UPDATED_STATUS)
            .payment(UPDATED_PAYMENT);
        TimeslotDTO timeslotDTO = timeslotMapper.toDto(updatedTimeslot);

        restTimeslotMockMvc.perform(put("/api/timeslots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeslotDTO)))
            .andExpect(status().isOk());

        // Validate the Timeslot in the database
        List<Timeslot> timeslotList = timeslotRepository.findAll();
        assertThat(timeslotList).hasSize(databaseSizeBeforeUpdate);
        Timeslot testTimeslot = timeslotList.get(timeslotList.size() - 1);
        assertThat(testTimeslot.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testTimeslot.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testTimeslot.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTimeslot.getPayment()).isEqualTo(UPDATED_PAYMENT);
    }

    @Test
    public void updateNonExistingTimeslot() throws Exception {
        int databaseSizeBeforeUpdate = timeslotRepository.findAll().size();

        // Create the Timeslot
        TimeslotDTO timeslotDTO = timeslotMapper.toDto(timeslot);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeslotMockMvc.perform(put("/api/timeslots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeslotDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Timeslot in the database
        List<Timeslot> timeslotList = timeslotRepository.findAll();
        assertThat(timeslotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteTimeslot() throws Exception {
        // Initialize the database
        timeslotRepository.save(timeslot);

        int databaseSizeBeforeDelete = timeslotRepository.findAll().size();

        // Delete the timeslot
        restTimeslotMockMvc.perform(delete("/api/timeslots/{id}", timeslot.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Timeslot> timeslotList = timeslotRepository.findAll();
        assertThat(timeslotList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
