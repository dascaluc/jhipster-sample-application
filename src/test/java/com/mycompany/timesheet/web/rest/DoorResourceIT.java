package com.mycompany.timesheet.web.rest;

import com.mycompany.timesheet.JhipsterSampleApplicationApp;
import com.mycompany.timesheet.domain.Door;
import com.mycompany.timesheet.repository.DoorRepository;

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
 * Integration tests for the {@link DoorResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class DoorResourceIT {

    private static final String DEFAULT_DOOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOOR_NAME = "BBBBBBBBBB";

    @Autowired
    private DoorRepository doorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoorMockMvc;

    private Door door;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Door createEntity(EntityManager em) {
        Door door = new Door()
            .doorName(DEFAULT_DOOR_NAME);
        return door;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Door createUpdatedEntity(EntityManager em) {
        Door door = new Door()
            .doorName(UPDATED_DOOR_NAME);
        return door;
    }

    @BeforeEach
    public void initTest() {
        door = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoor() throws Exception {
        int databaseSizeBeforeCreate = doorRepository.findAll().size();

        // Create the Door
        restDoorMockMvc.perform(post("/api/doors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(door)))
            .andExpect(status().isCreated());

        // Validate the Door in the database
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeCreate + 1);
        Door testDoor = doorList.get(doorList.size() - 1);
        assertThat(testDoor.getDoorName()).isEqualTo(DEFAULT_DOOR_NAME);
    }

    @Test
    @Transactional
    public void createDoorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doorRepository.findAll().size();

        // Create the Door with an existing ID
        door.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoorMockMvc.perform(post("/api/doors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(door)))
            .andExpect(status().isBadRequest());

        // Validate the Door in the database
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDoors() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        // Get all the doorList
        restDoorMockMvc.perform(get("/api/doors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(door.getId().intValue())))
            .andExpect(jsonPath("$.[*].doorName").value(hasItem(DEFAULT_DOOR_NAME)));
    }
    
    @Test
    @Transactional
    public void getDoor() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        // Get the door
        restDoorMockMvc.perform(get("/api/doors/{id}", door.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(door.getId().intValue()))
            .andExpect(jsonPath("$.doorName").value(DEFAULT_DOOR_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingDoor() throws Exception {
        // Get the door
        restDoorMockMvc.perform(get("/api/doors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoor() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        int databaseSizeBeforeUpdate = doorRepository.findAll().size();

        // Update the door
        Door updatedDoor = doorRepository.findById(door.getId()).get();
        // Disconnect from session so that the updates on updatedDoor are not directly saved in db
        em.detach(updatedDoor);
        updatedDoor
            .doorName(UPDATED_DOOR_NAME);

        restDoorMockMvc.perform(put("/api/doors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoor)))
            .andExpect(status().isOk());

        // Validate the Door in the database
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeUpdate);
        Door testDoor = doorList.get(doorList.size() - 1);
        assertThat(testDoor.getDoorName()).isEqualTo(UPDATED_DOOR_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDoor() throws Exception {
        int databaseSizeBeforeUpdate = doorRepository.findAll().size();

        // Create the Door

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoorMockMvc.perform(put("/api/doors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(door)))
            .andExpect(status().isBadRequest());

        // Validate the Door in the database
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoor() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        int databaseSizeBeforeDelete = doorRepository.findAll().size();

        // Delete the door
        restDoorMockMvc.perform(delete("/api/doors/{id}", door.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
