package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserImpl implements UserDao {

    private final EntityManagerFactory entityFactory;

    @Autowired
    public JpaUserImpl(EntityManagerFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    @Override
    public Optional<User> findById(long id) {
        EntityManager entityManager = entityFactory.createEntityManager();
        User user = entityManager.find(User.class, id);
        entityManager.close();
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll(int amount, int page) {
        EntityManager entityManager = entityFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);
        query.select(userRoot);
        List<User> resultList = entityManager.createQuery(query).setMaxResults(amount).setFirstResult(amount * page)
                .getResultList();
        entityManager.close();
        return resultList;
    }
}
