package ru.gor.library.domain;

import java.util.UUID;

public class GenreTestSamples {

    public static Genre getGenreSample1() {
        return new Genre().id("id1").genreName("genreName1");
    }

    public static Genre getGenreSample2() {
        return new Genre().id("id2").genreName("genreName2");
    }

    public static Genre getGenreRandomSampleGenerator() {
        return new Genre().id(UUID.randomUUID().toString()).genreName(UUID.randomUUID().toString());
    }
}
