package com.leverx.servletapp.model.animal.dog.repository;

import com.leverx.servletapp.model.animal.Animal;
import com.leverx.servletapp.model.animal.dog.entity.Dog;
import com.leverx.servletapp.model.animal.dog.entity.Dog_;
import com.leverx.servletapp.model.user.entity.User;
import com.leverx.servletapp.model.user.entity.User_;
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
public class DogRepositoryImpl implements DogRepository {

    @Override
    public void save(Dog dog) {
        log.info("Saving dog with name '{}'.", dog.getName());

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(dog);

            transaction.commit();
            log.info("Dog was saved");
        } catch (EntityExistsException ex) {
            rollbackTransaction(transaction);
            log.error("Dog can't be saved");
            throw new IllegalArgumentException(ex);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Dog> findById(int id) {
        log.info("Getting dog with id = {}", id);

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            var criteriaQuery = getCriteriaQueryByAttributes(entityManager, Dog_.id, id);
            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery(criteriaQuery);
            var dog = (Dog) query.getSingleResult();

            transaction.commit();
            log.info("Dog with id = {} was found", id);

            return Optional.of(dog);
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Dog can't be found";
            log.error(message);
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<Dog> findByOwnerId(int id) {
        log.info("Getting dog by owner id = {}", id);

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            var criteriaBuilder = entityManager.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Dog.class);

            var root = criteriaQuery.from(Dog.class);
            var fieldName = root.get(Dog_.OWNERS);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(fieldName, id));

            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery(criteriaQuery);
            var dogs = query.getResultList();
            if (isEmpty(dogs)) {
                throw new NoResultException();
            }

            transaction.commit();
            log.info("Dogs were found");

            return dogs;
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Dogs can't be found";
            log.error(message);
            return emptyCollection();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<Dog> findAll() {
        log.info("Getting all dogs");

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            var criteriaQuery = getCriteriaQuery(entityManager);
            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery(criteriaQuery);
            var dogs = query.getResultList();

            transaction.commit();
            log.info("Dogs were found");

            return dogs;
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Dog can't be found";
            log.error(message);
            return emptyCollection();
        } finally {
            entityManager.close();
        }
    }

    private CriteriaQuery<Dog> getCriteriaQuery(EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Dog.class);
        var root = criteriaQuery.from(Dog.class);
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
