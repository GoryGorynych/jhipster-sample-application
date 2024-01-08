package ru.gor.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.gor.library.domain.Author;

/**
 * Spring Data MongoDB repository for the Author entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {}
