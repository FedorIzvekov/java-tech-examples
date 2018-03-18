package com.fedorizvekov.query.jpa.model.converter;

import static com.fedorizvekov.query.jpa.model.enums.Gender.FEMALE;
import static com.fedorizvekov.query.jpa.model.enums.Gender.MALE;
import static com.fedorizvekov.query.jpa.model.enums.Gender.NOT_DEFINED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fedorizvekov.query.jpa.model.enums.Gender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GenderConverterTest {

    @InjectMocks
    private GenderConverter converter;


    @Test
    public void should_return_0() {
        byte result = converter.convertToDatabaseColumn(NOT_DEFINED);
        assertThat(result).isEqualTo((byte) 0);
    }


    @Test
    public void should_return_1() {
        byte result = converter.convertToDatabaseColumn(MALE);
        assertThat(result).isEqualTo((byte) 1);
    }


    @Test
    public void should_return_2() {
        byte result = converter.convertToDatabaseColumn(FEMALE);
        assertThat(result).isEqualTo((byte) 2);
    }


    @Test
    public void should_return_NOT_DEFINED() {
        Gender result = converter.convertToEntityAttribute((byte) 0);
        assertThat(result).isEqualTo(NOT_DEFINED);
    }


    @Test
    public void should_return_MALE() {
        Gender result = converter.convertToEntityAttribute((byte) 1);
        assertThat(result).isEqualTo(MALE);
    }


    @Test
    public void should_return_FEMALE() {
        Gender result = converter.convertToEntityAttribute((byte) 2);
        assertThat(result).isEqualTo(FEMALE);
    }


    @Test
    public void should_thrown_IllegalArgumentException() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute((byte) 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid Gender value: '3', need: 0 = NOT_DEFINED, 1 = MALE, 2 = FEMALE");
    }

}
