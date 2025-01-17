package com.projectweb.ProjectWeb.dao;

import com.projectweb.ProjectWeb.model.Category_Entity;
import com.projectweb.ProjectWeb.model.Product_Attribute_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository // Để Spring quản lý bean này
public class ProductAttributeDao {

    @PersistenceContext // Inject EntityManager do Spring quản lý
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Transactional
    public void createProductAttribute(Product_Attribute_Entity productAttribute) {
        entityManager.persist(productAttribute);
    }

    public List<Product_Attribute_Entity> getFilteredProductAttribute(
            Integer ID_ATTRIBUTE,
            String NAME_ATTRIBUTE,
            Integer ID_CATEGORY,
            String NAME_CATEGORY,
            String sortField,
            String sortOrder,
            Integer setOff,
            Integer offSet) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Attribute_Entity> query = cb.createQuery(Product_Attribute_Entity.class);
        Root<Product_Attribute_Entity> root = query.from(Product_Attribute_Entity.class);
        Join<Product_Attribute_Entity, Category_Entity> categoryJoin = root.join("category", JoinType.LEFT);

        Predicate conditions = cb.conjunction();

        if (NAME_CATEGORY != null) {
            conditions = cb.and(conditions, cb.like(categoryJoin.get("NAME_CATEGORY"), "%" + NAME_CATEGORY + "%"));
        }
        if (ID_ATTRIBUTE != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_ATTRIBUTE"), ID_ATTRIBUTE));
        }
        if (NAME_ATTRIBUTE != null) {
            conditions = cb.and(conditions, cb.like(root.get("NAME_ATTRIBUTE"), "%" + NAME_ATTRIBUTE + "%"));
        }
        if (ID_CATEGORY != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_CATEGORY"), ID_CATEGORY));
        }

        query.where(conditions);

        // Sắp xếp
        if (sortField != null && sortOrder != null) {
            Path<?> sortPath = root.get(sortField);
            if ("desc".equalsIgnoreCase(sortOrder)) {
                query.orderBy(cb.desc(sortPath));
            } else {
                query.orderBy(cb.asc(sortPath));
            }
        }

        TypedQuery<Product_Attribute_Entity> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(offSet != null ? offSet : 0);
        typedQuery.setMaxResults(setOff != null ? setOff : 10);

        return typedQuery.getResultList();
    }

    @Transactional
    public void deleteProductAttributeById(Integer ID_ATTRIBUTE) {
        Product_Attribute_Entity productAttribute = entityManager.find(Product_Attribute_Entity.class, ID_ATTRIBUTE);
        if (productAttribute != null) {
            try {
                entityManager.remove(productAttribute);
            } catch (RuntimeException e) {
                throw new RuntimeException(
                        "Please delete all attribute values belonging to this product attribute: " + ID_ATTRIBUTE, e);
            }
        } else {
            throw new RuntimeException("Cannot find attribute ID to delete: " + ID_ATTRIBUTE);
        }
    }

    @Transactional
    public void updateProductAttribute(Integer ID_ATTRIBUTE, Integer ID_CATEGORY, String NAME_ATTRIBUTE) {
        Product_Attribute_Entity productAttribute = entityManager.find(Product_Attribute_Entity.class, ID_ATTRIBUTE);
        if (productAttribute == null) {
            throw new RuntimeException("Cannot find Product Attribute with ID: " + ID_ATTRIBUTE);
        }

        if (ID_CATEGORY != null) productAttribute.setID_CATEGORY(ID_CATEGORY);
        if (NAME_ATTRIBUTE != null) productAttribute.setNAME_ATTRIBUTE(NAME_ATTRIBUTE);

        try {
            entityManager.merge(productAttribute);
        } catch (RuntimeException e) {
            throw new RuntimeException(
                    "Error occurred while updating Product Attribute with ID: " + ID_ATTRIBUTE, e);
        }
    }
}
