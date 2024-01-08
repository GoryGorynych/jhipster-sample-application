package ru.gor.library.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.gor.library.IntegrationTest;
import ru.gor.library.domain.Genre;
import ru.gor.library.repository.GenreRepository;

/**
 * Integration tests for the {@link GenreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GenreResourceIT {

    private static final String DEFAULT_GENRE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GENRE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/genres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MockMvc restGenreMockMvc;

    private Genre genre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Genre createEntity() {
        Genre genre = new Genre().genreName(DEFAULT_GENRE_NAME);
        return genre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Genre createUpdatedEntity() {
        Genre genre = new Genre().genreName(UPDATED_GENRE_NAME);
        return genre;
    }

    @BeforeEach
    public void initTest() {
        genreRepository.deleteAll();
        genre = createEntity();
    }

    @Test
    void createGenre() throws Exception {
        int databaseSizeBeforeCreate = genreRepository.findAll().size();
        // Create the Genre
        restGenreMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(genre))
            )
            .andExpect(status().isCreated());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeCreate + 1);
        Genre testGenre = genreList.get(genreList.size() - 1);
        assertThat(testGenre.getGenreName()).isEqualTo(DEFAULT_GENRE_NAME);
    }

    @Test
    void createGenreWithExistingId() throws Exception {
        // Create the Genre with an existing ID
        genre.setId("existing_id");

        int databaseSizeBeforeCreate = genreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenreMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(genre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllGenres() throws Exception {
        // Initialize the database
        genreRepository.save(genre);

        // Get all the genreList
        restGenreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genre.getId())))
            .andExpect(jsonPath("$.[*].genreName").value(hasItem(DEFAULT_GENRE_NAME)));
    }

    @Test
    void getGenre() throws Exception {
        // Initialize the database
        genreRepository.save(genre);

        // Get the genre
        restGenreMockMvc
            .perform(get(ENTITY_API_URL_ID, genre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(genre.getId()))
            .andExpect(jsonPath("$.genreName").value(DEFAULT_GENRE_NAME));
    }

    @Test
    void getNonExistingGenre() throws Exception {
        // Get the genre
        restGenreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingGenre() throws Exception {
        // Initialize the database
        genreRepository.save(genre);

        int databaseSizeBeforeUpdate = genreRepository.findAll().size();

        // Update the genre
        Genre updatedGenre = genreRepository.findById(genre.getId()).orElseThrow();
        updatedGenre.genreName(UPDATED_GENRE_NAME);

        restGenreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGenre.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGenre))
            )
            .andExpect(status().isOk());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeUpdate);
        Genre testGenre = genreList.get(genreList.size() - 1);
        assertThat(testGenre.getGenreName()).isEqualTo(UPDATED_GENRE_NAME);
    }

    @Test
    void putNonExistingGenre() throws Exception {
        int databaseSizeBeforeUpdate = genreRepository.findAll().size();
        genre.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, genre.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(genre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchGenre() throws Exception {
        int databaseSizeBeforeUpdate = genreRepository.findAll().size();
        genre.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(genre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamGenre() throws Exception {
        int databaseSizeBeforeUpdate = genreRepository.findAll().size();
        genre.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenreMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(genre))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateGenreWithPatch() throws Exception {
        // Initialize the database
        genreRepository.save(genre);

        int databaseSizeBeforeUpdate = genreRepository.findAll().size();

        // Update the genre using partial update
        Genre partialUpdatedGenre = new Genre();
        partialUpdatedGenre.setId(genre.getId());

        partialUpdatedGenre.genreName(UPDATED_GENRE_NAME);

        restGenreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGenre.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGenre))
            )
            .andExpect(status().isOk());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeUpdate);
        Genre testGenre = genreList.get(genreList.size() - 1);
        assertThat(testGenre.getGenreName()).isEqualTo(UPDATED_GENRE_NAME);
    }

    @Test
    void fullUpdateGenreWithPatch() throws Exception {
        // Initialize the database
        genreRepository.save(genre);

        int databaseSizeBeforeUpdate = genreRepository.findAll().size();

        // Update the genre using partial update
        Genre partialUpdatedGenre = new Genre();
        partialUpdatedGenre.setId(genre.getId());

        partialUpdatedGenre.genreName(UPDATED_GENRE_NAME);

        restGenreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGenre.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGenre))
            )
            .andExpect(status().isOk());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeUpdate);
        Genre testGenre = genreList.get(genreList.size() - 1);
        assertThat(testGenre.getGenreName()).isEqualTo(UPDATED_GENRE_NAME);
    }

    @Test
    void patchNonExistingGenre() throws Exception {
        int databaseSizeBeforeUpdate = genreRepository.findAll().size();
        genre.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, genre.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(genre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchGenre() throws Exception {
        int databaseSizeBeforeUpdate = genreRepository.findAll().size();
        genre.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(genre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamGenre() throws Exception {
        int databaseSizeBeforeUpdate = genreRepository.findAll().size();
        genre.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenreMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(genre))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Genre in the database
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteGenre() throws Exception {
        // Initialize the database
        genreRepository.save(genre);

        int databaseSizeBeforeDelete = genreRepository.findAll().size();

        // Delete the genre
        restGenreMockMvc
            .perform(delete(ENTITY_API_URL_ID, genre.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
