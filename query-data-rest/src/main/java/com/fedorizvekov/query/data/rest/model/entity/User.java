package com.fedorizvekov.query.data.rest.model.entity;

import static com.fedorizvekov.query.data.rest.model.enums.Gender.NOT_DEFINED;
import static java.util.Objects.hash;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fedorizvekov.query.data.rest.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "`user`")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Builder.Default
    @Column(name = "gender", nullable = false, columnDefinition = "TINYINT")
    private Gender gender = NOT_DEFINED;

    @Builder.Default
    @Embedded
    private Timestamps timestamps = new Timestamps();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contact> contacts = new HashSet<>();


    public void setContacts(List<Contact> contactsList){

        contacts.clear();

        for(var contact : contactsList) {
            this.addContact(contact);
        }
    }


    public void addContact(Contact contact) {
        contact.setUser(this);
        contacts.add(contact);
    }


    public void removeContact(Contact contact) {
        contact.setUser(null);
        contacts.remove(contact);
    }


    @Override
    public boolean equals(Object object) {

        if (this == object) return true;
        if (isNull(object) || this.getClass() != object.getClass()) return false;

        var user = (User) object;

        if (nonNull(this.userId) && nonNull(user.userId)) {
            return Objects.equals(this.userId, user.userId);
        }

        return Objects.equals(this.firstName, user.firstName)
                && Objects.equals(this.lastName, user.lastName)
                && Objects.equals(this.middleName, user.middleName)
                && Objects.equals(this.birthdate, user.birthdate)
                && this.gender == user.gender;
    }


    @Override
    public int hashCode() {
        if (nonNull(this.userId)) {
            return hash(this.userId);
        }

        return hash(firstName, lastName, middleName, birthdate, gender);
    }

}
