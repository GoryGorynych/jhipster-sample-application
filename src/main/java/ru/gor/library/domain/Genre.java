package ru.gor.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Genre.
 */
@Document(collection = "genre")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Genre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("genre_name")
    private String genreName;

    @DBRef
    @Field("book")
    @JsonIgnoreProperties(value = { "comments", "author", "genre" }, allowSetters = true)
    private Set<Book> books = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Genre id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenreName() {
        return this.genreName;
    }

    public Genre genreName(String genreName) {
        this.setGenreName(genreName);
        return this;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Set<Book> getBooks() {
        return this.books;
    }

    public void setBooks(Set<Book> books) {
        if (this.books != null) {
            this.books.forEach(i -> i.setGenre(null));
        }
        if (books != null) {
            books.forEach(i -> i.setGenre(this));
        }
        this.books = books;
    }

    public Genre books(Set<Book> books) {
        this.setBooks(books);
        return this;
    }

    public Genre addBook(Book book) {
        this.books.add(book);
        book.setGenre(this);
        return this;
    }

    public Genre removeBook(Book book) {
        this.books.remove(book);
        book.setGenre(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Genre)) {
            return false;
        }
        return getId() != null && getId().equals(((Genre) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Genre{" +
            "id=" + getId() +
            ", genreName='" + getGenreName() + "'" +
            "}";
    }
}
