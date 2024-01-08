package ru.gor.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gor.library.domain.Authority;

/**
 * Spring Data MongoDB repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {}
