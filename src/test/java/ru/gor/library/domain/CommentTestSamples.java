package ru.gor.library.domain;

import java.util.UUID;

public class CommentTestSamples {

    public static Comment getCommentSample1() {
        return new Comment().id("id1").comment("comment1");
    }

    public static Comment getCommentSample2() {
        return new Comment().id("id2").comment("comment2");
    }

    public static Comment getCommentRandomSampleGenerator() {
        return new Comment().id(UUID.randomUUID().toString()).comment(UUID.randomUUID().toString());
    }
}
