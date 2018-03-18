package com.fedorizvekov.query.jpa.model.converter;

import static com.fedorizvekov.query.jpa.model.enums.ContactStatus.CONFIRMED;
import static com.fedorizvekov.query.jpa.model.enums.ContactStatus.NOT_CONFIRMED;
import static com.fedorizvekov.query.jpa.model.enums.ContactStatus.REPLACED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fedorizvekov.query.jpa.model.enums.ContactStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContactStatusConverterTest {

    @InjectMocks
    private ContactStatusConverter converter;


    @Test
    public void should_return_0() {
        byte result = converter.convertToDatabaseColumn(CONFIRMED);
        assertThat(result).isEqualTo((byte) 0);
    }


    @Test
    public void should_return_1() {
        byte result = converter.convertToDatabaseColumn(NOT_CONFIRMED);
        assertThat(result).isEqualTo((byte) 1);
    }


    @Test
    public void should_return_2() {
        byte result = converter.convertToDatabaseColumn(REPLACED);
        assertThat(result).isEqualTo((byte) 2);
    }


    @Test
    public void should_return_CONFIRMED() {
        ContactStatus result = converter.convertToEntityAttribute((byte) 0);
        assertThat(result).isEqualTo(CONFIRMED);
    }


    @Test
    public void should_return_NOT_CONFIRMED() {
        ContactStatus result = converter.convertToEntityAttribute((byte) 1);
        assertThat(result).isEqualTo(NOT_CONFIRMED);
    }


    @Test
    public void should_return_REPLACED() {
        ContactStatus result = converter.convertToEntityAttribute((byte) 2);
        assertThat(result).isEqualTo(REPLACED);
    }


    @Test
    public void should_thrown_IllegalArgumentException() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute((byte) 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid CredentialStatus value: '3', need: 0 = CONFIRMED, 1 = NOT_CONFIRMED, 2 = REPLACED");
    }

}
