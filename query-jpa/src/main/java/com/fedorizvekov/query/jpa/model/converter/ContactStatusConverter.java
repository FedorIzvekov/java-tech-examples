package com.fedorizvekov.query.jpa.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fedorizvekov.query.jpa.model.enums.ContactStatus;

@Converter(autoApply = true)
public class ContactStatusConverter implements AttributeConverter<ContactStatus, Byte> {

    @Override
    public Byte convertToDatabaseColumn(ContactStatus contactStatus) {
        return contactStatus.getValue();
    }


    @Override
    public ContactStatus convertToEntityAttribute(Byte statusValue) {
        return ContactStatus.fromValue(statusValue);
    }

}
