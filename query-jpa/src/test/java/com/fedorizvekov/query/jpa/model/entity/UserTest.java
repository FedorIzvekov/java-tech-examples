package com.fedorizvekov.query.jpa.model.entity;

import static com.fedorizvekov.query.jpa.model.enums.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @DisplayName("Should equals and hash code by id")
    @Test
    void should_equalsAndHashCode_byId() {
        var user = User.builder().userId(1L).firstName("test_firstName").build();
        var userWithSameId = User.builder().userId(1L).firstName("test").build();

        assertAll(
                () -> assertThat(user).isEqualTo(userWithSameId),
                () -> assertThat(user.hashCode()).isEqualTo(userWithSameId.hashCode())
        );
    }


    @DisplayName("Should equals and hash code by values")
    @Test
    void should_equalsAndHashCode_byValues() {
        var user = User.builder().firstName("Test").build();
        var userWithSameValues = User.builder().firstName("Test").build();

        assertAll(
                () -> assertThat(user).isEqualTo(userWithSameValues),
                () -> assertThat(user.hashCode()).isEqualTo(userWithSameValues.hashCode())
        );
    }


    @DisplayName("Should not equals and hash code by id")
    @Test
    void should_notEqualsAndHashCode_byId() {
        var user = User.builder().userId(1L).firstName("test").build();
        var userWithDifferentId = User.builder().userId(2L).firstName("test").build();

        assertAll(
                () -> assertThat(user).isNotEqualTo(userWithDifferentId),
                () -> assertThat(user.hashCode()).isNotEqualTo(userWithDifferentId.hashCode())
        );
    }


    @DisplayName("Should not equals and hash code by values")
    @Test
    void should_notEqualsAndHashCode_byValues() {
        var user = User.builder().firstName("Test").gender(MALE).build();
        var userWithSameValues = User.builder().firstName("Test").build();

        assertAll(
                () -> assertThat(user).isNotEqualTo(userWithSameValues),
                () -> assertThat(user.hashCode()).isNotEqualTo(userWithSameValues.hashCode())
        );
    }

}
