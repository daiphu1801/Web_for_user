package com.projectweb.ProjectWeb.dao;

import com.projectweb.ProjectWeb.model.Brand_Entity;
import com.projectweb.ProjectWeb.model.Category_Entity;
import com.projectweb.ProjectWeb.model.Product_Base_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.List;

public class ProductBaseDao {
    private final EntityManager entityManager;


    public ProductBaseDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void createProductBase(Product_Base_Entity product) {
        entityManager.persist(product);
    }

    public Product_Base_Entity getProductBaseById(Integer ID_BASE_PRODUCT) {
        String jpql = "SELECT u FROM Product_Base_Entity u WHERE u.ID_BASE_PRODUCT = :ID_BASE_PRODUCT";
        TypedQuery<Product_Base_Entity> query = entityManager.createQuery(jpql, Product_Base_Entity.class);
        query.setParameter("ID_BASE_PRODUCT", ID_BASE_PRODUCT);
        return query.getSingleResult();

    }


    public List<Product_Base_Entity> getFilteredProductBase(Integer ID_BASE_PRODUCT,
                                                            String NAME_PRODUCT,
                                                            Integer ID_CATEGORY,
                                                            String NAME_CATEGORY,
                                                            Integer ID_BRAND,
                                                            String NAME_BRAND,
                                                            Integer TOTAL_QUANTITY,
                                                            String typeQuantity,
                                                            LocalDateTime DATE_RELEASE,
                                                            String typeDate,
                                                            Integer ViewCount,
                                                            String typeView,
                                                            String sortField,
                                                            String sortOrder,
                                                            Integer offset,
                                                            Integer setOff) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Base_Entity> query = cb.createQuery(Product_Base_Entity.class);
        Root<Product_Base_Entity> root = query.from(Product_Base_Entity.class);

        Join<Product_Base_Entity, Category_Entity> categoryJoin = root.join("category", JoinType.INNER);
        Join<Product_Base_Entity, Brand_Entity> brandJoin = root.join("brand", JoinType.INNER);

        Predicate conditions = cb.conjunction();
        boolean hasConditions = false;

        if (DATE_RELEASE != null) {
            conditions = switch (typeDate) {
                case "<" -> cb.and(conditions, cb.lessThan(root.get("DATE_RELEASE"), DATE_RELEASE));
                case ">" -> cb.and(conditions, cb.greaterThan(root.get("DATE_RELEASE"), DATE_RELEASE));
                case "=" -> cb.and(conditions, cb.equal(root.get("DATE_RELEASE"), DATE_RELEASE));
                case "<=" -> cb.and(conditions, cb.lessThanOrEqualTo(root.get("DATE_RELEASE"), DATE_RELEASE));
                case "=>" -> cb.and(conditions, cb.greaterThanOrEqualTo(root.get("DATE_RELEASE"), DATE_RELEASE));
                default -> conditions;
            };
            hasConditions = true;
        }
        if (ID_BASE_PRODUCT != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_BASE_PRODUCT"), ID_BASE_PRODUCT));
            hasConditions = true;
        }
        if (NAME_PRODUCT != null) {
            conditions = cb.and(conditions, cb.like(root.get("NAME_PRODUCT"), "%" + NAME_PRODUCT + "%"));
            hasConditions = true;
        }
        if (ID_CATEGORY != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_CATEGORY"), ID_CATEGORY));
            hasConditions = true;
        }
        if (NAME_CATEGORY != null) {
            conditions = cb.and(conditions, cb.like(categoryJoin.get("NAME_CATEGORY"), "%" + NAME_CATEGORY + "%"));
            hasConditions = true;
        }
        if (ID_BRAND != null) {
            conditions = cb.and(conditions, cb.equal(root.get("ID_BRAND"), ID_BRAND));
            hasConditions = true;
        }
        if (NAME_BRAND != null) {
            conditions = cb.and(conditions, cb.like(brandJoin.get("NAME_BRAND"), "%" + NAME_BRAND + "%"));
            hasConditions = true;
        }
        if (TOTAL_QUANTITY != null) {
            conditions = switch (typeQuantity) {
                case "<" -> cb.and(conditions, cb.lessThan(root.get("TOTAL_QUANTITY"), TOTAL_QUANTITY));
                case ">" -> cb.and(conditions, cb.greaterThan(root.get("TOTAL_QUANTITY"), TOTAL_QUANTITY));
                case "=" -> cb.and(conditions, cb.equal(root.get("TOTAL_QUANTITY"), TOTAL_QUANTITY));
                case "<=" -> cb.and(conditions, cb.lessThanOrEqualTo(root.get("TOTAL_QUANTITY"), TOTAL_QUANTITY));
                case "=>" -> cb.and(conditions, cb.greaterThanOrEqualTo(root.get("TOTAL_QUANTITY"), TOTAL_QUANTITY));
                default -> conditions;
            };
            hasConditions = true;
        }
        if (ViewCount != null) {
            conditions = switch (typeView) {
                case "<" -> cb.and(conditions, cb.lessThan(root.get("ViewCount"), ViewCount));
                case ">" -> cb.and(conditions, cb.greaterThan(root.get("ViewCount"), ViewCount));
                case "=" -> cb.and(conditions, cb.equal(root.get("ViewCount"), ViewCount));
                case "<=" -> cb.and(conditions, cb.lessThanOrEqualTo(root.get("ViewCount"), ViewCount));
                case "=>" -> cb.and(conditions, cb.greaterThanOrEqualTo(root.get("ViewCount"), ViewCount));
                default -> conditions;
            };
            hasConditions = true;
        }

        if (hasConditions) {
            query.where(conditions);
        } else {
            query.select(root);
        }

        if (sortField != null && sortOrder != null) {
            Path<?> sortPath = root.get(sortField.toUpperCase());
            if ("desc".equalsIgnoreCase(sortOrder)) {
                query.orderBy(cb.desc(sortPath));
            } else {
                query.orderBy(cb.asc(sortPath));
            }
        }

        query.select(cb.construct(
                Product_Base_Entity.class,
                root.get("ID_BASE_PRODUCT"),
                root.get("NAME_PRODUCT"),
                root.get("QUANTITY"),
                root.get("DATE_RELEASE"),
                root.get("DES_PRODUCT"),
                root.get("VIEW_COUNT"),
                root.get("ID_CATEGORY"),
                root.get("ID_BRAND"),
                brandJoin.get("NAME_BRAND"),
                categoryJoin.get("NAME_CATEGORY")
        ));

        TypedQuery<Product_Base_Entity> typedQuery = entityManager.createQuery(query);
        if (offset != null) typedQuery.setFirstResult(offset);
        if (setOff != null) typedQuery.setMaxResults(setOff);
        return typedQuery.getResultList();
    }

    public void deleteProductBase(Integer ID_BASE_PRODUCT) {
        Product_Base_Entity product = entityManager.find(Product_Base_Entity.class, ID_BASE_PRODUCT);
        if (product != null) {
            try {
                entityManager.remove(product);
                return;
            } catch (Exception e) {
                throw new RuntimeException("Fail to delete base product attribute please delete all row in attribute values first");
            }
        }
        throw new RuntimeException("Cant find ID_BASE_PRODUCT to delete");
    }

    public boolean updateProductBase(Integer ID_BASE_PRODUCT,
                                     String NAME_PRODUCT,
                                     String DES_PRODUCT,
                                     Integer ID_CATEGORY,
                                     Integer VIEW_COUNT,
                                     Integer TOTAL_QUANTITY,
                                     Integer ID_Brand,
                                     LocalDateTime DATE_RELEASE
    ) {
        try {
            Product_Base_Entity product = entityManager.find(Product_Base_Entity.class, ID_BASE_PRODUCT);
            if (product == null) {
                return false;
            }
            if (NAME_PRODUCT != null) product.setNAME_PRODUCT(NAME_PRODUCT);
            if (DES_PRODUCT != null) product.setDES_PRODUCT(DES_PRODUCT);
            if (ID_CATEGORY != null) product.setID_CATEGORY(ID_CATEGORY);
            if (ID_Brand != null) product.setID_BRAND(ID_Brand);
            if (VIEW_COUNT != null) product.setVIEW_COUNT(VIEW_COUNT);
            if (TOTAL_QUANTITY != null) product.setQUANTITY(TOTAL_QUANTITY);
            if (DATE_RELEASE != null) product.setDATE_RELEASE(DATE_RELEASE);
            entityManager.merge(product);
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error occurred while updating product base", e);
        }
    }
}
