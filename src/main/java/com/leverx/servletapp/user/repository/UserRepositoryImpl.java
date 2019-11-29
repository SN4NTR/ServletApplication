package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.user.entity.User;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.InternalServerErrorException;
import java.util.Collection;

import static com.leverx.servletapp.db.HibernateConfig.getEntityManager;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public void save(User user) {
        log.info("Saving user with name '{}'.", user.getFirstName());

        var entityManager = getEntityManager();
        var transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(user);
            transaction.commit();

            log.info("User was saved");
        } catch (Exception ex) {
            transaction.rollback();
            log.error("User can't be saved");
            throw new InternalServerErrorException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public User findById(int id) {
        log.info("Getting user by id = {}", id);

        var entityManager = getEntityManager();
        var transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            var user = entityManager.find(User.class, id);
            transaction.commit();

            log.info("User with id = {} was found", id);
            return user;
        } catch (Exception ex) {
            transaction.rollback();
            log.error("User can't be found");
            throw new InternalServerErrorException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> findByName(String name) {
        log.info("Getting user by firstName = {}", name);

        var entityManager = getEntityManager();
        var transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            var query = entityManager.createQuery("from User where firstName=:firstName");
            query.setParameter("firstName", name);
            var users = query.getResultList();
            transaction.commit();

            log.info("Users were found");
            return users;
        } catch (Exception ex) {
            transaction.rollback();
            log.error("User can't be found");
            throw new InternalServerErrorException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> findAll() {
        log.info("Getting all users");

        var entityManager = getEntityManager();
        var transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            var query = entityManager.createQuery("from User");
            var users = query.getResultList();
            transaction.commit();

            log.info("Users were found");
            return users;
        } catch (Exception ex) {
            transaction.rollback();
            log.error("Users cant' be found");
            throw new InternalServerErrorException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(int id) {
        log.info("Deleting user with id = {}", id);

        var entityManager = getEntityManager();
        var transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            var user = entityManager.find(User.class, id);
            entityManager.remove(user);
            transaction.commit();

            log.info("User with id = {} was deleted", id);
        } catch (Exception ex) {
            transaction.rollback();
            log.error("User can't be deleted");
            throw new InternalServerErrorException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(User user) {
        var id = user.getId();
        log.info("Updating user with id = {}", id);

        var entityManager = getEntityManager();
        var transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(user);
            transaction.commit();

            log.info("User is updated");
        } catch (Exception ex) {
            transaction.rollback();
            log.error("User can't be updated");
            throw new InternalServerErrorException(ex);
        } finally {
            entityManager.close();
        }
    }
}
