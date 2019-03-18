package com.fedorizvekov.query.data.rest.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContactTest {

    @DisplayName("Should equals and hash code by id")
    @Test
    void should_equalsAndHashCode_byId() {
        var contact = Contact.builder().contactId(1L).value("test_email@email.com").build();
        var contactWithSameId = Contact.builder().contactId(1L).value("test2@email.com").build();

        assertAll(
                () -> assertThat(contact).isEqualTo(contactWithSameId),
                () -> assertThat(contact.hashCode()).isEqualTo(contactWithSameId.hashCode())
        );
    }


    @DisplayName("Should equals and hash code by value")
    @Test
    void should_equalsAndHashCode_byValue() {
        var contact = Contact.builder().value("test_email@email.com").build();
        var contactWithSameValue = Contact.builder().value("test_email@email.com").build();

        assertAll(
                () -> assertThat(contact).isEqualTo(contactWithSameValue),
                () -> assertThat(contact.hashCode()).isEqualTo(contactWithSameValue.hashCode())
        );
    }


    @DisplayName("Should not equals and hash code by id")
    @Test
    void should_notEqualsAndHashCode_byId() {
        var contact = Contact.builder().contactId(1L).value("test_email@email.com").build();
        var contactWithDifferentId = Contact.builder().contactId(2L).value("test_email@email.com").build();

        assertAll(
                () -> assertThat(contact).isNotEqualTo(contactWithDifferentId),
                () -> assertThat(contact.hashCode()).isNotEqualTo(contactWithDifferentId.hashCode())
        );

    }


    @DisplayName("Should not equals and hash code by value")
    @Test
    void should_notEqualsAndHashCode_byValue() {
        var contact = Contact.builder().value("test_email@email.com").build();
        var contactWithDifferentValue = Contact.builder().value("test2@email.com").build();

        assertAll(
                () -> assertThat(contact).isNotEqualTo(contactWithDifferentValue),
                () -> assertThat(contact.hashCode()).isNotEqualTo(contactWithDifferentValue.hashCode())
        );

    }

}
