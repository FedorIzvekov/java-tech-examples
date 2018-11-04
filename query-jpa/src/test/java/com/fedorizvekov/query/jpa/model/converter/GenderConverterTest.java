package com.fedorizvekov.query.jpa.model.converter;

import static com.fedorizvekov.query.jpa.model.enums.Gender.FEMALE;
import static com.fedorizvekov.query.jpa.model.enums.Gender.MALE;
import static com.fedorizvekov.query.jpa.model.enums.Gender.NOT_DEFINED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import com.fedorizvekov.query.jpa.model.enums.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GenderConverterTest {

    @InjectMocks
    private GenderConverter converter;


    private static Stream<Arguments> provideConvert() {
        return Stream.of(
                Arguments.of((short) 0, NOT_DEFINED),
                Arguments.of((short) 1, MALE),
                Arguments.of((short) 2, FEMALE)
        );
    }


    @DisplayName("Should convert Gender to number")
    @MethodSource("provideConvert")
    @ParameterizedTest
    void shouldConvert_gender_toNumber(short number, Gender gender) {
        var result = converter.convertToDatabaseColumn(gender);
        assertThat(result).isEqualTo(number);
    }


    @DisplayName("Should convert number to Gender")
    @MethodSource("provideConvert")
    @ParameterizedTest
    void shouldConvert_number_to_gender(short number, Gender gender) {
        var result = converter.convertToEntityAttribute(number);
        assertThat(result).isEqualTo(gender);
    }


    @DisplayName("Should thrown IllegalArgumentException")
    @Test
    void should_thrown_IllegalArgumentException() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute((short) 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid Gender value: '3', need: 0 = NOT_DEFINED, 1 = MALE, 2 = FEMALE");
    }

}
