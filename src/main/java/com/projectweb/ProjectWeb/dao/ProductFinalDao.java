package com.projectweb.ProjectWeb.dao;

import com.projectweb.ProjectWeb.model.Product_Base_Entity;
import com.projectweb.ProjectWeb.model.Product_Final_Entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository // Để Spring quản lý lớp này như một Repository
public class ProductFinalDao {

    @PersistenceContext // Inject EntityManager do Spring quản lý
    private EntityManager entityManager;

    @Transactional // Đảm bảo mọi thay đổi được thực hiện trong transaction
    public void createProductFinal(Product_Final_Entity product) {
        entityManager.persist(product);
    }

    public List<Product_Final_Entity> findProductsWithHighestDiscount() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Final_Entity> query = cb.createQuery(Product_Final_Entity.class);
        Root<Product_Final_Entity> root = query.from(Product_Final_Entity.class);

        // Subquery để tìm mức giảm giá cao nhất
        Subquery<Double> maxDiscountSubquery = query.subquery(Double.class);
        Root<Product_Final_Entity> subRoot = maxDiscountSubquery.from(Product_Final_Entity.class);
        maxDiscountSubquery.select(cb.max(subRoot.get("DISCOUNT")));

        // Lọc những sản phẩm có mức giảm giá bằng mức giảm giá cao nhất
        query.select(root).where(cb.equal(root.get("DISCOUNT"), maxDiscountSubquery));

        return entityManager.createQuery(query).getResultList();
    }

    public List<Product_Final_Entity> getAllProductsFinal() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Final_Entity> query = cb.createQuery(Product_Final_Entity.class);
        Root<Product_Final_Entity> root = query.from(Product_Final_Entity.class);
        query.select(root); // Lấy tất cả các cột
        return entityManager.createQuery(query).getResultList();
    }

    public Product_Final_Entity getProductFinalById(Integer ID_FINAL_PRODUCT) {
        try {
            String jpql = "SELECT u FROM Product_Final_Entity u WHERE u.ID_SP = :ID_FINAL_PRODUCT";
            TypedQuery<Product_Final_Entity> query = entityManager.createQuery(jpql, Product_Final_Entity.class);
            query.setParameter("ID_FINAL_PRODUCT", ID_FINAL_PRODUCT);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Trả về null nếu không tìm thấy bản ghi
        }
    }

    public List<Product_Final_Entity> getFilteredProductFinal(
            Integer ID_SP,
            Integer ID_BASE_PRODUCT,
            String NAME_PRODUCT_BASE,
            String NAME_PRODUCT,
            Double PRICE_SP,
            String typePrice,
            Integer QUANTITY,
            Double DISCOUNT,
            String typeDiscount,
            String typeQuantity,
            String sortField,
            String sortOrder,
            Integer offset,
            Integer setOff) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product_Final_Entity> query = cb.createQuery(Product_Final_Entity.class);
        Root<Product_Final_Entity> root = query.from(Product_Final_Entity.class);

        Join<Product_Final_Entity, Product_Base_Entity> baseProductJoin = root.join("product_base", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        if (PRICE_SP != null && typePrice != null) {
            switch (typePrice) {
                case "<" -> predicates.add(cb.lessThan(root.get("PRICE_SP"), PRICE_SP));
                case ">" -> predicates.add(cb.greaterThan(root.get("PRICE_SP"), PRICE_SP));
                case "=" -> predicates.add(cb.equal(root.get("PRICE_SP"), PRICE_SP));
                case "<=" -> predicates.add(cb.lessThanOrEqualTo(root.get("PRICE_SP"), PRICE_SP));
                case ">=" -> predicates.add(cb.greaterThanOrEqualTo(root.get("PRICE_SP"), PRICE_SP));
            }
        }
        if (DISCOUNT != null && typeDiscount != null) {
            switch (typeDiscount) {
                case "<" -> predicates.add(cb.lessThan(root.get("DISCOUNT"), DISCOUNT));
                case ">" -> predicates.add(cb.greaterThan(root.get("DISCOUNT"), DISCOUNT));
                case "=" -> predicates.add(cb.equal(root.get("DISCOUNT"), DISCOUNT));
                case "<=" -> predicates.add(cb.lessThanOrEqualTo(root.get("DISCOUNT"), DISCOUNT));
                case ">=" -> predicates.add(cb.greaterThanOrEqualTo(root.get("DISCOUNT"), DISCOUNT));
            }
        }
        if (ID_SP != null) predicates.add(cb.equal(root.get("ID_SP"), ID_SP));
        if (NAME_PRODUCT != null) predicates.add(cb.like(root.get("NAME_PRODUCT"), "%" + NAME_PRODUCT + "%"));
        if (ID_BASE_PRODUCT != null) predicates.add(cb.equal(root.get("ID_BASE_PRODUCT"), ID_BASE_PRODUCT));
        if (NAME_PRODUCT_BASE != null) predicates.add(cb.like(baseProductJoin.get("NAME_PRODUCT"), "%" + NAME_PRODUCT_BASE + "%"));
        if (QUANTITY != null && typeQuantity != null) {
            switch (typeQuantity) {
                case "<" -> predicates.add(cb.lessThan(root.get("QUANTITY"), QUANTITY));
                case ">" -> predicates.add(cb.greaterThan(root.get("QUANTITY"), QUANTITY));
                case "=" -> predicates.add(cb.equal(root.get("QUANTITY"), QUANTITY));
                case "<=" -> predicates.add(cb.lessThanOrEqualTo(root.get("QUANTITY"), QUANTITY));
                case ">=" -> predicates.add(cb.greaterThanOrEqualTo(root.get("QUANTITY"), QUANTITY));
            }
        }

        query.where(predicates.toArray(new Predicate[0]));

        if (sortField != null && sortOrder != null) {
            Path<?> sortPath = root.get(sortField);
            if ("desc".equalsIgnoreCase(sortOrder)) {
                query.orderBy(cb.desc(sortPath));
            } else {
                query.orderBy(cb.asc(sortPath));
            }
        }

        TypedQuery<Product_Final_Entity> typedQuery = entityManager.createQuery(query);
        if (offset != null) typedQuery.setFirstResult(offset);
        if (setOff != null) typedQuery.setMaxResults(setOff);

        return typedQuery.getResultList();
    }

    public Integer getFilteredProductFinalCount(
            Integer ID_SP,
            Integer ID_BASE_PRODUCT,
            String NAME_PRODUCT_BASE,
            String NAME_PRODUCT,
            Double PRICE_SP,
            String typePrice,
            Integer QUANTITY,
            Double DISCOUNT,
            String typeDiscount,
            String typeQuantity) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Product_Final_Entity> root = query.from(Product_Final_Entity.class);

        Join<Product_Final_Entity, Product_Base_Entity> baseProductJoin = root.join("product_base", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        if (PRICE_SP != null && typePrice != null) {
            switch (typePrice) {
                case "<" -> predicates.add(cb.lessThan(root.get("PRICE_SP"), PRICE_SP));
                case ">" -> predicates.add(cb.greaterThan(root.get("PRICE_SP"), PRICE_SP));
                case "=" -> predicates.add(cb.equal(root.get("PRICE_SP"), PRICE_SP));
                case "<=" -> predicates.add(cb.lessThanOrEqualTo(root.get("PRICE_SP"), PRICE_SP));
                case ">=" -> predicates.add(cb.greaterThanOrEqualTo(root.get("PRICE_SP"), PRICE_SP));
            }
        }
        if (DISCOUNT != null && typeDiscount != null) {
            switch (typeDiscount) {
                case "<" -> predicates.add(cb.lessThan(root.get("DISCOUNT"), DISCOUNT));
                case ">" -> predicates.add(cb.greaterThan(root.get("DISCOUNT"), DISCOUNT));
                case "=" -> predicates.add(cb.equal(root.get("DISCOUNT"), DISCOUNT));
                case "<=" -> predicates.add(cb.lessThanOrEqualTo(root.get("DISCOUNT"), DISCOUNT));
                case ">=" -> predicates.add(cb.greaterThanOrEqualTo(root.get("DISCOUNT"), DISCOUNT));
            }
        }
        if (ID_SP != null) predicates.add(cb.equal(root.get("ID_SP"), ID_SP));
        if (NAME_PRODUCT != null) predicates.add(cb.like(root.get("NAME_PRODUCT"), "%" + NAME_PRODUCT + "%"));
        if (ID_BASE_PRODUCT != null) predicates.add(cb.equal(root.get("ID_BASE_PRODUCT"), ID_BASE_PRODUCT));
        if (NAME_PRODUCT_BASE != null) predicates.add(cb.like(baseProductJoin.get("NAME_PRODUCT"), "%" + NAME_PRODUCT_BASE + "%"));
        if (QUANTITY != null && typeQuantity != null) {
            switch (typeQuantity) {
                case "<" -> predicates.add(cb.lessThan(root.get("QUANTITY"), QUANTITY));
                case ">" -> predicates.add(cb.greaterThan(root.get("QUANTITY"), QUANTITY));
                case "=" -> predicates.add(cb.equal(root.get("QUANTITY"), QUANTITY));
                case "<=" -> predicates.add(cb.lessThanOrEqualTo(root.get("QUANTITY"), QUANTITY));
                case ">=" -> predicates.add(cb.greaterThanOrEqualTo(root.get("QUANTITY"), QUANTITY));
            }
        }

        query.where(predicates.toArray(new Predicate[0]));
        query.select(cb.count(root));

        Long count = entityManager.createQuery(query).getSingleResult();
        return count != null ? count.intValue() : 0;
    }

    @Transactional
    public void deleteProductFinal(Integer ID_SP) {
        Product_Final_Entity product = entityManager.find(Product_Final_Entity.class, ID_SP);
        if (product != null) {
            entityManager.remove(product);
        } else {
            throw new RuntimeException("Cannot find Product Final with ID_SP: " + ID_SP);
        }
    }

    @Transactional
    public void updateProductFinal(
            Integer ID_SP,
            Integer ID_BASE_PRODUCT,
            String NAME_PRODUCT,
            String DES_PRODUCT,
            Integer QUANTITY,
            Double DISCOUNT,
            String IMAGE_SP,
            Double Price) {

        Product_Final_Entity product = entityManager.find(Product_Final_Entity.class, ID_SP);
        if (product == null) {
            throw new RuntimeException("Cannot find Product Final with ID_SP: " + ID_SP);
        }

        if (NAME_PRODUCT != null) product.setNAME_PRODUCT(NAME_PRODUCT);
        if (DES_PRODUCT != null) product.setDES_PRODUCT(DES_PRODUCT);
        if (QUANTITY != null) product.setQUANTITY(QUANTITY);
        if (DISCOUNT != null) product.setDISCOUNT(DISCOUNT);
        if (IMAGE_SP != null) product.setIMAGE_SP(IMAGE_SP);
        if (Price != null) product.setPRICE_SP(Price);
        if (ID_BASE_PRODUCT != null) product.setID_BASE_PRODUCT(ID_BASE_PRODUCT);

        entityManager.merge(product);
    }
}
