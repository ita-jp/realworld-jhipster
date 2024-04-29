package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.UserExtendedAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.UserExtended;
import com.mycompany.myapp.repository.UserExtendedRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserExtendedResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UserExtendedResourceIT {

    private static final String ENTITY_API_URL = "/api/user-extendeds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserExtendedRepository userExtendedRepository;

    @Mock
    private UserExtendedRepository userExtendedRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserExtendedMockMvc;

    private UserExtended userExtended;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtended createEntity(EntityManager em) {
        UserExtended userExtended = new UserExtended();
        return userExtended;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtended createUpdatedEntity(EntityManager em) {
        UserExtended userExtended = new UserExtended();
        return userExtended;
    }

    @BeforeEach
    public void initTest() {
        userExtended = createEntity(em);
    }

    @Test
    @Transactional
    void createUserExtended() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the UserExtended
        var returnedUserExtended = om.readValue(
            restUserExtendedMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userExtended))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UserExtended.class
        );

        // Validate the UserExtended in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertUserExtendedUpdatableFieldsEquals(returnedUserExtended, getPersistedUserExtended(returnedUserExtended));
    }

    @Test
    @Transactional
    void createUserExtendedWithExistingId() throws Exception {
        // Create the UserExtended with an existing ID
        userExtended.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtendedMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userExtended)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtended in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserExtendeds() throws Exception {
        // Initialize the database
        userExtendedRepository.saveAndFlush(userExtended);

        // Get all the userExtendedList
        restUserExtendedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtended.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserExtendedsWithEagerRelationshipsIsEnabled() throws Exception {
        when(userExtendedRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserExtendedMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userExtendedRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserExtendedsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userExtendedRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserExtendedMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(userExtendedRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getUserExtended() throws Exception {
        // Initialize the database
        userExtendedRepository.saveAndFlush(userExtended);

        // Get the userExtended
        restUserExtendedMockMvc
            .perform(get(ENTITY_API_URL_ID, userExtended.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userExtended.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserExtended() throws Exception {
        // Get the userExtended
        restUserExtendedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserExtended() throws Exception {
        // Initialize the database
        userExtendedRepository.saveAndFlush(userExtended);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userExtended
        UserExtended updatedUserExtended = userExtendedRepository.findById(userExtended.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUserExtended are not directly saved in db
        em.detach(updatedUserExtended);

        restUserExtendedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserExtended.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedUserExtended))
            )
            .andExpect(status().isOk());

        // Validate the UserExtended in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUserExtendedToMatchAllProperties(updatedUserExtended);
    }

    @Test
    @Transactional
    void putNonExistingUserExtended() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userExtended.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtendedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userExtended.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(userExtended))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtended in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserExtended() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userExtended.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtendedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(userExtended))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtended in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserExtended() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userExtended.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtendedMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userExtended)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserExtended in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserExtendedWithPatch() throws Exception {
        // Initialize the database
        userExtendedRepository.saveAndFlush(userExtended);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userExtended using partial update
        UserExtended partialUpdatedUserExtended = new UserExtended();
        partialUpdatedUserExtended.setId(userExtended.getId());

        restUserExtendedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserExtended.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUserExtended))
            )
            .andExpect(status().isOk());

        // Validate the UserExtended in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUserExtendedUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUserExtended, userExtended),
            getPersistedUserExtended(userExtended)
        );
    }

    @Test
    @Transactional
    void fullUpdateUserExtendedWithPatch() throws Exception {
        // Initialize the database
        userExtendedRepository.saveAndFlush(userExtended);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userExtended using partial update
        UserExtended partialUpdatedUserExtended = new UserExtended();
        partialUpdatedUserExtended.setId(userExtended.getId());

        restUserExtendedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserExtended.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUserExtended))
            )
            .andExpect(status().isOk());

        // Validate the UserExtended in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUserExtendedUpdatableFieldsEquals(partialUpdatedUserExtended, getPersistedUserExtended(partialUpdatedUserExtended));
    }

    @Test
    @Transactional
    void patchNonExistingUserExtended() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userExtended.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtendedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userExtended.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(userExtended))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtended in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserExtended() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userExtended.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtendedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(userExtended))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtended in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserExtended() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userExtended.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtendedMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(userExtended))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserExtended in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserExtended() throws Exception {
        // Initialize the database
        userExtendedRepository.saveAndFlush(userExtended);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the userExtended
        restUserExtendedMockMvc
            .perform(delete(ENTITY_API_URL_ID, userExtended.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return userExtendedRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected UserExtended getPersistedUserExtended(UserExtended userExtended) {
        return userExtendedRepository.findById(userExtended.getId()).orElseThrow();
    }

    protected void assertPersistedUserExtendedToMatchAllProperties(UserExtended expectedUserExtended) {
        assertUserExtendedAllPropertiesEquals(expectedUserExtended, getPersistedUserExtended(expectedUserExtended));
    }

    protected void assertPersistedUserExtendedToMatchUpdatableProperties(UserExtended expectedUserExtended) {
        assertUserExtendedAllUpdatablePropertiesEquals(expectedUserExtended, getPersistedUserExtended(expectedUserExtended));
    }
}
