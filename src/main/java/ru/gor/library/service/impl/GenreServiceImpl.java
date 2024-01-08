package ru.gor.library.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.gor.library.domain.Genre;
import ru.gor.library.repository.GenreRepository;
import ru.gor.library.service.GenreService;

/**
 * Service Implementation for managing {@link ru.gor.library.domain.Genre}.
 */
@Service
public class GenreServiceImpl implements GenreService {

    private final Logger log = LoggerFactory.getLogger(GenreServiceImpl.class);

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre save(Genre genre) {
        log.debug("Request to save Genre : {}", genre);
        return genreRepository.save(genre);
    }

    @Override
    public Genre update(Genre genre) {
        log.debug("Request to update Genre : {}", genre);
        return genreRepository.save(genre);
    }

    @Override
    public Optional<Genre> partialUpdate(Genre genre) {
        log.debug("Request to partially update Genre : {}", genre);

        return genreRepository
            .findById(genre.getId())
            .map(existingGenre -> {
                if (genre.getGenreName() != null) {
                    existingGenre.setGenreName(genre.getGenreName());
                }

                return existingGenre;
            })
            .map(genreRepository::save);
    }

    @Override
    public Page<Genre> findAll(Pageable pageable) {
        log.debug("Request to get all Genres");
        return genreRepository.findAll(pageable);
    }

    @Override
    public Optional<Genre> findOne(String id) {
        log.debug("Request to get Genre : {}", id);
        return genreRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Genre : {}", id);
        genreRepository.deleteById(id);
    }
}
