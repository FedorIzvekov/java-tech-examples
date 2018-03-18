package com.fedorizvekov.query.jpa.repository;

import com.fedorizvekov.query.jpa.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
