package com.fedorizvekov.query.jpa.service.impl;

import java.util.List;
import com.fedorizvekov.query.jpa.exception.NotFoundException;
import com.fedorizvekov.query.jpa.model.entity.User;
import com.fedorizvekov.query.jpa.repository.UserRepository;
import com.fedorizvekov.query.jpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User create(User user) {
        return userRepository.saveAndFlush(user);
    }


    @Override
    public User read(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found User with id '" + id + "'"));
    }


    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }


    @Override
    public User update(long id, User user) {
        var existsUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found User with id '" + id + "'"));

        existsUser.setFirstName(user.getFirstName());
        existsUser.setLastName(user.getLastName());
        existsUser.setMiddleName(user.getMiddleName());
        existsUser.setBirthdate(user.getBirthdate());
        existsUser.setGender(user.getGender());

        existsUser.getContacts().clear();
        user.getContacts().forEach(existsUser::addContact);

        return userRepository.saveAndFlush(existsUser);
    }


    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

}
