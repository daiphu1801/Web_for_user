package com.projectweb.ProjectWeb.dao;

import com.projectweb.ProjectWeb.model.Category_Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

//    public CategoryDao(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    public List<Category_Entity> getAllCategories() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category_Entity> query = cb.createQuery(Category_Entity.class);
        Root<Category_Entity> root = query.from(Category_Entity.class);
        query.select(root); // Lấy tất cả các cột
        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    public void createCategory(Category_Entity category) {
        try {
            entityManager.persist(category);
        } catch (Exception e) {
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

    @Transactional
    public void updateCategory(Integer id, String name) {
        try {
            Category_Entity category = entityManager.find(Category_Entity.class, id);
            if (category == null) throw new RuntimeException("Category with ID " + id + " not found");
            category.setNAME_CATEGORY(name);
            entityManager.merge(category);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update category", e);
        }
    }

    @Transactional
    public void deleteCategoryById(Integer categoryId) {
        try {
            Category_Entity categoryToDelete = entityManager.find(Category_Entity.class, categoryId);
            if (categoryToDelete != null) {
                entityManager.remove(categoryToDelete);
            } else {
                throw new RuntimeException("Category with ID " + categoryId + " not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete category", e);
        }
    }

    public boolean categoryExists(Integer id) {
        Category_Entity category = entityManager.find(Category_Entity.class, id);
        return category != null;
    }
}
