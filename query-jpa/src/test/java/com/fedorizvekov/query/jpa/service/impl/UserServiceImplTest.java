package com.fedorizvekov.query.jpa.service.impl;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;
import com.fedorizvekov.query.jpa.exception.NotFoundException;
import com.fedorizvekov.query.jpa.model.entity.User;
import com.fedorizvekov.query.jpa.repository.UserRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final Long id = 1L;
    private static final String firstName = "test_name";
    private static final User user = User.builder().firstName(firstName).build();

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;


    private static Stream<ThrowableAssert.ThrowingCallable> provideThrowingCallable() {
        var userRepository = mock(UserRepository.class);
        var staticUserService = new UserServiceImpl(userRepository);

        return Stream.of(
                () -> staticUserService.read(id),
                () -> staticUserService.update(id, user)
        );
    }


    @DisplayName("Should invoke save and flush")
    @Test
    void shouldInvoke_saveAndFlush() {
        userService.create(user);
        verify(userRepository).saveAndFlush(eq(user));
    }


    @DisplayName("Should invoke find by id")
    @Test
    void shouldInvoke_findById() {
        when(userRepository.findById(anyLong())).thenReturn(of(user));

        userService.read(id);
        verify(userRepository).findById(eq(id));
    }


    @DisplayName("Should invoke find all")
    @Test
    void shouldInvoke_findAll() {
        userService.readAll();
        verify(userRepository).findAll();
    }


    @DisplayName("Should invoke find by id and save and flush")
    @Test
    void shouldInvoke_findById_and_saveAndFlush() {
        when(userRepository.findById(anyLong())).thenReturn(of(user));

        userService.update(id, user);
        verify(userRepository).findById(id);
        verify(userRepository).saveAndFlush(user);
    }


    @DisplayName("Should invoke delete by id")
    @Test
    void shouldInvoke_deleteById() {
        userService.delete(id);
        verify(userRepository).deleteById(id);
    }


    @DisplayName("Should thrown NotFoundException")
    @MethodSource("provideThrowingCallable")
    @ParameterizedTest
    void shouldThrown_NotFoundException(ThrowableAssert.ThrowingCallable throwingCallable) {

        assertThatThrownBy(throwingCallable)
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Not found User with id '1'");
    }

}
