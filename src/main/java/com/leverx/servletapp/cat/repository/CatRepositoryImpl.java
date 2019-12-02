package com.leverx.servletapp.cat.repository;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.Cat_;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityTransaction;
import javax.ws.rs.InternalServerErrorException;
import java.util.Collection;

import static com.leverx.servletapp.db.HibernateConfig.getEntityManager;
import static java.util.Objects.nonNull;

@Slf4j
public class CatRepositoryImpl implements CatRepository {

    @Override
    public void save(Cat cat) {
        log.info("Saving cat with name '{}'.", cat.getName());

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(cat);

            transaction.commit();
            log.info("Cat was saved");
        } catch (Exception ex) {
            rollbackTransaction(transaction);
            log.error("Cat can't be saved");
            throw new InternalServerErrorException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Cat findById(int id) {
        log.info("Getting cat with id = {}", id);

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            var criteriaBuilder = entityManager.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Cat.class);

            var root = criteriaQuery.from(Cat.class);
            var fieldName = root.get(Cat_.id);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(fieldName, id));

            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery(criteriaQuery);
            var cat = query.getSingleResult();

            transaction.commit();
            log.info("Cat with id = {} was found", id);
            return cat;
        } catch (Exception ex) {
            rollbackTransaction(transaction);
            log.error("Cat with id = {} can't be found", id);
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<Cat> findAll() {
        log.info("Getting all users");

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            var criteriaBuilder = entityManager.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Cat.class);
            var root = criteriaQuery.from(Cat.class);

            criteriaQuery.select(root);

            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery(criteriaQuery);
            var cats = query.getResultList();

            transaction.commit();
            log.info("Cats were found");
            return cats;
        } catch (Exception ex) {
            rollbackTransaction(transaction);
            log.error("Cats can't be found");
            throw new InternalServerErrorException(ex);
        } finally {
            entityManager.close();
        }
    }

    private void rollbackTransaction(EntityTransaction transaction) {
        if (nonNull(transaction) && transaction.isActive()) {
            transaction.rollback();
        }
    }
}
