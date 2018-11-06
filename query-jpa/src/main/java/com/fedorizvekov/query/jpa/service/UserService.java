package com.fedorizvekov.query.jpa.service;

import java.util.List;
import com.fedorizvekov.query.jpa.model.entity.User;

public interface UserService {

    User create(User user);

    User read(long id);

    List<User> readAll();

    User update(long id, User user);

    void delete(long id);

}
