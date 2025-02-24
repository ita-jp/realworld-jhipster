package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TagAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTagAllPropertiesEquals(Tag expected, Tag actual) {
        assertTagAutoGeneratedPropertiesEquals(expected, actual);
        assertTagAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTagAllUpdatablePropertiesEquals(Tag expected, Tag actual) {
        assertTagUpdatableFieldsEquals(expected, actual);
        assertTagUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTagAutoGeneratedPropertiesEquals(Tag expected, Tag actual) {
        assertThat(expected)
            .as("Verify Tag auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTagUpdatableFieldsEquals(Tag expected, Tag actual) {
        assertThat(expected)
            .as("Verify Tag relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTagUpdatableRelationshipsEquals(Tag expected, Tag actual) {
        assertThat(expected)
            .as("Verify Tag relationships")
            .satisfies(e -> assertThat(e.getArticles()).as("check articles").isEqualTo(actual.getArticles()));
    }
}
