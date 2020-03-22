package com.mycompany.timesheet.web.rest;

import com.mycompany.timesheet.JhipsterSampleApplicationApp;
import com.mycompany.timesheet.domain.TimeTracking;
import com.mycompany.timesheet.repository.TimeTrackingRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.timesheet.domain.enumeration.AccesType;
/**
 * Integration tests for the {@link TimeTrackingResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TimeTrackingResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final AccesType DEFAULT_ACCESS_TYPEE = AccesType.IN;
    private static final AccesType UPDATED_ACCESS_TYPEE = AccesType.OUT;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TimeTrackingRepository timeTrackingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimeTrackingMockMvc;

    private TimeTracking timeTracking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeTracking createEntity(EntityManager em) {
        TimeTracking timeTracking = new TimeTracking()
            .date(DEFAULT_DATE)
            .accessTypee(DEFAULT_ACCESS_TYPEE)
            .description(DEFAULT_DESCRIPTION);
        return timeTracking;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeTracking createUpdatedEntity(EntityManager em) {
        TimeTracking timeTracking = new TimeTracking()
            .date(UPDATED_DATE)
            .accessTypee(UPDATED_ACCESS_TYPEE)
            .description(UPDATED_DESCRIPTION);
        return timeTracking;
    }

    @BeforeEach
    public void initTest() {
        timeTracking = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeTracking() throws Exception {
        int databaseSizeBeforeCreate = timeTrackingRepository.findAll().size();

        // Create the TimeTracking
        restTimeTrackingMockMvc.perform(post("/api/time-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeTracking)))
            .andExpect(status().isCreated());

        // Validate the TimeTracking in the database
        List<TimeTracking> timeTrackingList = timeTrackingRepository.findAll();
        assertThat(timeTrackingList).hasSize(databaseSizeBeforeCreate + 1);
        TimeTracking testTimeTracking = timeTrackingList.get(timeTrackingList.size() - 1);
        assertThat(testTimeTracking.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTimeTracking.getAccessTypee()).isEqualTo(DEFAULT_ACCESS_TYPEE);
        assertThat(testTimeTracking.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTimeTrackingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeTrackingRepository.findAll().size();

        // Create the TimeTracking with an existing ID
        timeTracking.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeTrackingMockMvc.perform(post("/api/time-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeTracking)))
            .andExpect(status().isBadRequest());

        // Validate the TimeTracking in the database
        List<TimeTracking> timeTrackingList = timeTrackingRepository.findAll();
        assertThat(timeTrackingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeTrackingRepository.findAll().size();
        // set the field null
        timeTracking.setDate(null);

        // Create the TimeTracking, which fails.

        restTimeTrackingMockMvc.perform(post("/api/time-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeTracking)))
            .andExpect(status().isBadRequest());

        List<TimeTracking> timeTrackingList = timeTrackingRepository.findAll();
        assertThat(timeTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccessTypeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeTrackingRepository.findAll().size();
        // set the field null
        timeTracking.setAccessTypee(null);

        // Create the TimeTracking, which fails.

        restTimeTrackingMockMvc.perform(post("/api/time-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeTracking)))
            .andExpect(status().isBadRequest());

        List<TimeTracking> timeTrackingList = timeTrackingRepository.findAll();
        assertThat(timeTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTimeTrackings() throws Exception {
        // Initialize the database
        timeTrackingRepository.saveAndFlush(timeTracking);

        // Get all the timeTrackingList
        restTimeTrackingMockMvc.perform(get("/api/time-trackings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].accessTypee").value(hasItem(DEFAULT_ACCESS_TYPEE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getTimeTracking() throws Exception {
        // Initialize the database
        timeTrackingRepository.saveAndFlush(timeTracking);

        // Get the timeTracking
        restTimeTrackingMockMvc.perform(get("/api/time-trackings/{id}", timeTracking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timeTracking.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.accessTypee").value(DEFAULT_ACCESS_TYPEE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingTimeTracking() throws Exception {
        // Get the timeTracking
        restTimeTrackingMockMvc.perform(get("/api/time-trackings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeTracking() throws Exception {
        // Initialize the database
        timeTrackingRepository.saveAndFlush(timeTracking);

        int databaseSizeBeforeUpdate = timeTrackingRepository.findAll().size();

        // Update the timeTracking
        TimeTracking updatedTimeTracking = timeTrackingRepository.findById(timeTracking.getId()).get();
        // Disconnect from session so that the updates on updatedTimeTracking are not directly saved in db
        em.detach(updatedTimeTracking);
        updatedTimeTracking
            .date(UPDATED_DATE)
            .accessTypee(UPDATED_ACCESS_TYPEE)
            .description(UPDATED_DESCRIPTION);

        restTimeTrackingMockMvc.perform(put("/api/time-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTimeTracking)))
            .andExpect(status().isOk());

        // Validate the TimeTracking in the database
        List<TimeTracking> timeTrackingList = timeTrackingRepository.findAll();
        assertThat(timeTrackingList).hasSize(databaseSizeBeforeUpdate);
        TimeTracking testTimeTracking = timeTrackingList.get(timeTrackingList.size() - 1);
        assertThat(testTimeTracking.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTimeTracking.getAccessTypee()).isEqualTo(UPDATED_ACCESS_TYPEE);
        assertThat(testTimeTracking.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeTracking() throws Exception {
        int databaseSizeBeforeUpdate = timeTrackingRepository.findAll().size();

        // Create the TimeTracking

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeTrackingMockMvc.perform(put("/api/time-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(timeTracking)))
            .andExpect(status().isBadRequest());

        // Validate the TimeTracking in the database
        List<TimeTracking> timeTrackingList = timeTrackingRepository.findAll();
        assertThat(timeTrackingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimeTracking() throws Exception {
        // Initialize the database
        timeTrackingRepository.saveAndFlush(timeTracking);

        int databaseSizeBeforeDelete = timeTrackingRepository.findAll().size();

        // Delete the timeTracking
        restTimeTrackingMockMvc.perform(delete("/api/time-trackings/{id}", timeTracking.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TimeTracking> timeTrackingList = timeTrackingRepository.findAll();
        assertThat(timeTrackingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
