package com.fedorizvekov.query.jpa.model.converter;

import static com.fedorizvekov.query.jpa.model.enums.ContactType.EMAIL;
import static com.fedorizvekov.query.jpa.model.enums.ContactType.PHONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContactTypeConverterTest {

    @InjectMocks
    private ContactTypeConverter converter;


    @Test
    public void should_return_0() {
        var result = converter.convertToDatabaseColumn(EMAIL);
        assertThat(result).isEqualTo((byte) 0);
    }


    @Test
    public void should_return_1() {
        var result = converter.convertToDatabaseColumn(PHONE);
        assertThat(result).isEqualTo((byte) 1);
    }


    @Test
    public void should_return_EMAIL() {
        var result = converter.convertToEntityAttribute((byte) 0);
        assertThat(result).isEqualTo(EMAIL);
    }


    @Test
    public void should_return_PHONE() {
        var result = converter.convertToEntityAttribute((byte) 1);
        assertThat(result).isEqualTo(PHONE);
    }


    @Test
    public void should_thrown_IllegalArgumentException() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute((byte) 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid ContactType value: '2', need: 0 = EMAIL, 1 = PHONE");
    }

}
