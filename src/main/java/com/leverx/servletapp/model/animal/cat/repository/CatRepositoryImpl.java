package com.leverx.servletapp.model.animal.cat.repository;

import com.leverx.servletapp.model.animal.Animal;
import com.leverx.servletapp.model.animal.cat.entity.Cat;
import com.leverx.servletapp.model.animal.cat.entity.Cat_;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.Optional;

import static com.leverx.servletapp.db.EntityManagerConfig.getEntityManager;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.emptyCollection;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

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
        } catch (EntityExistsException ex) {
            rollbackTransaction(transaction);
            log.error("Cat can't be saved");
            throw new IllegalArgumentException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Cat> findById(int id) {
        log.info("Getting cat with id = {}", id);

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            var criteriaQuery = getCriteriaQueryByAttributes(entityManager, Cat_.id, id);
            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery(criteriaQuery);
            var cat = (Cat) query.getSingleResult();

            transaction.commit();
            log.info("Cat with id = {} was found", id);

            return Optional.of(cat);
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Cat can't be found";
            log.error(message);
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<Cat> findByOwnerId(int id) {
        log.info("Getting cat by owner id = {}", id);

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            var criteriaBuilder = entityManager.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Cat.class);

            var root = criteriaQuery.from(Cat.class);
            var fieldName = root.get(Cat_.OWNERS);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(fieldName, id));

            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery(criteriaQuery);
            var cats = query.getResultList();
            if (isEmpty(cats)) {
                throw new NoResultException();
            }

            transaction.commit();
            log.info("Cats were found");

            return cats;
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Cats can't be found";
            log.error(message);
            return emptyCollection();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<Cat> findAll() {
        log.info("Getting all cats");

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            var criteriaQuery = getCriteriaQuery(entityManager);
            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery(criteriaQuery);
            var cats = query.getResultList();

            transaction.commit();
            log.info("Cats were found");

            return cats;
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Cat can't be found";
            log.error(message);
            return emptyCollection();
        } finally {
            entityManager.close();
        }
    }

    private CriteriaQuery<Cat> getCriteriaQuery(EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Cat.class);
        var root = criteriaQuery.from(Cat.class);
        criteriaQuery.select(root);
        return criteriaQuery;
    }

    private CriteriaQuery<Animal> getCriteriaQueryByAttributes(EntityManager entityManager, SingularAttribute<Animal, ?> attribute, Object compareWith) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Animal.class);
        var root = criteriaQuery.from(Animal.class);
        var fieldName = root.get(attribute);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(fieldName, compareWith));

        return criteriaQuery;
    }

    private void rollbackTransaction(EntityTransaction transaction) {
        if (nonNull(transaction) && transaction.isActive()) {
            transaction.rollback();
        }
    }
}
