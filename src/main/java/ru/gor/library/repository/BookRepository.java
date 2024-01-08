package ru.gor.library.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gor.library.domain.Book;

/**
 * Spring Data MongoDB repository for the Book entity.
 */
@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    @Query("{}")
    Page<Book> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Book> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Book> findOneWithEagerRelationships(String id);
}
