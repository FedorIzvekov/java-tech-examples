package com.fedorizvekov.db.mysql.repository.impl;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.fedorizvekov.db.mysql.model.entity.TypeValue;
import com.fedorizvekov.db.mysql.repository.MysqlCriteriaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MysqlCriteriaRepositoryImpl implements MysqlCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public long count() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<TypeValue> root = query.from(TypeValue.class);
        Expression<Long> expression = builder.count(root);
        query.select(expression);

        return entityManager.createQuery(query).getSingleResult();
    }


    @Override
    public Optional<TypeValue> findById(Long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TypeValue> query = builder.createQuery(TypeValue.class);
        Root<TypeValue> root = query.from(TypeValue.class);
        Predicate predicate = builder.equal(root.get("longId"), id);
        query.select(root).where(predicate);

        List<TypeValue> typeValues = entityManager.createQuery(query).getResultList();

        if (typeValues.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(typeValues.get(0));
    }


    @Override
    public List<TypeValue> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TypeValue> query = builder.createQuery(TypeValue.class);
        Root<TypeValue> root = query.from(TypeValue.class);
        query.select(root);

        return entityManager.createQuery(query).getResultList();
    }

}
