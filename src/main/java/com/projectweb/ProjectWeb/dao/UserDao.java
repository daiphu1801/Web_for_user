package com.projectweb.ProjectWeb.dao;

import com.projectweb.ProjectWeb.model.User_Entity;
import com.projectweb.ProjectWeb.service.SecurityFunction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void createUser(User_Entity user) {
        try {
            entityManager.persist(user);
        } catch (RuntimeException e) {
            throw new RuntimeException("Email already used", e);
        }
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public List<User_Entity> getAllUsers() {
        String jpql = "SELECT u FROM User_Entity u";
        TypedQuery<User_Entity> query = entityManager.createQuery(jpql, User_Entity.class);
        return query.getResultList();
    }

    public User_Entity getUsersByID(String id) {
        String jpql = "SELECT u FROM User_Entity u WHERE u.ID_USER = :id";
        TypedQuery<User_Entity> query = entityManager.createQuery(jpql, User_Entity.class);
        query.setParameter("id", id);
        List<User_Entity> result = query.getResultList();
        if (!result.isEmpty()) {
            return result.get(0);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    public User_Entity getUsersByMail(String mail) {
        String jpql = "SELECT u FROM User_Entity u WHERE u.EMAIL_ACC = :mail";
        TypedQuery<User_Entity> query = entityManager.createQuery(jpql, User_Entity.class);
        query.setParameter("mail", mail);
        List<User_Entity> result = query.getResultList();
        if (!result.isEmpty()) {
            return result.get(0);
        } else {
            throw new RuntimeException("User not found with email: " + mail);
        }
    }

    public boolean isUserByMail(String mail) {
        String jpql = "SELECT COUNT(u) FROM User_Entity u WHERE u.EMAIL_ACC = :mail";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("mail", mail);
        return query.getSingleResult() > 0;
    }

    public boolean loginValidate(String email, String password) {
        String jpql = "SELECT u FROM User_Entity u WHERE u.EMAIL_ACC = :email AND u.PASSWORD_ACC = :password";
        TypedQuery<User_Entity> query = entityManager.createQuery(jpql, User_Entity.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return !query.getResultList().isEmpty();
    }

    public void updateUser(String id, String address, String email, String phone, String role, String name) {
        User_Entity user = entityManager.find(User_Entity.class, id);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        if (email != null) user.setEMAIL_ACC(email);
        if (address != null) user.setADDRESS(address);
        if (phone != null) user.setPHONE_ACC(phone);
        if (role != null) user.setROLE_ACC(role);
        if (name != null) user.setNAME_USER(name);
        entityManager.merge(user);
    }

    public void changePasswordByEmail(String email, String newPassword) {
        String jpql = "SELECT u FROM User_Entity u WHERE u.EMAIL_ACC = :email";
        TypedQuery<User_Entity> query = entityManager.createQuery(jpql, User_Entity.class);
        query.setParameter("email", email);
        User_Entity user = query.getResultStream().findFirst().orElseThrow(() ->
                new RuntimeException("User not found with email: " + email)
        );

        String newSalt = SecurityFunction.generateSalt();
        user.setSALT(newSalt);
        String hashedPassword = SecurityFunction.hashString(newPassword + newSalt);
        user.setPASSWORD_ACC(hashedPassword);
        entityManager.merge(user);
    }
}
