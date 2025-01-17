package com.projectweb.ProjectWeb.dao;

import com.projectweb.ProjectWeb.model.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository // Để Spring quản lý bean này
public class ProductAttributeValuesDao {

    @PersistenceContext // Spring quản lý EntityManager
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Transactional
    public void createProductAttributeValues(Product_Attribute_Values_Entity attributeValue) {
        try {
            entityManager.persist(attributeValue);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error creating attribute value", e);
        }
    }

    public Product_Attribute_Values_Entity getProductAttributeValuesById(Integer ID) {
        try {
            String jpql = "SELECT u FROM Product_Attribute_Values_Entity u WHERE u.ID = :ID";
            TypedQuery<Product_Attribute_Values_Entity> query = entityManager.createQuery(jpql, Product_Attribute_Values_Entity.class);
            query.setParameter("ID", ID);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Trả về null nếu không tìm thấy
        }
    }

    public List<Product_Attribute_Values_Entity> getFilteredProductAttributeValues(
            Integer ID,
            String VALUE,
            Integer ID_ATTRIBUTE,
            String NAME_ATTRIBUTE,
            Integer ID_BASE_PRODUCT,
            String NAME_PRODUCT,
            String sortField,
            String sortOrder,
            Integer offset,
            Integer setOff) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Attribute_Values_Entity> query = cb.createQuery(Product_Attribute_Values_Entity.class);
        Root<Product_Attribute_Values_Entity> root = query.from(Product_Attribute_Values_Entity.class);

        Join<Product_Attribute_Values_Entity, Product_Attribute_Entity> productAttributeJoin = root.join("product_attribute", JoinType.LEFT);
        Join<Product_Attribute_Values_Entity, Product_Base_Entity> productBaseJoin = root.join("product_base", JoinType.LEFT);

        Predicate conditions = cb.conjunction();

        if (ID != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID"), ID));
        }
        if (VALUE != null) {
            conditions = cb.and(conditions, cb.equal(root.get("VALUE"), VALUE));
        }
        if (ID_ATTRIBUTE != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_ATTRIBUTE"), ID_ATTRIBUTE));
        }
        if (NAME_ATTRIBUTE != null) {
            conditions = cb.and(conditions, cb.like(productAttributeJoin.get("NAME_ATTRIBUTE"), "%" + NAME_ATTRIBUTE + "%"));
        }
        if (ID_BASE_PRODUCT != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_BASE_PRODUCT"), ID_BASE_PRODUCT));
        }
        if (NAME_PRODUCT != null) {
            conditions = cb.and(conditions, cb.like(productBaseJoin.get("NAME_PRODUCT"), "%" + NAME_PRODUCT + "%"));
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

        TypedQuery<Product_Attribute_Values_Entity> typedQuery = entityManager.createQuery(query);
        if (offset != null) typedQuery.setFirstResult(offset);
        if (setOff != null) typedQuery.setMaxResults(setOff);

        return typedQuery.getResultList();
    }

    @Transactional
    public void deleteAttributeValues(Integer id, Integer ID_ATTRIBUTE, Integer ID_BASE_PRODUCT) {
        StringBuilder queryStr = new StringBuilder("SELECT po FROM Product_Attribute_Values_Entity po WHERE 1=1");

        if (id != null) queryStr.append(" AND po.ID = :id");
        if (ID_ATTRIBUTE != null) queryStr.append(" AND po.ID_ATTRIBUTE = :ID_ATTRIBUTE");
        if (ID_BASE_PRODUCT != null) queryStr.append(" AND po.ID_BASE_PRODUCT = :ID_BASE_PRODUCT");

        TypedQuery<Product_Attribute_Values_Entity> query = entityManager.createQuery(queryStr.toString(), Product_Attribute_Values_Entity.class);

        if (id != null) query.setParameter("id", id);
        if (ID_ATTRIBUTE != null) query.setParameter("ID_ATTRIBUTE", ID_ATTRIBUTE);
        if (ID_BASE_PRODUCT != null) query.setParameter("ID_BASE_PRODUCT", ID_BASE_PRODUCT);

        List<Product_Attribute_Values_Entity> attributeValues = query.getResultList();
        for (Product_Attribute_Values_Entity optionValue : attributeValues) {
            entityManager.remove(optionValue);
        }
    }

    @Transactional
    public void updateProductAttributeValues(
            Integer ID,
            String VALUE,
            Integer ID_BASE_PRODUCT,
            Integer ID_ATTRIBUTE) {
        Product_Attribute_Values_Entity attributeValue = entityManager.find(Product_Attribute_Values_Entity.class, ID);
        if (attributeValue == null) {
            throw new RuntimeException("Cannot find Product Attribute Value with ID: " + ID);
        }

        if (VALUE != null) attributeValue.setVALUE(VALUE);
        if (ID_ATTRIBUTE != null) attributeValue.setID_ATTRIBUTE(ID_ATTRIBUTE);
        if (ID_BASE_PRODUCT != null) attributeValue.setID_BASE_PRODUCT(ID_BASE_PRODUCT);

        entityManager.merge(attributeValue);
    }
}
