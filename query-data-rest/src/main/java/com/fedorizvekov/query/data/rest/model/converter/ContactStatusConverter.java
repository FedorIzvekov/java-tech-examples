package com.fedorizvekov.query.data.rest.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fedorizvekov.query.data.rest.model.enums.ContactStatus;

@Converter(autoApply = true)
public class ContactStatusConverter implements AttributeConverter<ContactStatus, Short> {

    @Override
    public Short convertToDatabaseColumn(ContactStatus contactStatus) {
        return contactStatus.getValue();
    }


    @Override
    public ContactStatus convertToEntityAttribute(Short statusValue) {
        return ContactStatus.fromValue(statusValue);
    }

}
