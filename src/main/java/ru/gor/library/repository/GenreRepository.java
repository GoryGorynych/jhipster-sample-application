package ru.gor.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.gor.library.domain.Genre;

/**
 * Spring Data MongoDB repository for the Genre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {}
