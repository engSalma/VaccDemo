package com.app.vaccnow.web.rest;

import com.app.vaccnow.VaccnowServiceApp;
import com.app.vaccnow.domain.Branch;
import com.app.vaccnow.repository.BranchRepository;
import com.app.vaccnow.service.BranchService;
import com.app.vaccnow.service.dto.BranchDTO;
import com.app.vaccnow.service.mapper.BranchMapper;

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

/**
 * Integration tests for the {@link BranchResource} REST controller.
 */
@SpringBootTest(classes = VaccnowServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BranchResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VACCINE_ALLOWED = false;
    private static final Boolean UPDATED_VACCINE_ALLOWED = true;

    private static final String DEFAULT_VACCINE_STOCK = "AAAAAAAAAA";
    private static final String UPDATED_VACCINE_STOCK = "BBBBBBBBBB";

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchMapper branchMapper;

    @Autowired
    private BranchService branchService;

    @Autowired
    private MockMvc restBranchMockMvc;

    private Branch branch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Branch createEntity() {
        Branch branch = new Branch()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .vaccineAllowed(DEFAULT_VACCINE_ALLOWED)
            .vaccineStock(DEFAULT_VACCINE_STOCK);
        return branch;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Branch createUpdatedEntity() {
        Branch branch = new Branch()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .vaccineAllowed(UPDATED_VACCINE_ALLOWED)
            .vaccineStock(UPDATED_VACCINE_STOCK);
        return branch;
    }

    @BeforeEach
    public void initTest() {
        branchRepository.deleteAll();
        branch = createEntity();
    }

    @Test
    public void createBranch() throws Exception {
        int databaseSizeBeforeCreate = branchRepository.findAll().size();
        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);
        restBranchMockMvc.perform(post("/api/branches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(branchDTO)))
            .andExpect(status().isCreated());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeCreate + 1);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBranch.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testBranch.isVaccineAllowed()).isEqualTo(DEFAULT_VACCINE_ALLOWED);
        assertThat(testBranch.getVaccineStock()).isEqualTo(DEFAULT_VACCINE_STOCK);
    }

    @Test
    public void createBranchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = branchRepository.findAll().size();

        // Create the Branch with an existing ID
        branch.setId("existing_id");
        BranchDTO branchDTO = branchMapper.toDto(branch);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBranchMockMvc.perform(post("/api/branches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(branchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchRepository.findAll().size();
        // set the field null
        branch.setName(null);

        // Create the Branch, which fails.
        BranchDTO branchDTO = branchMapper.toDto(branch);


        restBranchMockMvc.perform(post("/api/branches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(branchDTO)))
            .andExpect(status().isBadRequest());

        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllBranches() throws Exception {
        // Initialize the database
        branchRepository.save(branch);

        // Get all the branchList
        restBranchMockMvc.perform(get("/api/branches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branch.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].vaccineAllowed").value(hasItem(DEFAULT_VACCINE_ALLOWED.booleanValue())))
            .andExpect(jsonPath("$.[*].vaccineStock").value(hasItem(DEFAULT_VACCINE_STOCK)));
    }
    
    @Test
    public void getBranch() throws Exception {
        // Initialize the database
        branchRepository.save(branch);

        // Get the branch
        restBranchMockMvc.perform(get("/api/branches/{id}", branch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(branch.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.vaccineAllowed").value(DEFAULT_VACCINE_ALLOWED.booleanValue()))
            .andExpect(jsonPath("$.vaccineStock").value(DEFAULT_VACCINE_STOCK));
    }
    @Test
    public void getNonExistingBranch() throws Exception {
        // Get the branch
        restBranchMockMvc.perform(get("/api/branches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBranch() throws Exception {
        // Initialize the database
        branchRepository.save(branch);

        int databaseSizeBeforeUpdate = branchRepository.findAll().size();

        // Update the branch
        Branch updatedBranch = branchRepository.findById(branch.getId()).get();
        updatedBranch
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .vaccineAllowed(UPDATED_VACCINE_ALLOWED)
            .vaccineStock(UPDATED_VACCINE_STOCK);
        BranchDTO branchDTO = branchMapper.toDto(updatedBranch);

        restBranchMockMvc.perform(put("/api/branches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(branchDTO)))
            .andExpect(status().isOk());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBranch.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBranch.isVaccineAllowed()).isEqualTo(UPDATED_VACCINE_ALLOWED);
        assertThat(testBranch.getVaccineStock()).isEqualTo(UPDATED_VACCINE_STOCK);
    }

    @Test
    public void updateNonExistingBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();

        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBranchMockMvc.perform(put("/api/branches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(branchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBranch() throws Exception {
        // Initialize the database
        branchRepository.save(branch);

        int databaseSizeBeforeDelete = branchRepository.findAll().size();

        // Delete the branch
        restBranchMockMvc.perform(delete("/api/branches/{id}", branch.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
