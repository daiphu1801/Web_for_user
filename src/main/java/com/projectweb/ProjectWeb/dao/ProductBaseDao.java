package com.projectweb.ProjectWeb.dao;

import com.projectweb.ProjectWeb.model.Brand_Entity;
import com.projectweb.ProjectWeb.model.Category_Entity;
import com.projectweb.ProjectWeb.model.Product_Base_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository // Để Spring quản lý bean này
public class ProductBaseDao {

    @PersistenceContext // Inject EntityManager do Spring quản lý
    private EntityManager entityManager;

    @Transactional // Đảm bảo mọi thay đổi sẽ được quản lý trong transaction
    public void createProductBase(Product_Base_Entity product) {
        entityManager.persist(product);
    }

    public Product_Base_Entity getProductBaseById(Integer ID_BASE_PRODUCT) {
        try {
            String jpql = "SELECT u FROM Product_Base_Entity u WHERE u.ID_BASE_PRODUCT = :ID_BASE_PRODUCT";
            TypedQuery<Product_Base_Entity> query = entityManager.createQuery(jpql, Product_Base_Entity.class);
            query.setParameter("ID_BASE_PRODUCT", ID_BASE_PRODUCT);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Trả về null nếu không tìm thấy
        }
    }

    public List<Product_Base_Entity> getFilteredProductBase(
            Integer ID_BASE_PRODUCT,
            String NAME_PRODUCT,
            Integer ID_CATEGORY,
            String NAME_CATEGORY,
            Integer ID_BRAND,
            String NAME_BRAND,
            Integer TOTAL_QUANTITY,
            String typeQuantity,
            LocalDateTime DATE_RELEASE,
            String typeDate,
            Integer VIEW_COUNT,
            String typeView,
            String sortField,
            String sortOrder,
            Integer offset,
            Integer setOff) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Base_Entity> query = cb.createQuery(Product_Base_Entity.class);
        Root<Product_Base_Entity> root = query.from(Product_Base_Entity.class);

        Join<Product_Base_Entity, Category_Entity> categoryJoin = root.join("category", JoinType.LEFT);
        Join<Product_Base_Entity, Brand_Entity> brandJoin = root.join("brand", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        // Điều kiện lọc dữ liệu
        if (DATE_RELEASE != null && typeDate != null) {
            switch (typeDate) {
                case "<" -> predicates.add(cb.lessThan(root.get("DATE_RELEASE"), DATE_RELEASE));
                case ">" -> predicates.add(cb.greaterThan(root.get("DATE_RELEASE"), DATE_RELEASE));
                case "=" -> predicates.add(cb.equal(root.get("DATE_RELEASE"), DATE_RELEASE));
                case "<=" -> predicates.add(cb.lessThanOrEqualTo(root.get("DATE_RELEASE"), DATE_RELEASE));
                case ">=" -> predicates.add(cb.greaterThanOrEqualTo(root.get("DATE_RELEASE"), DATE_RELEASE));
            }
        }
        if (ID_BASE_PRODUCT != null) predicates.add(cb.equal(root.get("ID_BASE_PRODUCT"), ID_BASE_PRODUCT));
        if (NAME_PRODUCT != null) predicates.add(cb.like(root.get("NAME_PRODUCT"), "%" + NAME_PRODUCT + "%"));
        if (ID_CATEGORY != null) predicates.add(cb.equal(root.get("ID_CATEGORY"), ID_CATEGORY));
        if (ID_BRAND != null) predicates.add(cb.equal(root.get("ID_BRAND"), ID_BRAND));
        if (TOTAL_QUANTITY != null && typeQuantity != null) {
            switch (typeQuantity) {
                case "<" -> predicates.add(cb.lessThan(root.get("TOTAL_QUANTITY"), TOTAL_QUANTITY));
                case ">" -> predicates.add(cb.greaterThan(root.get("TOTAL_QUANTITY"), TOTAL_QUANTITY));
                case "=" -> predicates.add(cb.equal(root.get("TOTAL_QUANTITY"), TOTAL_QUANTITY));
                case "<=" -> predicates.add(cb.lessThanOrEqualTo(root.get("TOTAL_QUANTITY"), TOTAL_QUANTITY));
                case ">=" -> predicates.add(cb.greaterThanOrEqualTo(root.get("TOTAL_QUANTITY"), TOTAL_QUANTITY));
            }
        }
        if (VIEW_COUNT != null && typeView != null) {
            switch (typeView) {
                case "<" -> predicates.add(cb.lessThan(root.get("VIEW_COUNT"), VIEW_COUNT));
                case ">" -> predicates.add(cb.greaterThan(root.get("VIEW_COUNT"), VIEW_COUNT));
                case "=" -> predicates.add(cb.equal(root.get("VIEW_COUNT"), VIEW_COUNT));
                case "<=" -> predicates.add(cb.lessThanOrEqualTo(root.get("VIEW_COUNT"), VIEW_COUNT));
                case ">=" -> predicates.add(cb.greaterThanOrEqualTo(root.get("VIEW_COUNT"), VIEW_COUNT));
            }
        }

        query.where(predicates.toArray(new Predicate[0]));

        // Sắp xếp dữ liệu
        if (sortField != null && sortOrder != null) {
            Path<?> sortPath = switch (sortField) {
                case "NAME_CATEGORY" -> categoryJoin.get("NAME_CATEGORY");
                case "NAME_BRAND" -> brandJoin.get("NAME_BRAND");
                default -> root.get(sortField.toUpperCase());
            };
            if ("desc".equalsIgnoreCase(sortOrder)) {
                query.orderBy(cb.desc(sortPath));
            } else {
                query.orderBy(cb.asc(sortPath));
            }
        }

        TypedQuery<Product_Base_Entity> typedQuery = entityManager.createQuery(query);
        if (offset != null) typedQuery.setFirstResult(offset);
        if (setOff != null) typedQuery.setMaxResults(setOff);

        return typedQuery.getResultList();
    }

    public Integer getFilteredProductBaseCount(
            Integer ID_BASE_PRODUCT,
            String NAME_PRODUCT,
            Integer ID_CATEGORY,
            String NAME_CATEGORY,
            Integer ID_BRAND,
            String NAME_BRAND,
            Integer TOTAL_QUANTITY,
            String typeQuantity,
            LocalDateTime DATE_RELEASE,
            String typeDate,
            Integer VIEW_COUNT,
            String typeView) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Product_Base_Entity> root = query.from(Product_Base_Entity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (ID_BASE_PRODUCT != null) predicates.add(cb.equal(root.get("ID_BASE_PRODUCT"), ID_BASE_PRODUCT));
        if (NAME_PRODUCT != null) predicates.add(cb.like(root.get("NAME_PRODUCT"), "%" + NAME_PRODUCT + "%"));
        if (ID_CATEGORY != null) predicates.add(cb.equal(root.get("ID_CATEGORY"), ID_CATEGORY));
        if (ID_BRAND != null) predicates.add(cb.equal(root.get("ID_BRAND"), ID_BRAND));

        query.where(predicates.toArray(new Predicate[0]));
        query.select(cb.count(root));

        return Math.toIntExact(entityManager.createQuery(query).getSingleResult());
    }

    @Transactional
    public void deleteProductBase(Integer ID_BASE_PRODUCT) {
        Product_Base_Entity product = entityManager.find(Product_Base_Entity.class, ID_BASE_PRODUCT);
        if (product != null) {
            entityManager.remove(product);
        } else {
            throw new RuntimeException("Product not found with ID: " + ID_BASE_PRODUCT);
        }
    }

    @Transactional
    public boolean updateProductBase(
            Integer ID_BASE_PRODUCT,
            String NAME_PRODUCT,
            String DES_PRODUCT,
            Integer ID_CATEGORY,
            Integer VIEW_COUNT,
            Integer TOTAL_QUANTITY,
            Integer ID_BRAND,
            LocalDateTime DATE_RELEASE) {

        Product_Base_Entity product = entityManager.find(Product_Base_Entity.class, ID_BASE_PRODUCT);
        if (product == null) return false;

        if (NAME_PRODUCT != null) product.setNAME_PRODUCT(NAME_PRODUCT);
        if (DES_PRODUCT != null) product.setDES_PRODUCT(DES_PRODUCT);
        if (ID_CATEGORY != null) product.setID_CATEGORY(ID_CATEGORY);
        if (ID_BRAND != null) product.setID_BRAND(ID_BRAND);
        if (VIEW_COUNT != null) product.setVIEW_COUNT(VIEW_COUNT);
        if (TOTAL_QUANTITY != null) product.setTOTAL_QUANTITY(TOTAL_QUANTITY);
        if (DATE_RELEASE != null) product.setDATE_RELEASE(DATE_RELEASE);

        entityManager.merge(product);
        return true;
    }
}
