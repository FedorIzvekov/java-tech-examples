package com.fedorizvekov.query.jpa.repository;

import static com.fedorizvekov.query.jpa.model.enums.ContactStatus.NOT_CONFIRMED;
import static com.fedorizvekov.query.jpa.model.enums.ContactType.EMAIL;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fedorizvekov.query.jpa.model.entity.Contact;
import com.fedorizvekov.query.jpa.model.entity.User;
import com.fedorizvekov.query.jpa.model.enums.Gender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    private LocalDateTime testDateTime;
    private User user;
    private Contact contact;

    @Before
    public void setUp() {
        testDateTime = LocalDateTime.now();

        user = User.builder()
                .firstName("test_name")
                .lastName("test_last_name")
                .middleName("test_middle_name")
                .birthdate(LocalDate.parse("1990-12-31"))
                .gender(Gender.NOT_DEFINED)
                .build();

        contact = Contact.builder()
                .type(EMAIL)
                .value("test_email@email.com")
                .confirmationCode("111")
                .status(NOT_CONFIRMED)
                .build();
    }


    @Test
    public void should_save_user_with_minimum_params() {
        user = User.builder()
                .firstName("test_name")
                .gender(Gender.FEMALE)
                .build();

        User result = userRepository.saveAndFlush(user);

        assertThat(result.getUserId()).isGreaterThan(0);
        assertThat(result.getFirstName()).isEqualTo("test_name");
        assertThat(result.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(result.getTimestamps().getCreated()).isAfterOrEqualTo(testDateTime);
        assertThat(result.getTimestamps().getUpdated()).isAfterOrEqualTo(testDateTime);
        assertThat(result.getContacts()).isEmpty();
    }


    @Test
    public void should_save_user() {
        User result = userRepository.save(user);

        assertThat(result.getUserId()).isGreaterThan(0);
        assertThat(result.getFirstName()).isEqualTo("test_name");
        assertThat(result.getLastName()).isEqualTo("test_last_name");
        assertThat(result.getMiddleName()).isEqualTo("test_middle_name");
        assertThat(result.getGender()).isEqualTo(Gender.NOT_DEFINED);
        assertThat(result.getBirthdate()).isEqualTo("1990-12-31");
        assertThat(result.getTimestamps().getCreated()).isAfterOrEqualTo(testDateTime);
        assertThat(result.getTimestamps().getUpdated()).isAfterOrEqualTo(testDateTime);
        assertThat(result.getContacts()).isEmpty();
    }


//    @Test
//    public void should_save_contact() {
////        User savedUser = userRepository.saveAndFlush(user);
//        contact.setUser(user);
//
//        User user = userRepository.save(user);
//
//        assertThat(result.getContacts().get(0).getContactId()).isGreaterThan(0);
//        assertThat(result.getValue()).isEqualTo("test_email@email.com");
//        assertThat(result.getType()).isEqualTo(EMAIL);
//        assertThat(result.getStatus()).isEqualTo(NOT_CONFIRMED);
//        assertThat(result.getConfirmationCode()).isEqualTo("111");
//        assertThat(result.getTimestamps().getCreated()).isAfterOrEqualTo(testDateTime);
//        assertThat(result.getTimestamps().getUpdated()).isAfterOrEqualTo(testDateTime);
//        assertThat(result.getUser().getUserId()).isGreaterThan(0);
//        assertThat(result.getUser().getFirstName()).isEqualTo("test_name");
//    }

}
