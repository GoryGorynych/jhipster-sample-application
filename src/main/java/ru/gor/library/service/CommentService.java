package ru.gor.library.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gor.library.domain.Comment;

/**
 * Service Interface for managing {@link ru.gor.library.domain.Comment}.
 */
public interface CommentService {
    /**
     * Save a comment.
     *
     * @param comment the entity to save.
     * @return the persisted entity.
     */
    Comment save(Comment comment);

    /**
     * Updates a comment.
     *
     * @param comment the entity to update.
     * @return the persisted entity.
     */
    Comment update(Comment comment);

    /**
     * Partially updates a comment.
     *
     * @param comment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Comment> partialUpdate(Comment comment);

    /**
     * Get all the comments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Comment> findAll(Pageable pageable);

    /**
     * Get all the comments with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Comment> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" comment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Comment> findOne(String id);

    /**
     * Delete the "id" comment.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
