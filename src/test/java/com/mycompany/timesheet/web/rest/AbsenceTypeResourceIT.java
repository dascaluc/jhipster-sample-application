package com.mycompany.timesheet.web.rest;

import com.mycompany.timesheet.JhipsterSampleApplicationApp;
import com.mycompany.timesheet.domain.AbsenceType;
import com.mycompany.timesheet.repository.AbsenceTypeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AbsenceTypeResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AbsenceTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AbsenceTypeRepository absenceTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAbsenceTypeMockMvc;

    private AbsenceType absenceType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AbsenceType createEntity(EntityManager em) {
        AbsenceType absenceType = new AbsenceType()
            .name(DEFAULT_NAME);
        return absenceType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AbsenceType createUpdatedEntity(EntityManager em) {
        AbsenceType absenceType = new AbsenceType()
            .name(UPDATED_NAME);
        return absenceType;
    }

    @BeforeEach
    public void initTest() {
        absenceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAbsenceType() throws Exception {
        int databaseSizeBeforeCreate = absenceTypeRepository.findAll().size();

        // Create the AbsenceType
        restAbsenceTypeMockMvc.perform(post("/api/absence-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(absenceType)))
            .andExpect(status().isCreated());

        // Validate the AbsenceType in the database
        List<AbsenceType> absenceTypeList = absenceTypeRepository.findAll();
        assertThat(absenceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AbsenceType testAbsenceType = absenceTypeList.get(absenceTypeList.size() - 1);
        assertThat(testAbsenceType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAbsenceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = absenceTypeRepository.findAll().size();

        // Create the AbsenceType with an existing ID
        absenceType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbsenceTypeMockMvc.perform(post("/api/absence-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(absenceType)))
            .andExpect(status().isBadRequest());

        // Validate the AbsenceType in the database
        List<AbsenceType> absenceTypeList = absenceTypeRepository.findAll();
        assertThat(absenceTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAbsenceTypes() throws Exception {
        // Initialize the database
        absenceTypeRepository.saveAndFlush(absenceType);

        // Get all the absenceTypeList
        restAbsenceTypeMockMvc.perform(get("/api/absence-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(absenceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getAbsenceType() throws Exception {
        // Initialize the database
        absenceTypeRepository.saveAndFlush(absenceType);

        // Get the absenceType
        restAbsenceTypeMockMvc.perform(get("/api/absence-types/{id}", absenceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(absenceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingAbsenceType() throws Exception {
        // Get the absenceType
        restAbsenceTypeMockMvc.perform(get("/api/absence-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbsenceType() throws Exception {
        // Initialize the database
        absenceTypeRepository.saveAndFlush(absenceType);

        int databaseSizeBeforeUpdate = absenceTypeRepository.findAll().size();

        // Update the absenceType
        AbsenceType updatedAbsenceType = absenceTypeRepository.findById(absenceType.getId()).get();
        // Disconnect from session so that the updates on updatedAbsenceType are not directly saved in db
        em.detach(updatedAbsenceType);
        updatedAbsenceType
            .name(UPDATED_NAME);

        restAbsenceTypeMockMvc.perform(put("/api/absence-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAbsenceType)))
            .andExpect(status().isOk());

        // Validate the AbsenceType in the database
        List<AbsenceType> absenceTypeList = absenceTypeRepository.findAll();
        assertThat(absenceTypeList).hasSize(databaseSizeBeforeUpdate);
        AbsenceType testAbsenceType = absenceTypeList.get(absenceTypeList.size() - 1);
        assertThat(testAbsenceType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAbsenceType() throws Exception {
        int databaseSizeBeforeUpdate = absenceTypeRepository.findAll().size();

        // Create the AbsenceType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbsenceTypeMockMvc.perform(put("/api/absence-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(absenceType)))
            .andExpect(status().isBadRequest());

        // Validate the AbsenceType in the database
        List<AbsenceType> absenceTypeList = absenceTypeRepository.findAll();
        assertThat(absenceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAbsenceType() throws Exception {
        // Initialize the database
        absenceTypeRepository.saveAndFlush(absenceType);

        int databaseSizeBeforeDelete = absenceTypeRepository.findAll().size();

        // Delete the absenceType
        restAbsenceTypeMockMvc.perform(delete("/api/absence-types/{id}", absenceType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AbsenceType> absenceTypeList = absenceTypeRepository.findAll();
        assertThat(absenceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
