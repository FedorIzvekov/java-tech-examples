package com.fedorizvekov.query.data.rest.repository;

import com.fedorizvekov.query.data.rest.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, Long> {

//    @Override
//    @RestResource(exported = false)
//    void delete(User user);

}
