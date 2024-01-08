package ru.gor.library.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.gor.library.domain.BookTestSamples.*;
import static ru.gor.library.domain.GenreTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import ru.gor.library.web.rest.TestUtil;

class GenreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Genre.class);
        Genre genre1 = getGenreSample1();
        Genre genre2 = new Genre();
        assertThat(genre1).isNotEqualTo(genre2);

        genre2.setId(genre1.getId());
        assertThat(genre1).isEqualTo(genre2);

        genre2 = getGenreSample2();
        assertThat(genre1).isNotEqualTo(genre2);
    }

    @Test
    void bookTest() throws Exception {
        Genre genre = getGenreRandomSampleGenerator();
        Book bookBack = getBookRandomSampleGenerator();

        genre.addBook(bookBack);
        assertThat(genre.getBooks()).containsOnly(bookBack);
        assertThat(bookBack.getGenre()).isEqualTo(genre);

        genre.removeBook(bookBack);
        assertThat(genre.getBooks()).doesNotContain(bookBack);
        assertThat(bookBack.getGenre()).isNull();

        genre.books(new HashSet<>(Set.of(bookBack)));
        assertThat(genre.getBooks()).containsOnly(bookBack);
        assertThat(bookBack.getGenre()).isEqualTo(genre);

        genre.setBooks(new HashSet<>());
        assertThat(genre.getBooks()).doesNotContain(bookBack);
        assertThat(bookBack.getGenre()).isNull();
    }
}
