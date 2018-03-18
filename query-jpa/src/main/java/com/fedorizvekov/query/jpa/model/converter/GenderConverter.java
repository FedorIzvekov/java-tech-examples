package com.fedorizvekov.query.jpa.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fedorizvekov.query.jpa.model.enums.Gender;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, Byte> {

    @Override
    public Byte convertToDatabaseColumn(Gender gender) {
        return gender.getValue();
    }


    @Override
    public Gender convertToEntityAttribute(Byte genderValue) {
        return Gender.fromValue(genderValue);
    }

}