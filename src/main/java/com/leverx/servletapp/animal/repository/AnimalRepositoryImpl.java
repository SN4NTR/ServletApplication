package com.leverx.servletapp.animal.repository;

import com.leverx.servletapp.animal.entity.Animal;
import com.leverx.servletapp.animal.entity.Animal_;
import com.leverx.servletapp.user.entity.User_;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.Collection;

import static com.leverx.servletapp.db.EntityManagerConfig.getEntityManager;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.emptyCollection;

@Slf4j
public class AnimalRepositoryImpl implements AnimalRepository {

    @Override
    public Collection<Animal> findByOwnerId(int ownerId) {
        log.info("Getting animals by owner id");

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            var animals = getCatsByOwnerId(entityManager, ownerId);

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
    public Collection<Animal> findAll() {
        log.info("Getting all animals");

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            var animals = getAnimals(entityManager);

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

    private Collection<Animal> getAnimals(EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Animal.class);
        var root = criteriaQuery.from(Animal.class);

        criteriaQuery.select(root);

        var query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    private Collection<Animal> getCatsByOwnerId(EntityManager entityManager, int ownerId) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Animal.class);
        var root = criteriaQuery.from(Animal.class);
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
