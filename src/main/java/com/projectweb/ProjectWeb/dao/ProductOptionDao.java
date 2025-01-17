package com.projectweb.ProjectWeb.dao;

import com.projectweb.ProjectWeb.model.Product_Option_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository // Để Spring quản lý bean này
public class ProductOptionDao {

    @PersistenceContext // Inject EntityManager do Spring quản lý
    private EntityManager entityManager;

    @Transactional // Đảm bảo mọi thay đổi được thực hiện trong transaction
    public void createProductOption(Product_Option_Entity productOption) {
        entityManager.persist(productOption);
    }

    public List<Product_Option_Entity> getFilteredProductOption(
            Integer id,
            String name,
            String sortField,
            String sortOrder,
            Integer setOff,
            Integer offSet) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Option_Entity> query = cb.createQuery(Product_Option_Entity.class);
        Root<Product_Option_Entity> root = query.from(Product_Option_Entity.class);

        List<Predicate> predicates = new ArrayList<>();

        // Điều kiện lọc
        if (id != null) {
            predicates.add(cb.equal(root.get("ID_OPTION"), id));
        }
        if (name != null) {
            predicates.add(cb.like(root.get("NAME_OPTION"), "%" + name + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));

        // Sắp xếp
        if (sortField != null && sortOrder != null) {
            Path<?> sortPath = root.get(sortField);
            if ("desc".equalsIgnoreCase(sortOrder)) {
                query.orderBy(cb.desc(sortPath));
            } else {
                query.orderBy(cb.asc(sortPath));
            }
        }

        TypedQuery<Product_Option_Entity> typedQuery = entityManager.createQuery(query);
        if (offSet != null) typedQuery.setFirstResult(offSet);
        if (setOff != null) typedQuery.setMaxResults(setOff);

        return typedQuery.getResultList();
    }

    @Transactional
    public void deleteProductOptionById(Integer id) {
        Product_Option_Entity productOption = entityManager.find(Product_Option_Entity.class, id);
        if (productOption != null) {
            try {
                entityManager.remove(productOption);
            } catch (RuntimeException e) {
                throw new RuntimeException("Please delete all option values belonging to this product option: " + id, e);
            }
        } else {
            throw new RuntimeException("Cannot find product option with ID: " + id);
        }
    }

    @Transactional
    public void updateProductOption(Integer id, String name) {
        Product_Option_Entity productOption = entityManager.find(Product_Option_Entity.class, id);
        if (productOption == null) {
            throw new RuntimeException("Cannot find Product Option to update with ID: " + id);
        }
        if (name != null) {
            productOption.setNAME_OPTION(name);
        }
        entityManager.merge(productOption);
    }

    public Integer getFilteredProductOptionCount(Integer id, String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Product_Option_Entity> root = query.from(Product_Option_Entity.class);

        List<Predicate> predicates = new ArrayList<>();

        // Điều kiện lọc
        if (id != null) {
            predicates.add(cb.equal(root.get("ID_OPTION"), id));
        }
        if (name != null) {
            predicates.add(cb.like(root.get("NAME_OPTION"), "%" + name + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));
        query.select(cb.count(root));

        Long count = entityManager.createQuery(query).getSingleResult();
        return count != null ? count.intValue() : 0;
    }
}
