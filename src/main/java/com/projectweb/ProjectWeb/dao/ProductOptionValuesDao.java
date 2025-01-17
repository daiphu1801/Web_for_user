package com.projectweb.ProjectWeb.dao;

import com.projectweb.ProjectWeb.model.Product_Final_Entity;
import com.projectweb.ProjectWeb.model.Product_Option_Entity;
import com.projectweb.ProjectWeb.model.Product_Option_Values_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository // Để Spring quản lý bean này
public class ProductOptionValuesDao {

    @PersistenceContext // Inject EntityManager do Spring quản lý
    private EntityManager entityManager;

    @Transactional // Đảm bảo mọi thay đổi được thực hiện trong transaction
    public void createProductOptionValues(Product_Option_Values_Entity optionValue) {
        entityManager.persist(optionValue);
    }

    @Transactional
    public void updateProductOptionValues(Integer id, String value, Integer idOption, Integer idFinalProduct) {
        Product_Option_Values_Entity optionValue = entityManager.find(Product_Option_Values_Entity.class, id);
        if (optionValue == null) {
            throw new RuntimeException("Cannot find option value to update with ID: " + id);
        }
        if (value != null) optionValue.setVALUE(value);
        if (idOption != null) optionValue.setID_OPTION(idOption);
        if (idFinalProduct != null) optionValue.setID_FINAL_PRODUCT(idFinalProduct);
        entityManager.merge(optionValue);
    }

    public List<Product_Option_Values_Entity> getFilteredProductOptionValue(
            Integer id,
            Integer idOption,
            String nameOption,
            String value,
            Integer idFinalProduct,
            String nameProduct,
            String sortField,
            String sortOrder,
            Integer offset,
            Integer setOff) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Option_Values_Entity> query = cb.createQuery(Product_Option_Values_Entity.class);
        Root<Product_Option_Values_Entity> root = query.from(Product_Option_Values_Entity.class);

        Join<Product_Option_Values_Entity, Product_Option_Entity> productOptionJoin = root.join("product_options", JoinType.LEFT);
        Join<Product_Option_Values_Entity, Product_Final_Entity> productFinalJoin = root.join("product_final", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        if (id != null) predicates.add(cb.equal(root.get("ID"), id));
        if (idOption != null) predicates.add(cb.equal(root.get("ID_OPTION"), idOption));
        if (nameOption != null) predicates.add(cb.like(productOptionJoin.get("NAME_OPTION"), "%" + nameOption + "%"));
        if (value != null) predicates.add(cb.equal(root.get("VALUE"), value));
        if (idFinalProduct != null) predicates.add(cb.equal(root.get("ID_FINAL_PRODUCT"), idFinalProduct));
        if (nameProduct != null) predicates.add(cb.like(productFinalJoin.get("NAME_PRODUCT"), "%" + nameProduct + "%"));

        query.where(predicates.toArray(new Predicate[0]));

        if (sortField != null && sortOrder != null) {
            Path<?> sortPath = root.get(sortField);
            if ("desc".equalsIgnoreCase(sortOrder)) {
                query.orderBy(cb.desc(sortPath));
            } else {
                query.orderBy(cb.asc(sortPath));
            }
        }

        TypedQuery<Product_Option_Values_Entity> typedQuery = entityManager.createQuery(query);
        if (offset != null) typedQuery.setFirstResult(offset);
        if (setOff != null) typedQuery.setMaxResults(setOff);

        return typedQuery.getResultList();
    }

    public Integer getFilteredProductOptionValueCount(
            Integer id,
            Integer idOption,
            String nameOption,
            String value,
            Integer idFinalProduct,
            String nameProduct) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Product_Option_Values_Entity> root = query.from(Product_Option_Values_Entity.class);

        Join<Product_Option_Values_Entity, Product_Option_Entity> productOptionJoin = root.join("product_options", JoinType.LEFT);
        Join<Product_Option_Values_Entity, Product_Final_Entity> productFinalJoin = root.join("product_final", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        if (id != null) predicates.add(cb.equal(root.get("ID"), id));
        if (idOption != null) predicates.add(cb.equal(root.get("ID_OPTION"), idOption));
        if (nameOption != null) predicates.add(cb.like(productOptionJoin.get("NAME_OPTION"), "%" + nameOption + "%"));
        if (value != null) predicates.add(cb.equal(root.get("VALUE"), value));
        if (idFinalProduct != null) predicates.add(cb.equal(root.get("ID_FINAL_PRODUCT"), idFinalProduct));
        if (nameProduct != null) predicates.add(cb.like(productFinalJoin.get("NAME_PRODUCT"), "%" + nameProduct + "%"));

        query.where(predicates.toArray(new Predicate[0]));
        query.select(cb.count(root));

        Long count = entityManager.createQuery(query).getSingleResult();
        return count != null ? count.intValue() : 0;
    }

    @Transactional
    public void deleteOptionValues(Integer id, Integer idOption, Integer idFinalProduct) {
        StringBuilder queryStr = new StringBuilder("SELECT po FROM Product_Option_Values_Entity po WHERE 1=1");
        if (id != null) queryStr.append(" AND po.ID = :id");
        if (idOption != null) queryStr.append(" AND po.ID_OPTION = :idOption");
        if (idFinalProduct != null) queryStr.append(" AND po.ID_FINAL_PRODUCT = :idFinalProduct");

        TypedQuery<Product_Option_Values_Entity> query = entityManager.createQuery(queryStr.toString(), Product_Option_Values_Entity.class);

        if (id != null) query.setParameter("id", id);
        if (idOption != null) query.setParameter("idOption", idOption);
        if (idFinalProduct != null) query.setParameter("idFinalProduct", idFinalProduct);

        List<Product_Option_Values_Entity> optionValues = query.getResultList();
        if (optionValues.isEmpty()) {
            throw new RuntimeException("Cannot find Option Values to delete");
        }

        for (Product_Option_Values_Entity optionValue : optionValues) {
            entityManager.remove(optionValue);
        }
    }
}
