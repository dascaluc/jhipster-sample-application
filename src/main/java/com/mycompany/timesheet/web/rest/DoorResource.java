package com.mycompany.timesheet.web.rest;

import com.mycompany.timesheet.domain.Door;
import com.mycompany.timesheet.repository.DoorRepository;
import com.mycompany.timesheet.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.timesheet.domain.Door}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DoorResource {

    private final Logger log = LoggerFactory.getLogger(DoorResource.class);

    private static final String ENTITY_NAME = "door";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoorRepository doorRepository;

    public DoorResource(DoorRepository doorRepository) {
        this.doorRepository = doorRepository;
    }

    /**
     * {@code POST  /doors} : Create a new door.
     *
     * @param door the door to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new door, or with status {@code 400 (Bad Request)} if the door has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doors")
    public ResponseEntity<Door> createDoor(@RequestBody Door door) throws URISyntaxException {
        log.debug("REST request to save Door : {}", door);
        if (door.getId() != null) {
            throw new BadRequestAlertException("A new door cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Door result = doorRepository.save(door);
        return ResponseEntity.created(new URI("/api/doors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doors} : Updates an existing door.
     *
     * @param door the door to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated door,
     * or with status {@code 400 (Bad Request)} if the door is not valid,
     * or with status {@code 500 (Internal Server Error)} if the door couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doors")
    public ResponseEntity<Door> updateDoor(@RequestBody Door door) throws URISyntaxException {
        log.debug("REST request to update Door : {}", door);
        if (door.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Door result = doorRepository.save(door);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, door.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doors} : get all the doors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doors in body.
     */
    @GetMapping("/doors")
    public ResponseEntity<List<Door>> getAllDoors(Pageable pageable) {
        log.debug("REST request to get a page of Doors");
        Page<Door> page = doorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /doors/:id} : get the "id" door.
     *
     * @param id the id of the door to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the door, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doors/{id}")
    public ResponseEntity<Door> getDoor(@PathVariable Long id) {
        log.debug("REST request to get Door : {}", id);
        Optional<Door> door = doorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(door);
    }

    /**
     * {@code DELETE  /doors/:id} : delete the "id" door.
     *
     * @param id the id of the door to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doors/{id}")
    public ResponseEntity<Void> deleteDoor(@PathVariable Long id) {
        log.debug("REST request to delete Door : {}", id);
        doorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
