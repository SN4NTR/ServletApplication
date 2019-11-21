package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.user.entity.User;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static com.leverx.servletapp.db.HibernateConfig.getSessionFactory;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class.getSimpleName());

    private SessionFactory sessionFactory = getSessionFactory();

    @Override
    public void save(User user) {
        LOGGER.info("Saving user with name '{}'.", user.getFirstName());

        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();

        LOGGER.info("User is saved");
    }

    @Override
    public User findById(int id) {
        LOGGER.info("Getting user by id = {}", id);

        var session = sessionFactory.openSession();
        var user = session.get(User.class, id);
        session.close();

        return user;
    }

    @Override
    public Collection<User> findByName(String name) {
        LOGGER.info("Getting user by firstName = {}", name);

        var session = sessionFactory.openSession();
        var query = session.createQuery("from User where firstName=:firstName");
        query.setParameter("firstName", name);
        var users = query.list();
        session.close();

        LOGGER.info("Users are retrieved");

        return users;
    }

    @Override
    public Collection<User> findAll() {
        LOGGER.info("Getting all users");

        var session = sessionFactory.openSession();
        var query = session.createQuery("from User");
        var users = query.list();
        session.close();

        return users;
    }

    @Override
    public void delete(User user) {
        var id = user.getId();
        LOGGER.info("Deleting user with id = {}", id);

        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        LOGGER.info("Updating user with id = {}", user.getId());

        var session = getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();

        LOGGER.info("User is updated");
    }
}
