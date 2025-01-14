package com.projectweb.ProjectWeb.dao;

import com.projectweb.ProjectWeb.model.Category_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.List;

public class CategoryDao {
    private final EntityManager entityManager;

    public CategoryDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createCategory(Category_Entity category) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(category);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Failed to create category", e);
        }
    }

    public List<Category_Entity> getFilteredCategories(Integer id, String name, String sortField, String sortOrder) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category_Entity> query = cb.createQuery(Category_Entity.class);
        Root<Category_Entity> root = query.from(Category_Entity.class);

        Predicate conditions = cb.conjunction();
        if (id != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_CATEGORY"), id));
        }
        if (name != null && !name.trim().isEmpty()) {
            conditions = cb.and(conditions, cb.like(root.get("NAME_CATEGORY"), "%" + name + "%"));
        }

        query.where(conditions);

        if (sortField != null && sortOrder != null) {
            try {
                Path<?> sortPath = root.get(sortField);
                if ("desc".equalsIgnoreCase(sortOrder)) {
                    query.orderBy(cb.desc(sortPath));
                } else {
                    query.orderBy(cb.asc(sortPath));
                }
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid sort field: " + sortField, e);
            }
        }

        return entityManager.createQuery(query).getResultList();
    }

    public void updateCategory(Integer id, String name) {
        try {
            entityManager.getTransaction().begin();
            Category_Entity category = entityManager.find(Category_Entity.class, id);
            if (category == null) throw new RuntimeException("Category with ID " + id + " not found");
            category.setNAME_CATEGORY(name);
            entityManager.merge(category);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Failed to update category", e);
        }
    }

    public void deleteCategoryById(Integer categoryId) {
        try {
            entityManager.getTransaction().begin();
            Category_Entity categoryToDelete = entityManager.find(Category_Entity.class, categoryId);
            if (categoryToDelete != null) {
                entityManager.remove(categoryToDelete);
                entityManager.getTransaction().commit();
            } else {
                entityManager.getTransaction().rollback();
                throw new RuntimeException("Category with ID " + categoryId + " not found");
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Failed to delete category", e);
        }
    }

    public boolean categoryExists(Integer id) {
        Category_Entity category = entityManager.find(Category_Entity.class, id);
        return category != null;
    }
}
