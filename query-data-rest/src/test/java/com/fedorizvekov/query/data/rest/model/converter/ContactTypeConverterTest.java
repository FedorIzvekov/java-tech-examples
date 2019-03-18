package com.fedorizvekov.query.data.rest.model.converter;

import static com.fedorizvekov.query.data.rest.model.enums.ContactType.EMAIL;
import static com.fedorizvekov.query.data.rest.model.enums.ContactType.PHONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import com.fedorizvekov.query.data.rest.model.enums.ContactType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContactTypeConverterTest {

    @InjectMocks
    private ContactTypeConverter converter;


    private static Stream<Arguments> provideConvert() {
        return Stream.of(
                Arguments.of((short) 0, EMAIL),
                Arguments.of((short) 1, PHONE)
        );
    }


    @DisplayName("Should convert ContactType to number")
    @MethodSource("provideConvert")
    @ParameterizedTest
    void shouldConvert_contactType_toNumber(short number, ContactType contactType) {
        var result = converter.convertToDatabaseColumn(contactType);
        assertThat(result).isEqualTo(number);
    }


    @DisplayName("Should convert number to ContactType")
    @MethodSource("provideConvert")
    @ParameterizedTest
    void shouldConvert_number_toContactType(short number, ContactType contactType) {
        var result = converter.convertToEntityAttribute(number);
        assertThat(result).isEqualTo(contactType);
    }


    @DisplayName("Should thrown IllegalArgumentException")
    @Test
    void should_thrown_IllegalArgumentException() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute((short) 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid ContactType value: '2', need: 0 = EMAIL, 1 = PHONE");
    }

}
