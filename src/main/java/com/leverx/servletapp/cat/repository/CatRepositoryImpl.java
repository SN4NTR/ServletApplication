package com.leverx.servletapp.cat.repository;

import com.leverx.servletapp.cat.entity.Cat;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.InternalServerErrorException;
import java.util.Collection;

import static com.leverx.servletapp.db.HibernateConfig.getEntityManagerFactory;
import static java.util.Objects.nonNull;
import static org.slf4j.LoggerFactory.getLogger;

public class CatRepositoryImpl implements CatRepository {

    private static final Logger LOGGER = getLogger(CatRepositoryImpl.class.getSimpleName());

    @Override
    public void save(Cat cat) {
        LOGGER.info("Saving cat with name '{}'.", cat.getName());

        var entityManagerFactory = getEntityManagerFactory();
        EntityTransaction transaction = null;
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(cat);
            transaction.commit();

            LOGGER.info("Cat was saved");
        } catch (Exception ex) {
            if (nonNull(transaction)) {
                transaction.rollback();
            }
            LOGGER.error("Cat can't be saved");
            throw new InternalServerErrorException(ex);
        } finally {
            if (nonNull(entityManager)) {
                entityManager.close();
            }
        }
    }

    @Override
    public Cat findById(int id) {
        LOGGER.info("Getting cat with id = {}", id);

        var entityManagerFactory = getEntityManagerFactory();
        EntityTransaction transaction = null;
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            var cat = entityManager.find(Cat.class, id);
            transaction.commit();
            LOGGER.info("Cat with id = {} was found", id);

            return cat;
        } catch (Exception ex) {
            if (nonNull(transaction)) {
                transaction.rollback();
            }
            LOGGER.error("Cat with id = {} can't be found", id);
            throw new InternalServerErrorException(ex);
        } finally {
            if (nonNull(entityManager)) {
                entityManager.close();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Cat> findAll() {
        LOGGER.info("Getting all users");

        var entityManagerFactory = getEntityManagerFactory();
        EntityTransaction transaction = null;
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery("from Cat");
            var cats = query.getResultList();
            transaction.commit();
            LOGGER.info("Cats were found");

            return cats;
        } catch (Exception ex) {
            if (nonNull(transaction)) {
                transaction.rollback();
            }
            LOGGER.error("Cats can't be found");
            throw new InternalServerErrorException(ex);
        } finally {
            if (nonNull(entityManager)) {
                entityManager.close();
            }
        }
    }
}
