package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.UserExtended;
import com.mycompany.myapp.repository.UserExtendedRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.UserExtended}.
 */
@RestController
@RequestMapping("/api/user-extendeds")
@Transactional
public class UserExtendedResource {

    private final Logger log = LoggerFactory.getLogger(UserExtendedResource.class);

    private static final String ENTITY_NAME = "userExtended";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserExtendedRepository userExtendedRepository;

    public UserExtendedResource(UserExtendedRepository userExtendedRepository) {
        this.userExtendedRepository = userExtendedRepository;
    }

    /**
     * {@code POST  /user-extendeds} : Create a new userExtended.
     *
     * @param userExtended the userExtended to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userExtended, or with status {@code 400 (Bad Request)} if the userExtended has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UserExtended> createUserExtended(@RequestBody UserExtended userExtended) throws URISyntaxException {
        log.debug("REST request to save UserExtended : {}", userExtended);
        if (userExtended.getId() != null) {
            throw new BadRequestAlertException("A new userExtended cannot already have an ID", ENTITY_NAME, "idexists");
        }
        userExtended = userExtendedRepository.save(userExtended);
        return ResponseEntity.created(new URI("/api/user-extendeds/" + userExtended.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, userExtended.getId().toString()))
            .body(userExtended);
    }

    /**
     * {@code PUT  /user-extendeds/:id} : Updates an existing userExtended.
     *
     * @param id the id of the userExtended to save.
     * @param userExtended the userExtended to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userExtended,
     * or with status {@code 400 (Bad Request)} if the userExtended is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userExtended couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserExtended> updateUserExtended(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserExtended userExtended
    ) throws URISyntaxException {
        log.debug("REST request to update UserExtended : {}, {}", id, userExtended);
        if (userExtended.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userExtended.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userExtendedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        userExtended = userExtendedRepository.save(userExtended);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userExtended.getId().toString()))
            .body(userExtended);
    }

    /**
     * {@code PATCH  /user-extendeds/:id} : Partial updates given fields of an existing userExtended, field will ignore if it is null
     *
     * @param id the id of the userExtended to save.
     * @param userExtended the userExtended to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userExtended,
     * or with status {@code 400 (Bad Request)} if the userExtended is not valid,
     * or with status {@code 404 (Not Found)} if the userExtended is not found,
     * or with status {@code 500 (Internal Server Error)} if the userExtended couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserExtended> partialUpdateUserExtended(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserExtended userExtended
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserExtended partially : {}, {}", id, userExtended);
        if (userExtended.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userExtended.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userExtendedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserExtended> result = userExtendedRepository.findById(userExtended.getId()).map(userExtendedRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userExtended.getId().toString())
        );
    }

    /**
     * {@code GET  /user-extendeds} : get all the userExtendeds.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userExtendeds in body.
     */
    @GetMapping("")
    public List<UserExtended> getAllUserExtendeds(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all UserExtendeds");
        if (eagerload) {
            return userExtendedRepository.findAllWithEagerRelationships();
        } else {
            return userExtendedRepository.findAll();
        }
    }

    /**
     * {@code GET  /user-extendeds/:id} : get the "id" userExtended.
     *
     * @param id the id of the userExtended to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userExtended, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserExtended> getUserExtended(@PathVariable("id") Long id) {
        log.debug("REST request to get UserExtended : {}", id);
        Optional<UserExtended> userExtended = userExtendedRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(userExtended);
    }

    /**
     * {@code DELETE  /user-extendeds/:id} : delete the "id" userExtended.
     *
     * @param id the id of the userExtended to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserExtended(@PathVariable("id") Long id) {
        log.debug("REST request to delete UserExtended : {}", id);
        userExtendedRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
