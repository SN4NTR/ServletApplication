package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.User_;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.Optional;

import static com.leverx.servletapp.db.EntityManagerConfig.getEntityManager;
import static java.util.Objects.nonNull;
import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;
import static org.apache.commons.collections4.CollectionUtils.emptyCollection;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public void save(User user) {
        log.info("Saving user with name '{}'.", user.getFirstName());

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(user);

            transaction.commit();
            log.info("User was saved");
        } catch (EntityExistsException ex) {
            rollbackTransaction(transaction);
            log.error("User can't be saved");
            throw new IllegalArgumentException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(int id) {
        log.info("Deleting user with id = {}", id);

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            var user = entityManager.find(User.class, id);
            entityManager.remove(user);

            transaction.commit();
            log.info("User with id = {} was deleted", id);
        } catch (IllegalArgumentException ex) {
            rollbackTransaction(transaction);
            log.error("User can't be deleted");
            throw new IllegalArgumentException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(User user) {
        var id = user.getId();
        log.info("Updating user with id = {}", id);

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.merge(user);

            transaction.commit();
            log.info("User is updated");
        } catch (IllegalArgumentException ex) {
            rollbackTransaction(transaction);
            log.error("User can't be updated");
            throw new IllegalArgumentException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<User> findById(int id) {
        log.info("Getting user by id = {}", id);

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            var criteriaQuery = getUserByCriteria(entityManager, User_.id, id);
            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery(criteriaQuery).setLockMode(PESSIMISTIC_WRITE);
            var user = query.getSingleResult();

            transaction.commit();
            log.info("User with id = {} was found", id);

            return Optional.of(user);
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "User can't be found";
            log.error(message);
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<User> findByName(String name) {
        log.info("Getting user by firstName = {}", name);

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            var criteriaQuery = getUserByCriteria(entityManager, User_.firstName, name);
            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery(criteriaQuery);
            var users = query.getResultList();

            transaction.commit();
            log.info("Users were found");

            return users;
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Users can't be found";
            log.error(message);
            return emptyCollection();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<User> findAll() {
        log.info("Getting all users");

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            var users = getUsers(entityManager);

            transaction.commit();
            log.info("Users were found");

            return users;
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Users cant' be found";
            log.error(message);
            return emptyCollection();
        } finally {
            entityManager.close();
        }
    }

    private Collection<User> getUsers(EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(User.class);
        var root = criteriaQuery.from(User.class);

        criteriaQuery.select(root);

        var query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    private CriteriaQuery<User> getUserByCriteria(EntityManager entityManager, SingularAttribute<User, ?> attribute, Object compareWith) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(User.class);
        var root = criteriaQuery.from(User.class);
        var fieldName = root.get(attribute);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(fieldName, compareWith));

        return criteriaQuery;
    }

    private void rollbackTransaction(EntityTransaction transaction) {
        if (nonNull(transaction) && transaction.isActive()) {
            transaction.rollback();
        }
    }
}
