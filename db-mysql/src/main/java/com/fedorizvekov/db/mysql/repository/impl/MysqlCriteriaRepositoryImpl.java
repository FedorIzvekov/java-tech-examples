package com.fedorizvekov.db.mysql.repository.impl;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.fedorizvekov.db.mysql.model.entity.TypeValue;
import com.fedorizvekov.db.mysql.repository.MysqlCriteriaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MysqlCriteriaRepositoryImpl implements MysqlCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public long count() {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(Long.class);
        var root = query.from(TypeValue.class);
        var expression = builder.count(root);
        query.select(expression);

        return entityManager.createQuery(query).getSingleResult();
    }


    @Override
    public Optional<TypeValue> findById(Long id) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(TypeValue.class);
        var root = query.from(TypeValue.class);
        var predicate = builder.equal(root.get("longId"), id);
        query.select(root).where(predicate);

        var typeValues = entityManager.createQuery(query).getResultList();

        return typeValues.isEmpty() ? empty() : of(typeValues.get(0));
    }


    @Override
    public List<TypeValue> findAll() {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(TypeValue.class);
        var root = query.from(TypeValue.class);
        query.select(root);

        return entityManager.createQuery(query).getResultList();
    }

}
