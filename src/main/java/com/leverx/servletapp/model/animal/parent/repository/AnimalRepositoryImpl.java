package com.leverx.servletapp.model.animal.parent.repository;

import com.leverx.servletapp.model.animal.cat.entity.Cat;
import com.leverx.servletapp.model.animal.cat.entity.Cat_;
import com.leverx.servletapp.model.animal.parent.Animal;
import com.leverx.servletapp.model.animal.parent.Animal_;
import com.leverx.servletapp.model.user.entity.User;
import com.leverx.servletapp.model.user.entity.User_;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.Collection;

import static com.leverx.servletapp.db.EntityManagerConfig.getEntityManager;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.emptyCollection;

@Slf4j
public class AnimalRepositoryImpl implements AnimalRepository {

    @Override
    public Collection<Animal> findByOwnerId(int ownerId) {
        log.info("Getting animals by owner id");

        var entityManager = getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            var animals = getCatsByOwnerId(entityManager, ownerId);

            transaction.commit();
            log.info("Animals were found");

            return animals;
        } catch (NoResultException ex) {
            rollbackTransaction(transaction);
            var message = "Animals can't be found";
            log.error(message);
            return emptyCollection();
        } finally {
            entityManager.close();
        }
    }

    private Collection<Animal> getCatsByOwnerId(EntityManager entityManager, int ownerId) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Animal.class);
        var root = criteriaQuery.from(User.class);
        var condition = criteriaBuilder.equal(root.get(User_.id), ownerId);
        criteriaQuery.where(condition);
        var animals = root.join(User_.animals);
        criteriaQuery.select(animals).where(condition);
        var query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    private void rollbackTransaction(EntityTransaction transaction) {
        if (nonNull(transaction) && transaction.isActive()) {
            transaction.rollback();
        }
    }
}
