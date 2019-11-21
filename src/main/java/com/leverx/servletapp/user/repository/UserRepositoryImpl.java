package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.user.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

import static com.leverx.servletapp.db.HibernateConfig.getSessionFactory;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class.getSimpleName());

    private SessionFactory sessionFactory = getSessionFactory();

    @Override
    public void save(User user) {
        LOGGER.info("Saving user with name '{}'.", user.getFirstName());

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();

        LOGGER.info("User is saved");
    }

    @Override
    public User findById(int id) {
        LOGGER.info("Getting user by id = {}", id);

        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();

        return user;
    }

    @Override
    public Collection<User> findByName(String name) {
        LOGGER.info("Getting user by firstName = {}", name);

        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from User where firstName=:firstName");
        query.setParameter("firstName", name);
        List users = query.list();
        session.close();

        LOGGER.info("Users are retrieved");

        return users;
    }

    @Override
    public Collection<User> findAll() {
        LOGGER.info("Getting all users");

        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from User");
        List users = query.list();
        session.close();

        return users;
    }

    @Override
    public void delete(User user) {
        var id = user.getId();
        LOGGER.info("Deleting user with id = {}", id);

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        LOGGER.info("Updating user with id = {}", user.getId());

        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();

        LOGGER.info("User is updated");
    }
}
