package com.leverx.servletapp.animal.repository;

import com.leverx.servletapp.animal.entity.Animal;
import com.leverx.servletapp.animal.entity.Animal_;
import com.leverx.servletapp.user.entity.User_;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.Optional;

import static com.leverx.servletapp.db.EntityManagerConfig.getEntityManager;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.emptyCollection;

@Slf4j
public class AnimalRepositoryImpl implements AnimalRepository {

    @Override
    public <T extends Animal> void save(T t) {
        log.info("Saving animal with name '{}'.", t.getName());

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(t);

            transaction.commit();
            log.info("Animal was saved");
        } catch (EntityExistsException ex) {
            rollbackTransaction(transaction);
            log.error("Animal can't be saved");
            throw new IllegalArgumentException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public <T extends Animal> Optional<T> findById(int id, Class<T> tClass) {
        log.info("Getting animal with id = {}", id);

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            var animal = getAnimalById(id, entityManager, tClass);

            transaction.commit();
            log.info("Animal with id = {} was found", id);

            return Optional.of(animal);
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Animal can't be found";
            log.error(message);
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public <T extends Animal> Collection<T> findByOwnerId(int ownerId, Class<T> tClass) {
        log.info("Getting animals by owner id");

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            var animals = getAnimalsByOwnerId(ownerId, entityManager, tClass);

            transaction.commit();
            log.info("Animals were found");

            return animals;
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Animals can't be found";
            log.error(message);
            return emptyCollection();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public <T extends Animal> Collection<T> findAll(Class<T> tClass) {
        log.info("Getting all animals");

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            var animals = getAnimals(entityManager, tClass);

            transaction.commit();
            log.info("Animals were found");

            return animals;
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Animals can't be found";
            log.error(message);
            return emptyCollection();
        } finally {
            entityManager.close();
        }
    }

    private <T extends Animal> T getAnimalById(int id, EntityManager entityManager, Class<T> tClass) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(tClass);
        var root = criteriaQuery.from(tClass);
        var fieldName = root.get(Animal_.id);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(fieldName, id));

        var query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    private <T extends Animal> Collection<T> getAnimals(EntityManager entityManager, Class<T> tClass) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(tClass);
        var root = criteriaQuery.from(tClass);

        criteriaQuery.select(root);

        var query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    private static <T extends Animal> Collection<T> getAnimalsByOwnerId(int ownerId, EntityManager entityManager, Class<T> tClass) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(tClass);
        var root = criteriaQuery.from(tClass);
        var owners = root.join(Animal_.owners);
        var condition = criteriaBuilder.equal(owners.get(User_.id), ownerId);

        criteriaQuery.select(root).where(condition);

        var query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    private void rollbackTransaction(EntityTransaction transaction) {
        if (nonNull(transaction) && transaction.isActive()) {
            transaction.rollback();
        }
    }
}
