package fr.istic.taa.jaxrs.dao.generic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractJpaDao<K, T extends Serializable> implements IGenericDao<K, T> {

    private Class<T> clazz;

    protected EntityManager entityManager;

    public AbstractJpaDao() {
        this.entityManager = EntityManagerHelper.getEntityManager();
    }

    public void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T findOne(K id) {
        return entityManager.find(clazz, id);
    }

    public List<T> findAll() {
        return entityManager.createQuery("select e from " + clazz.getName() + " as e", clazz).getResultList();
    }

    public void deleteAll() {
        EntityTransaction t = this.entityManager.getTransaction();
        t.begin();
        entityManager.createQuery("DELETE FROM " + clazz.getName()).executeUpdate();
        t.commit();
    }


    public void save(T entity) {
        EntityTransaction t = this.entityManager.getTransaction();
        t.begin();
        entityManager.persist(entity);
        t.commit();

    }

    public T update(final T entity) {
        EntityTransaction t = this.entityManager.getTransaction();
        t.begin();
        T res = entityManager.merge(entity);
        t.commit();
        return res;

    }

    public void delete(T entity) {
        EntityTransaction t = this.entityManager.getTransaction();
        t.begin();
        entityManager.remove(entity);
        t.commit();

    }

    public void deleteById(K entityId) {
        T entity = findOne(entityId);
        delete(entity);
    }

    public T findByEmailAndPassword(String email, String password) {
        Query query = entityManager.createQuery("SELECT u FROM " + clazz.getName() + " u WHERE u.email = :email AND u.password = :password");
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<T> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0); // Supposant qu'il n'y a qu'un seul utilisateur avec cet email et ce mot de passe
        } else {
            return null; // Aucun utilisateur trouvé avec ces identifiants
        }
    }

    public T findByRoleString(String role) {
        Query query = entityManager.createQuery("SELECT u FROM " + clazz.getName() + " u WHERE u.roleString = :role");
        query.setParameter("role", role);
        List<T> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0); // Supposant qu'il n'y a qu'un seul utilisateur avec cet email et ce mot de passe
        } else {
            return null; // Aucun utilisateur trouvé avec ces identifiants
        }
    }
}
