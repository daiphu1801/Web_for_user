package com.projectweb.ProjectWeb.dao;

import com.projectweb.ProjectWeb.model.Brand_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BrandDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void createBrand(Brand_Entity brand) {
        entityManager.persist(brand);
    }



    public List<Brand_Entity> getAllBrands() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand_Entity> query = cb.createQuery(Brand_Entity.class);
        Root<Brand_Entity> root = query.from(Brand_Entity.class);
        query.select(root); // Lấy tất cả các cột
        return entityManager.createQuery(query).getResultList();
    }

    public List<Brand_Entity> getFilteredBrand(Integer id, String name, String sortField, String sortOrder) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand_Entity> query = cb.createQuery(Brand_Entity.class);
        Root<Brand_Entity> root = query.from(Brand_Entity.class);

        Predicate conditions = cb.conjunction();

        if (id != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_BRAND"), id));
        }

        if (name != null && !name.trim().isEmpty()) {
            conditions = cb.and(conditions, cb.like(root.get("NAME_BRAND"), "%" + name + "%"));
        }

        query.where(conditions);

        if (sortField != null && sortOrder != null) {
            Path<?> sortPath = root.get(sortField);
            if ("desc".equalsIgnoreCase(sortOrder)) {
                query.orderBy(cb.desc(sortPath));
            } else {
                query.orderBy(cb.asc(sortPath));
            }
        }

        TypedQuery<Brand_Entity> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    public Integer getFilteredBrandCount(Integer id, String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Brand_Entity> root = query.from(Brand_Entity.class);

        Predicate conditions = cb.conjunction();

        if (id != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_BRAND"), id));
        }

        if (name != null && !name.trim().isEmpty()) {
            conditions = cb.and(conditions, cb.like(root.get("NAME_BRAND"), "%" + name + "%"));
        }

        query.where(conditions);
        query.select(cb.count(root));

        Long result = entityManager.createQuery(query).getSingleResult();
        return result != null ? result.intValue() : 0;
    }

    public void updateBrand(Integer id, String name, String detail) {
        Brand_Entity brand = entityManager.find(Brand_Entity.class, id);
        if (brand == null) {
            throw new RuntimeException("Brand not found");
        }
        if (name != null) {
            brand.setNAME_BRAND(name);
        }
        if (detail != null) {
            brand.setDETAIL(detail);
        }
        entityManager.merge(brand);
    }

    public void deleteBrandById(Integer brandId) {
        Brand_Entity brand = entityManager.find(Brand_Entity.class, brandId);
        if (brand == null) {
            throw new RuntimeException("Brand not found");
        }
        entityManager.remove(brand);
    }
}
