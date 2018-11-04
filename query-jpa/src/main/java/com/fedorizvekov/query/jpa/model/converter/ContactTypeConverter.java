package com.fedorizvekov.query.jpa.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fedorizvekov.query.jpa.model.enums.ContactType;

@Converter(autoApply = true)
public class ContactTypeConverter implements AttributeConverter<ContactType, Short> {

    @Override
    public Short convertToDatabaseColumn(ContactType contactType) {
        return contactType.getValue();
    }


    @Override
    public ContactType convertToEntityAttribute(Short typeValue) {
        return ContactType.fromValue(typeValue);
    }

}
