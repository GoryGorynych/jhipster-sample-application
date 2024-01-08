package ru.gor.library.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.gor.library.domain.AuthorTestSamples.*;
import static ru.gor.library.domain.BookTestSamples.*;
import static ru.gor.library.domain.GenreTestSamples.*;

import org.junit.jupiter.api.Test;
import ru.gor.library.web.rest.TestUtil;

class BookTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Book.class);
        Book book1 = getBookSample1();
        Book book2 = new Book();
        assertThat(book1).isNotEqualTo(book2);

        book2.setId(book1.getId());
        assertThat(book1).isEqualTo(book2);

        book2 = getBookSample2();
        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    void authorTest() throws Exception {
        Book book = getBookRandomSampleGenerator();
        Author authorBack = getAuthorRandomSampleGenerator();

        book.setAuthor(authorBack);
        assertThat(book.getAuthor()).isEqualTo(authorBack);

        book.author(null);
        assertThat(book.getAuthor()).isNull();
    }

    @Test
    void genreTest() throws Exception {
        Book book = getBookRandomSampleGenerator();
        Genre genreBack = getGenreRandomSampleGenerator();

        book.setGenre(genreBack);
        assertThat(book.getGenre()).isEqualTo(genreBack);

        book.genre(null);
        assertThat(book.getGenre()).isNull();
    }
}
