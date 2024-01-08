package ru.gor.library.domain;

import java.util.UUID;

public class BookTestSamples {

    public static Book getBookSample1() {
        return new Book().id("id1").name("name1").description("description1");
    }

    public static Book getBookSample2() {
        return new Book().id("id2").name("name2").description("description2");
    }

    public static Book getBookRandomSampleGenerator() {
        return new Book().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString()).description(UUID.randomUUID().toString());
    }
}
