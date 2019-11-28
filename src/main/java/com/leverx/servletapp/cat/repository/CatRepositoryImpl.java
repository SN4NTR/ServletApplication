package com.leverx.servletapp.cat.repository;

import com.leverx.servletapp.cat.entity.Cat;
import org.hibernate.Transaction;
import org.slf4j.Logger;

import javax.ws.rs.InternalServerErrorException;
import java.util.Collection;

import static com.leverx.servletapp.db.HibernateConfig.getSessionFactory;
import static java.util.Objects.nonNull;
import static org.slf4j.LoggerFactory.getLogger;

public class CatRepositoryImpl implements CatRepository {

    private static final Logger LOGGER = getLogger(CatRepositoryImpl.class.getSimpleName());

    @Override
    public void save(Cat cat) {
        LOGGER.info("Saving cat with name '{}'.", cat.getName());

        var sessionFactory = getSessionFactory();
        Transaction transaction = null;

        try (var session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(cat);
            transaction.commit();

            LOGGER.info("Cat was saved");
        } catch (Exception ex) {
            if (nonNull(transaction)) {
                transaction.rollback();
                LOGGER.error("Cat can't be saved");
                throw new InternalServerErrorException(ex);
            }
        }
    }

    @Override
    public Cat findById(int id) {
        LOGGER.info("Getting cat with id = {}", id);

        var sessionFactory = getSessionFactory();

        try (var session = sessionFactory.openSession()) {
            return session.get(Cat.class, id);
        } catch (Exception ex) {
            LOGGER.error("Cat with id = {} can't be found", id);
            throw new InternalServerErrorException(ex);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Cat> findAll() {
        LOGGER.info("Getting all users");

        var sessionFactory = getSessionFactory();

        try (var session = sessionFactory.openSession()) {
            var query = session.createQuery("from Cat");
            return query.list();
        } catch (Exception ex) {
            LOGGER.error("Cats can't be found");
            throw new InternalServerErrorException(ex);
        }
    }
}
