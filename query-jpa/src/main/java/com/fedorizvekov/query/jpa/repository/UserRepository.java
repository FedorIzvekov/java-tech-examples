package com.fedorizvekov.query.jpa.repository;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.query.jpa.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @EntityGraph(attributePaths = {"contacts"})
    List<User> findAll();


    @Override
    @EntityGraph(attributePaths = {"contacts"})
    Optional<User> findById(Long id);

}
