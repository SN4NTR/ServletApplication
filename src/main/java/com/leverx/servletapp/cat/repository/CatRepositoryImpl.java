package com.leverx.servletapp.cat.repository;

import com.leverx.servletapp.cat.entity.Cat;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;

import java.util.Collection;

import static com.leverx.servletapp.db.HibernateConfig.getInstance;
import static org.slf4j.LoggerFactory.getLogger;

public class CatRepositoryImpl implements CatRepository {

    private static final Logger LOGGER = getLogger(CatRepositoryImpl.class.getSimpleName());

    @Override
    public void save(Cat cat) {
        LOGGER.info("Saving cat with name '{}'.", cat.getName());
        SessionFactory sessionFactory = getInstance();

        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.save(cat);
        transaction.commit();
        session.close();

        LOGGER.info("Cat was saved");
    }

    @Override
    public Cat findById(int id) {
        LOGGER.info("Getting cat with id = {}", id);
        SessionFactory sessionFactory = getInstance();

        var session = sessionFactory.openSession();
        var cat = session.get(Cat.class, id);
        session.close();

        return cat;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Cat> findAll() {
        LOGGER.info("Getting all users");
        SessionFactory sessionFactory = getInstance();

        var session = sessionFactory.openSession();
        var query = session.createQuery("from Cat");
        var cats = query.list();
        session.close();

        return cats;
    }
}
