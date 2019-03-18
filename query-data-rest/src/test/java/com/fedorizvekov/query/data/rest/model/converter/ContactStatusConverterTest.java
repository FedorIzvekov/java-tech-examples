package com.fedorizvekov.query.data.rest.model.converter;

import static com.fedorizvekov.query.data.rest.model.enums.ContactStatus.CONFIRMED;
import static com.fedorizvekov.query.data.rest.model.enums.ContactStatus.NOT_CONFIRMED;
import static com.fedorizvekov.query.data.rest.model.enums.ContactStatus.REPLACED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import com.fedorizvekov.query.data.rest.model.enums.ContactStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContactStatusConverterTest {

    @InjectMocks
    private ContactStatusConverter converter;


    private static Stream<Arguments> provideConvert() {
        return Stream.of(
                Arguments.of((short) 0, CONFIRMED),
                Arguments.of((short) 1, NOT_CONFIRMED),
                Arguments.of((short) 2, REPLACED)
        );
    }


    @DisplayName("Should convert ContactStatus to number")
    @MethodSource("provideConvert")
    @ParameterizedTest
    void shouldConvert_contactStatus_toNumber(short number, ContactStatus contactStatus) {
        var result = converter.convertToDatabaseColumn(contactStatus);
        assertThat(result).isEqualTo(number);
    }


    @DisplayName("Should convert number to ContactStatus")
    @MethodSource("provideConvert")
    @ParameterizedTest
    void shouldConvert_number_toContactStatus(short number, ContactStatus contactStatus) {
        var result = converter.convertToEntityAttribute(number);
        assertThat(result).isEqualTo(contactStatus);
    }


    @DisplayName("Should thrown IllegalArgumentException")
    @Test
    void should_thrown_IllegalArgumentException() {
        assertThatThrownBy(() -> converter.convertToEntityAttribute((short) 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid CredentialStatus value: '3', need: 0 = CONFIRMED, 1 = NOT_CONFIRMED, 2 = REPLACED");
    }

}
