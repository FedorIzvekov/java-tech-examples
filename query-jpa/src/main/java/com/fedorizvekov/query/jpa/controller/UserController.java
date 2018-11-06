package com.fedorizvekov.query.jpa.controller;

import java.util.List;
import com.fedorizvekov.query.jpa.model.entity.User;
import com.fedorizvekov.query.jpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userService.create(user);
    }


    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.readAll();
    }


    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable Long id) {
        return userService.read(id);
    }


    @PutMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }


    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

}
