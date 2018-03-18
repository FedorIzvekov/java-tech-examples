package com.fedorizvekov.query.jpa.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fedorizvekov.query.jpa.model.enums.ContactType;

@Converter(autoApply = true)
public class ContactTypeConverter implements AttributeConverter<ContactType, Byte> {

    @Override
    public Byte convertToDatabaseColumn(ContactType contactType) {
        return contactType.getValue();
    }


    @Override
    public ContactType convertToEntityAttribute(Byte typeValue) {
        return ContactType.fromValue(typeValue);
    }

}
