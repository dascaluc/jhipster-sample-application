package com.mycompany.timesheet.web.rest;

import com.mycompany.timesheet.domain.TimeTracking;
import com.mycompany.timesheet.repository.TimeTrackingRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.timesheet.domain.TimeTracking}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TimeTrackingResource {

    private final Logger log = LoggerFactory.getLogger(TimeTrackingResource.class);

    private static final String ENTITY_NAME = "timeTracking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeTrackingRepository timeTrackingRepository;

    public TimeTrackingResource(TimeTrackingRepository timeTrackingRepository) {
        this.timeTrackingRepository = timeTrackingRepository;
    }

    /**
     * {@code POST  /time-trackings} : Create a new timeTracking.
     *
     * @param timeTracking the timeTracking to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timeTracking, or with status {@code 400 (Bad Request)} if the timeTracking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/time-trackings")
    public ResponseEntity<TimeTracking> createTimeTracking(@Valid @RequestBody TimeTracking timeTracking) throws URISyntaxException {
        log.debug("REST request to save TimeTracking : {}", timeTracking);
        if (timeTracking.getId() != null) {
            throw new BadRequestAlertException("A new timeTracking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeTracking result = timeTrackingRepository.save(timeTracking);
        return ResponseEntity.created(new URI("/api/time-trackings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /time-trackings} : Updates an existing timeTracking.
     *
     * @param timeTracking the timeTracking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeTracking,
     * or with status {@code 400 (Bad Request)} if the timeTracking is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timeTracking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/time-trackings")
    public ResponseEntity<TimeTracking> updateTimeTracking(@Valid @RequestBody TimeTracking timeTracking) throws URISyntaxException {
        log.debug("REST request to update TimeTracking : {}", timeTracking);
        if (timeTracking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeTracking result = timeTrackingRepository.save(timeTracking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, timeTracking.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /time-trackings} : get all the timeTrackings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeTrackings in body.
     */
    @GetMapping("/time-trackings")
    public ResponseEntity<List<TimeTracking>> getAllTimeTrackings(Pageable pageable) {
        log.debug("REST request to get a page of TimeTrackings");
        Page<TimeTracking> page = timeTrackingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /time-trackings/:id} : get the "id" timeTracking.
     *
     * @param id the id of the timeTracking to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timeTracking, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/time-trackings/{id}")
    public ResponseEntity<TimeTracking> getTimeTracking(@PathVariable Long id) {
        log.debug("REST request to get TimeTracking : {}", id);
        Optional<TimeTracking> timeTracking = timeTrackingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(timeTracking);
    }

    /**
     * {@code DELETE  /time-trackings/:id} : delete the "id" timeTracking.
     *
     * @param id the id of the timeTracking to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/time-trackings/{id}")
    public ResponseEntity<Void> deleteTimeTracking(@PathVariable Long id) {
        log.debug("REST request to delete TimeTracking : {}", id);
        timeTrackingRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
