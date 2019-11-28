package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.user.entity.User;
import org.hibernate.Transaction;
import org.slf4j.Logger;

import javax.ws.rs.InternalServerErrorException;
import java.util.Collection;

import static com.leverx.servletapp.db.HibernateConfig.getSessionFactory;
import static java.util.Objects.nonNull;
import static org.slf4j.LoggerFactory.getLogger;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = getLogger(UserRepositoryImpl.class.getSimpleName());

    @Override
    public void save(User user) {
        LOGGER.info("Saving user with name '{}'.", user.getFirstName());

        var sessionFactory = getSessionFactory();
        Transaction transaction = null;

        try (var session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();

            LOGGER.info("User was saved");
        } catch (Exception ex) {
            if (nonNull(transaction)) {
                transaction.rollback();
                LOGGER.error("User can't be saved");
                throw new InternalServerErrorException(ex);
            }
        }
    }

    @Override
    public User findById(int id) {
        LOGGER.info("Getting user by id = {}", id);

        var sessionFactory = getSessionFactory();

        try (var session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception ex) {
            LOGGER.error("User can't be found");
            throw new InternalServerErrorException(ex);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> findByName(String name) {
        LOGGER.info("Getting user by firstName = {}", name);

        var sessionFactory = getSessionFactory();

        try (var session = sessionFactory.openSession()) {
            var query = session.createQuery("from User where firstName=:firstName");
            query.setParameter("firstName", name);
            return query.list();
        } catch (Exception ex) {
            LOGGER.error("User can't be found");
            throw new InternalServerErrorException(ex);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> findAll() {
        LOGGER.info("Getting all users");

        var sessionFactory = getSessionFactory();

        try (var session = sessionFactory.openSession()) {
            var query = session.createQuery("from User");
            return query.list();
        } catch (Exception ex) {
            LOGGER.error("Users can't be found");
            throw new InternalServerErrorException(ex);
        }
    }

    @Override
    public void delete(User user) {
        var id = user.getId();
        LOGGER.info("Deleting user with id = {}", id);

        var sessionFactory = getSessionFactory();
        Transaction transaction = null;

        try (var session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();

            LOGGER.info("User with id = {} was deleted", id);
        } catch (Exception ex) {
            if (nonNull(transaction)) {
                transaction.rollback();
                LOGGER.error("User can't be deleted");
                throw  new InternalServerErrorException(ex);
            }
        }
    }

    @Override
    public void update(User user) {
        var id = user.getId();
        LOGGER.info("Updating user with id = {}", id);

        var sessionFactory = getSessionFactory();
        Transaction transaction = null;

        try (var session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            LOGGER.info("User is updated");
        } catch (Exception ex) {
            if (nonNull(transaction)) {
                transaction.rollback();
                LOGGER.error("User can't be updated");
                throw  new InternalServerErrorException(ex);
            }
        }
    }
}
