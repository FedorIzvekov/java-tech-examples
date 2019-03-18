package com.fedorizvekov.query.data.rest.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fedorizvekov.query.data.rest.model.enums.Gender;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, Short> {

    @Override
    public Short convertToDatabaseColumn(Gender gender) {
        return gender.getValue();
    }


    @Override
    public Gender convertToEntityAttribute(Short genderValue) {
        return Gender.fromValue(genderValue);
    }

}