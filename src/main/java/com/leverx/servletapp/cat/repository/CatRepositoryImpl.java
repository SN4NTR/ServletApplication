package com.leverx.servletapp.cat.repository;

import com.leverx.servletapp.cat.entity.Cat;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static com.leverx.servletapp.db.HibernateConfig.getSessionFactory;

public class CatRepositoryImpl implements CatRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatRepositoryImpl.class.getSimpleName());

    private SessionFactory sessionFactory = getSessionFactory();

    @Override
    public void save(Cat cat) {
        LOGGER.info("Saving cat with name '{}'.", cat.getName());

        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.save(cat);
        transaction.commit();
        session.close();

        LOGGER.info("Cat is saved");
    }

    @Override
    public Cat findById(int id) {
        LOGGER.info("Getting cat with id = {}", id);

        var session = sessionFactory.openSession();
        var cat = session.get(Cat.class, id);
        session.close();

        return cat;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Cat> findAll() {
        LOGGER.info("Getting all users");

        var session = sessionFactory.openSession();
        var query = session.createQuery("from Cat");
        var cats = query.list();
        session.close();

        return cats;
    }
}
