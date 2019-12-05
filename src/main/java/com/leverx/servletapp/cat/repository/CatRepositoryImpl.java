package com.leverx.servletapp.cat.repository;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.Cat_;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.ws.rs.InternalServerErrorException;
import java.util.Collection;
import java.util.Optional;

import static com.leverx.servletapp.db.EntityManagerConfig.getEntityManager;
import static java.util.Objects.nonNull;

@Slf4j
public class CatRepositoryImpl implements CatRepository {

    @Override
    public void save(Cat cat) {
        log.info("Saving cat with name '{}'.", cat.getName());

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(cat);

            transaction.commit();
            log.info("Cat was saved");
        } catch (EntityExistsException ex) {
            rollbackTransaction(transaction);
            log.error("Cat can't be saved");
            throw new InternalServerErrorException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Cat> findById(int id) {
        log.info("Getting cat with id = {}", id);

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            var criteriaBuilder = entityManager.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Cat.class);

            var root = criteriaQuery.from(Cat.class);
            var fieldName = root.get(Cat_.id);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(fieldName, id));

            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery(criteriaQuery);
            var cat = query.getSingleResult();

            transaction.commit();
            log.info("Cat with id = {} was found", id);
            return nonNull(cat) ? Optional.of(cat) : Optional.empty();
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Cat can't be found";
            log.error(message);
            throw new EntityNotFoundException(message);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<Cat> findByOwnerId(int id) {
        log.info("Getting cat by owner id = {}", id);

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            var criteriaBuilder = entityManager.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Cat.class);

            var root = criteriaQuery.from(Cat.class);
            var fieldName = root.get(Cat_.owner);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(fieldName, id));

            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery(criteriaQuery);
            var cats = query.getResultList();

            transaction.commit();
            log.info("Cats were found");
            return cats;
        } catch (IllegalStateException ex) {
            rollbackTransaction(transaction);
            var message = "Cat can't be found";
            log.error(message);
            throw new EntityNotFoundException(message);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<Cat> findAll() {
        log.info("Getting all users");

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            var criteriaBuilder = entityManager.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Cat.class);
            var root = criteriaQuery.from(Cat.class);

            criteriaQuery.select(root);

            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery(criteriaQuery);
            var cats = query.getResultList();

            transaction.commit();
            log.info("Cats were found");
            return cats;
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Cat can't be found";
            log.error(message);
            throw new EntityNotFoundException(message);
        } finally {
            entityManager.close();
        }
    }

    private void rollbackTransaction(EntityTransaction transaction) {
        if (nonNull(transaction) && transaction.isActive()) {
            transaction.rollback();
        }
    }
}
