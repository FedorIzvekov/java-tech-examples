package com.fedorizvekov.query.data.rest.model.entity;

import static com.fedorizvekov.query.data.rest.model.enums.ContactStatus.NOT_CONFIRMED;
import static java.util.Objects.hash;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fedorizvekov.query.data.rest.model.enums.ContactStatus;
import com.fedorizvekov.query.data.rest.model.enums.ContactType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "contact")
@Builder
@Getter
@Setter
@ToString(exclude = "user")
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id", nullable = false)
    private Long contactId;

    @Column(name = "type", columnDefinition = "TINYINT", nullable = false)
    private ContactType type;

    @Column(name = "value", nullable = false, length = 254)
    private String value;

    @Column(name = "confirmation_code", nullable = false, length = 10)
    private String confirmationCode;

    @Builder.Default
    @Column(name = "status", columnDefinition = "TINYINT", nullable = false)
    private ContactStatus status = NOT_CONFIRMED;

    @Builder.Default
    @Embedded
    private Timestamps timestamps = new Timestamps();

    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;


    @Override
    public boolean equals(Object object) {

        if (this == object) return true;
        if (isNull(object) || this.getClass() != object.getClass()) return false;

        var contact = (Contact) object;

        if (nonNull(this.contactId) && nonNull(contact.contactId)) {
            return Objects.equals(this.contactId, contact.contactId);
        }

        return Objects.equals(this.value, contact.value);
    }


    @Override
    public int hashCode() {
        if (nonNull(this.contactId)) {
            return hash(this.contactId);
        }

        return hash(this.value);
    }

}
