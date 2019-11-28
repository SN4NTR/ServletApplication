package com.leverx.servletapp.cat.repository;

import com.leverx.servletapp.cat.entity.Cat;
import org.slf4j.Logger;

import javax.ws.rs.InternalServerErrorException;
import java.util.Collection;

import static com.leverx.servletapp.db.HibernateConfig.getEntityManager;
import static org.slf4j.LoggerFactory.getLogger;

public class CatRepositoryImpl implements CatRepository {

    private static final Logger LOGGER = getLogger(CatRepositoryImpl.class.getSimpleName());

    @Override
    public void save(Cat cat) {
        LOGGER.info("Saving cat with name '{}'.", cat.getName());

        var entityManager = getEntityManager();
        var transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(cat);
            transaction.commit();

            LOGGER.info("Cat was saved");
        } catch (Exception ex) {
            transaction.rollback();
            LOGGER.error("Cat can't be saved");
            throw new InternalServerErrorException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Cat findById(int id) {
        LOGGER.info("Getting cat with id = {}", id);

        var entityManager = getEntityManager();
        var transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            var cat = entityManager.find(Cat.class, id);
            transaction.commit();

            LOGGER.info("Cat with id = {} was found", id);
            return cat;
        } catch (Exception ex) {
            transaction.rollback();
            LOGGER.error("Cat with id = {} can't be found", id);
            throw new InternalServerErrorException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Cat> findAll() {
        LOGGER.info("Getting all users");

        var entityManager = getEntityManager();
        var transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            var query = entityManager.createQuery("from Cat");
            var cats = query.getResultList();
            transaction.commit();
            LOGGER.info("Cats were found");

            return cats;
        } catch (Exception ex) {
            transaction.rollback();
            LOGGER.error("Cats can't be found");
            throw new InternalServerErrorException(ex);
        } finally {
            entityManager.close();
        }
    }
}
