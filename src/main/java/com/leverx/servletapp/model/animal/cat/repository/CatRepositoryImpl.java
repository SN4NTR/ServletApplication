package com.leverx.servletapp.model.animal.cat.repository;

import com.leverx.servletapp.annotation.Repository;
import com.leverx.servletapp.model.animal.cat.entity.Cat;
import com.leverx.servletapp.model.animal.cat.entity.Cat_;
import com.leverx.servletapp.model.user.entity.User_;
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
@Repository
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
            throw new IllegalArgumentException(ex);
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
            transaction = entityManager.getTransaction();
            transaction.begin();

            var cat = getCatById(entityManager, id);

            transaction.commit();
            log.info("Cat with id = {} was found", id);

            return Optional.of(cat);
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Cat can't be found";
            log.error(message);
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<Cat> findByOwnerId(int ownerId) {
        log.info("Getting cats by owner id");

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            var cats = getCatsByOwnerId(entityManager, ownerId);

            transaction.commit();
            log.info("Cats were found");

            return cats;
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Cat can't be found";
            log.error(message);
            return emptyCollection();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<Cat> findAll() {
        log.info("Getting all cats");

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            var cats = getCats(entityManager);

            transaction.commit();
            log.info("Cats were found");

            return cats;
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Cat can't be found";
            log.error(message);
            return emptyCollection();
        } finally {
            entityManager.close();
        }
    }

    private Collection<Cat> getCatsByOwnerId(EntityManager entityManager, int ownerId) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Cat.class);
        var root = criteriaQuery.from(Cat.class);
        var users = root.join(Cat_.owners);
        var condition = criteriaBuilder.equal(users.get(User_.id), ownerId);

        criteriaQuery.select(root).where(condition);

        var query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    private Collection<Cat> getCats(EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Cat.class);
        var root = criteriaQuery.from(Cat.class);

        criteriaQuery.select(root);

        var query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    private Cat getCatById(EntityManager entityManager, int id) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Cat.class);
        var root = criteriaQuery.from(Cat.class);
        var fieldName = root.get(Cat_.id);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(fieldName, id));

        var query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    private void rollbackTransaction(EntityTransaction transaction) {
        if (nonNull(transaction) && transaction.isActive()) {
            transaction.rollback();
        }
    }
}
