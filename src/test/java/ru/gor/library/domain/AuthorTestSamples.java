package ru.gor.library.domain;

import java.util.UUID;

public class AuthorTestSamples {

    public static Author getAuthorSample1() {
        return new Author().id("id1").firstName("firstName1").secondName("secondName1");
    }

    public static Author getAuthorSample2() {
        return new Author().id("id2").firstName("firstName2").secondName("secondName2");
    }

    public static Author getAuthorRandomSampleGenerator() {
        return new Author()
            .id(UUID.randomUUID().toString())
            .firstName(UUID.randomUUID().toString())
            .secondName(UUID.randomUUID().toString());
    }
}
