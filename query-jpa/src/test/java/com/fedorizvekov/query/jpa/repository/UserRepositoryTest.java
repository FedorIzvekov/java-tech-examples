package com.fedorizvekov.query.jpa.repository;

import static com.fedorizvekov.query.jpa.model.enums.ContactType.EMAIL;
import static com.fedorizvekov.query.jpa.model.enums.ContactType.PHONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fedorizvekov.query.jpa.model.entity.Contact;
import com.fedorizvekov.query.jpa.model.entity.User;
import com.fedorizvekov.query.jpa.model.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    private final Long id = 1L;
    private final String firstName = "test_name";
    private final String lastName = "test_last_name";
    private final String middleName = "test_middle_name";
    private final LocalDate birthdate = LocalDate.parse("1990-12-31");
    private final String firstEmail = "firstEmail@email.com";
    private final String secondEmail = "secondEmail@email.com";
    private final String phone = "1112223344";
    private final LocalDateTime testDateTime = LocalDateTime.now();

    private final User user = User.builder().firstName(firstName).build();
    private final Contact firstContact = Contact.builder().type(EMAIL).value(firstEmail).confirmationCode("111").build();
    private final Contact secondContact = Contact.builder().type(EMAIL).value(secondEmail).confirmationCode("111").build();
    private final Contact thirdContact = Contact.builder().type(PHONE).value(phone).confirmationCode("111").build();

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;


    @BeforeEach
    void setUp() {
        entityManager.getEntityManager().createNativeQuery("ALTER TABLE user AUTO_INCREMENT = 1").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("ALTER TABLE contact AUTO_INCREMENT = 1").executeUpdate();
    }


    @DisplayName("Should save user with minimum params")
    @Test
    void shouldSave_userWithMinimumParams() {

        var result = userRepository.saveAndFlush(user);

        assertAll(
                () -> assertThat(result.getUserId()).isEqualTo(id),
                () -> assertThat(result.getFirstName()).isEqualTo(firstName),
                () -> assertThat(result.getGender()).isEqualTo(Gender.NOT_DEFINED),
                () -> assertThat(result.getTimestamps().getCreated()).isAfterOrEqualTo(testDateTime),
                () -> assertThat(result.getTimestamps().getUpdated()).isAfterOrEqualTo(testDateTime),
                () -> assertThat(result.getContacts()).isEmpty()
        );
    }


    @DisplayName("Should save user")
    @Test
    void shouldSave_user() {
        var user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .birthdate(birthdate)
                .gender(Gender.NOT_DEFINED)
                .build();

        var result = userRepository.saveAndFlush(user);

        assertAll(
                () -> assertThat(result.getUserId()).isEqualTo(id),
                () -> assertThat(result.getFirstName()).isEqualTo(firstName),
                () -> assertThat(result.getLastName()).isEqualTo(lastName),
                () -> assertThat(result.getMiddleName()).isEqualTo(middleName),
                () -> assertThat(result.getGender()).isEqualTo(Gender.NOT_DEFINED),
                () -> assertThat(result.getBirthdate()).isEqualTo(birthdate),
                () -> assertThat(result.getTimestamps().getCreated()).isAfterOrEqualTo(testDateTime),
                () -> assertThat(result.getTimestamps().getUpdated()).isAfterOrEqualTo(testDateTime),
                () -> assertThat(result.getContacts()).isEmpty()
        );
    }


    @DisplayName("Should save user with contact")
    @Test
    void shouldSave_userWithContact() {

        user.addContact(firstContact);

        var resultUser = userRepository.saveAndFlush(user);
        var resultContacts = resultUser.getContacts();

        assertAll(
                () -> assertThat(resultContacts).hasSize(1),
                () -> assertThat(resultContacts.get(0).getContactId()).isEqualTo(id),
                () -> assertThat(resultContacts.get(0).getType()).isEqualTo(EMAIL),
                () -> assertThat(resultContacts.get(0).getValue()).isEqualTo(firstEmail),
                () -> assertThat(resultContacts.get(0).getTimestamps().getCreated()).isAfterOrEqualTo(testDateTime),
                () -> assertThat(resultContacts.get(0).getTimestamps().getCreated()).isAfterOrEqualTo(testDateTime)
        );
    }


    @DisplayName("Should update contact")
    @Test
    void shouldUpdate_contact() {

        user.addContact(firstContact);

        var savedUser = entityManager.persist(user);
        var savedContact = savedUser.getContacts().get(0);

        savedContact.setType(PHONE);
        savedContact.setValue(phone);

        var resultUser = userRepository.saveAndFlush(user);
        var resultContacts = savedUser.getContacts().get(0);

        assertAll(
                () -> assertThat(resultUser.getContacts()).hasSize(1),
                () -> assertThat(resultContacts.getContactId()).isEqualTo(id),
                () -> assertThat(resultContacts.getType()).isEqualTo(PHONE),
                () -> assertThat(resultContacts.getValue()).isEqualTo(phone),
                () -> assertThat(resultContacts.getTimestamps().getCreated()).isEqualTo(savedContact.getTimestamps().getCreated()),
                () -> assertThat(resultContacts.getTimestamps().getUpdated()).isAfter(savedContact.getTimestamps().getCreated())
        );
    }


    @DisplayName("Should update contacts")
    @Test
    void shouldUpdate_contacts() {
        user.addContact(firstContact);
        user.addContact(secondContact);

        var savedUser = userRepository.saveAndFlush(user);
        var savedSecondContact = savedUser.getContacts().get(1);

        savedUser.getContacts().clear();

        user.addContact(savedSecondContact);
        user.addContact(thirdContact);

        var resultUser = userRepository.saveAndFlush(user);
        var resultSecondContact = resultUser.getContacts().get(0);
        var resultThirdContact = resultUser.getContacts().get(1);

        assertAll(
                () -> assertThat(resultUser.getContacts()).hasSize(2),
                () -> assertThat(resultSecondContact.getContactId()).isEqualTo(2L),
                () -> assertThat(resultSecondContact.getValue()).isEqualTo(secondEmail),
                () -> assertThat(resultThirdContact.getContactId()).isEqualTo(3L),
                () -> assertThat(resultThirdContact.getValue()).isEqualTo(phone)
        );
    }


    @DisplayName("Should delete contact")
    @Test
    void shouldDelete_contact() {

        user.addContact(firstContact);

        var savedUser = entityManager.persist(user);
        var savedContact = savedUser.getContacts().get(0);

        savedUser.getContacts().clear();

        var resultUser = userRepository.saveAndFlush(savedUser);
        var resultContacts = entityManager.find(Contact.class, savedContact.getContactId());

        assertAll(
                () -> assertThat(resultUser.getContacts()).hasSize(0),
                () -> assertThat(resultContacts).isNull()
        );
    }


    @DisplayName("Should select user by id")
    @Test
    void shouldSelect_userById() {
        user.addContact(firstContact);

        userRepository.saveAndFlush(user);

        var resultUser = userRepository.findById(id).get();
        var resultContact = resultUser.getContacts().get(0);

        assertAll(
                () -> assertThat(resultUser.getUserId()).isEqualTo(id),
                () -> assertThat(resultUser.getFirstName()).isEqualTo(firstName),
                () -> assertThat(resultContact.getContactId()).isEqualTo(id),
                () -> assertThat(resultContact.getValue()).isEqualTo(firstEmail)
        );

    }


    @DisplayName("Should select all users")
    @Test
    void shouldSelect_allUsers() {
        user.addContact(firstContact);
        userRepository.saveAndFlush(user);

        var secondUser = User.builder().firstName("test").build();
        secondUser.addContact(secondContact);
        userRepository.saveAndFlush(secondUser);

        var resultList = userRepository.findAll();
        var resultFirstUser = resultList.get(0);
        var resultFirstContact = resultFirstUser.getContacts().get(0);
        var resultSecondUser = resultList.get(1);
        var resultSecondContact = resultSecondUser.getContacts().get(0);

        assertAll(
                () -> assertThat(resultList).hasSize(2),
                () -> assertThat(resultFirstUser.getUserId()).isEqualTo(id),
                () -> assertThat(resultFirstUser.getFirstName()).isEqualTo(firstName),
                () -> assertThat(resultFirstContact.getContactId()).isEqualTo(id),
                () -> assertThat(resultFirstContact.getValue()).isEqualTo(firstEmail),
                () -> assertThat(resultSecondUser.getUserId()).isEqualTo(2L),
                () -> assertThat(resultSecondUser.getFirstName()).isEqualTo("test"),
                () -> assertThat(resultSecondContact.getContactId()).isEqualTo(2L),
                () -> assertThat(resultSecondContact.getValue()).isEqualTo(secondEmail)
        );
    }

}
