package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.user.entity.User;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;

import java.util.Collection;

import static com.leverx.servletapp.db.HibernateConfig.getInstance;
import static org.slf4j.LoggerFactory.getLogger;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = getLogger(UserRepositoryImpl.class.getSimpleName());

    @Override
    public void save(User user) {
        LOGGER.info("Saving user with name '{}'.", user.getFirstName());
        SessionFactory sessionFactory = getInstance();

        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();

        LOGGER.info("User was saved");
    }

    @Override
    public User findById(int id) {
        LOGGER.info("Getting user by id = {}", id);
        SessionFactory sessionFactory = getInstance();

        var session = sessionFactory.openSession();
        var user = session.get(User.class, id);
        session.close();

        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> findByName(String name) {
        LOGGER.info("Getting user by firstName = {}", name);
        SessionFactory sessionFactory = getInstance();

        var session = sessionFactory.openSession();
        var query = session.createQuery("from User where firstName=:firstName");
        query.setParameter("firstName", name);
        var users = query.list();
        session.close();

        return users;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> findAll() {
        LOGGER.info("Getting all users");
        SessionFactory sessionFactory = getInstance();

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
        SessionFactory sessionFactory = getInstance();

        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        LOGGER.info("Updating user with id = {}", user.getId());
        SessionFactory sessionFactory = getInstance();

        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();

        LOGGER.info("User is updated");
    }
}
