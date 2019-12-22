package com.leverx.servletapp.dog.repository;

import com.leverx.servletapp.animal.repository.AnimalRepositoryImpl;
import com.leverx.servletapp.dog.entity.Dog;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityTransaction;
import java.util.Collection;
import java.util.Optional;

import static com.leverx.servletapp.db.EntityManagerConfig.getEntityManager;

@Slf4j
public class DogRepositoryImpl extends AnimalRepositoryImpl implements DogRepository {

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
        return super.findById(id, Dog.class);
    }

    @Override
    public Collection<Dog> findByOwnerId(int ownerId) {
        log.info("Getting dogs by owner id");
        return super.findByOwnerId(ownerId, Dog.class);
    }

    @Override
    public Collection<Dog> findAll() {
        log.info("Getting all dogs");
        return super.findAll(Dog.class);
    }
}
