package com.mycompany.timesheet.web.rest;

import com.mycompany.timesheet.domain.AbsenceType;
import com.mycompany.timesheet.repository.AbsenceTypeRepository;
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
 * REST controller for managing {@link com.mycompany.timesheet.domain.AbsenceType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AbsenceTypeResource {

    private final Logger log = LoggerFactory.getLogger(AbsenceTypeResource.class);

    private static final String ENTITY_NAME = "absenceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AbsenceTypeRepository absenceTypeRepository;

    public AbsenceTypeResource(AbsenceTypeRepository absenceTypeRepository) {
        this.absenceTypeRepository = absenceTypeRepository;
    }

    /**
     * {@code POST  /absence-types} : Create a new absenceType.
     *
     * @param absenceType the absenceType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new absenceType, or with status {@code 400 (Bad Request)} if the absenceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/absence-types")
    public ResponseEntity<AbsenceType> createAbsenceType(@RequestBody AbsenceType absenceType) throws URISyntaxException {
        log.debug("REST request to save AbsenceType : {}", absenceType);
        if (absenceType.getId() != null) {
            throw new BadRequestAlertException("A new absenceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AbsenceType result = absenceTypeRepository.save(absenceType);
        return ResponseEntity.created(new URI("/api/absence-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /absence-types} : Updates an existing absenceType.
     *
     * @param absenceType the absenceType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated absenceType,
     * or with status {@code 400 (Bad Request)} if the absenceType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the absenceType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/absence-types")
    public ResponseEntity<AbsenceType> updateAbsenceType(@RequestBody AbsenceType absenceType) throws URISyntaxException {
        log.debug("REST request to update AbsenceType : {}", absenceType);
        if (absenceType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AbsenceType result = absenceTypeRepository.save(absenceType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, absenceType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /absence-types} : get all the absenceTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of absenceTypes in body.
     */
    @GetMapping("/absence-types")
    public ResponseEntity<List<AbsenceType>> getAllAbsenceTypes(Pageable pageable) {
        log.debug("REST request to get a page of AbsenceTypes");
        Page<AbsenceType> page = absenceTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /absence-types/:id} : get the "id" absenceType.
     *
     * @param id the id of the absenceType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the absenceType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/absence-types/{id}")
    public ResponseEntity<AbsenceType> getAbsenceType(@PathVariable Long id) {
        log.debug("REST request to get AbsenceType : {}", id);
        Optional<AbsenceType> absenceType = absenceTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(absenceType);
    }

    /**
     * {@code DELETE  /absence-types/:id} : delete the "id" absenceType.
     *
     * @param id the id of the absenceType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/absence-types/{id}")
    public ResponseEntity<Void> deleteAbsenceType(@PathVariable Long id) {
        log.debug("REST request to delete AbsenceType : {}", id);
        absenceTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
