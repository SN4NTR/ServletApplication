package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.user.entity.User;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.InternalServerErrorException;
import java.util.Collection;

import static com.leverx.servletapp.db.HibernateConfig.getEntityManagerFactory;
import static java.util.Objects.nonNull;
import static org.slf4j.LoggerFactory.getLogger;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = getLogger(UserRepositoryImpl.class.getSimpleName());

    @Override
    public void save(User user) {
        LOGGER.info("Saving user with name '{}'.", user.getFirstName());

        var entityManagerFactory = getEntityManagerFactory();
        EntityTransaction transaction = null;
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(user);
            transaction.commit();

            LOGGER.info("User was saved");
        } catch (Exception ex) {
            var message = "User can't be saved";
            rollback(transaction, message);
            throw new InternalServerErrorException(ex);
        } finally {
            closeEntityManager(entityManager);
        }
    }

    @Override
    public User findById(int id) {
        LOGGER.info("Getting user by id = {}", id);

        var entityManagerFactory = getEntityManagerFactory();
        EntityTransaction transaction = null;
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            var user = entityManager.find(User.class, id);
            transaction.commit();
            LOGGER.info("User with id = {} was found", id);

            return user;
        } catch (Exception ex) {
            var message = "User can't be found";
            rollback(transaction, message);
            throw new InternalServerErrorException(ex);
        } finally {
            closeEntityManager(entityManager);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> findByName(String name) {
        LOGGER.info("Getting user by firstName = {}", name);

        var entityManagerFactory = getEntityManagerFactory();
        EntityTransaction transaction = null;
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery("from User where firstName=:firstName");
            query.setParameter("firstName", name);
            var users = query.getResultList();
            transaction.commit();
            LOGGER.info("Users were found");

            return users;
        } catch (Exception ex) {
            var message = "User can't be found";
            rollback(transaction, message);
            throw new InternalServerErrorException(ex);
        } finally {
            closeEntityManager(entityManager);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> findAll() {
        LOGGER.info("Getting all users");

        var entityManagerFactory = getEntityManagerFactory();
        EntityTransaction transaction = null;
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery("from User");
            var users = query.getResultList();
            transaction.commit();
            LOGGER.info("Users were found");

            return users;
        } catch (Exception ex) {
            var message = "Users cant' be found";
            rollback(transaction, message);
            throw new InternalServerErrorException(ex);
        } finally {
            closeEntityManager(entityManager);
        }
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Deleting user with id = {}", id);

        var entityManagerFactory = getEntityManagerFactory();
        EntityTransaction transaction = null;
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            var user = entityManager.find(User.class, id);
            entityManager.remove(user);
            transaction.commit();

            LOGGER.info("User with id = {} was deleted", id);
        } catch (Exception ex) {
            var message = "User can't be deleted";
            rollback(transaction, message);
            throw  new InternalServerErrorException(ex);
        } finally {
            closeEntityManager(entityManager);
        }
    }

    @Override
    public void update(User user) {
        var id = user.getId();
        LOGGER.info("Updating user with id = {}", id);

        var entityManagerFactory = getEntityManagerFactory();
        EntityTransaction transaction = null;
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(user);
            transaction.commit();

            LOGGER.info("User is updated");
        } catch (Exception ex) {
            var message = "User can't be updated";
            rollback(transaction, message);
            throw  new InternalServerErrorException(ex);
        } finally {
            closeEntityManager(entityManager);
        }
    }

    private void closeEntityManager(EntityManager entityManager) {
        if (nonNull(entityManager)) {
            entityManager.close();
        }
    }

    private void rollback(EntityTransaction transaction, String message) {
        if (nonNull(transaction)) {
            transaction.rollback();
        }
        LOGGER.error(message);
    }
}
