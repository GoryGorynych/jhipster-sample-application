package ru.gor.library.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gor.library.domain.Book;

/**
 * Service Interface for managing {@link ru.gor.library.domain.Book}.
 */
public interface BookService {
    /**
     * Save a book.
     *
     * @param book the entity to save.
     * @return the persisted entity.
     */
    Book save(Book book);

    /**
     * Updates a book.
     *
     * @param book the entity to update.
     * @return the persisted entity.
     */
    Book update(Book book);

    /**
     * Partially updates a book.
     *
     * @param book the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Book> partialUpdate(Book book);

    /**
     * Get all the books.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Book> findAll(Pageable pageable);

    /**
     * Get all the books with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Book> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" book.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Book> findOne(String id);

    /**
     * Delete the "id" book.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
