package ru.gor.library.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gor.library.domain.PersistentToken;
import ru.gor.library.domain.User;

/**
 * Spring Data MongoDB repository for the {@link PersistentToken} entity.
 */
public interface PersistentTokenRepository extends MongoRepository<PersistentToken, String> {
    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);
}
