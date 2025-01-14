package com.projectweb.ProjectWeb.dao;

import org.example.model.Bill_Detail_Entity;
import org.example.model.Product_Final_Entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import java.util.List;

public class BillDetailDao {
    private final EntityManager entityManager;

    public BillDetailDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    // Tạo chi tiết hóa đơn
    public void createBillDetail(List<Bill_Detail_Entity> listBillDetail) {
        int batchSize = 10;
        for (int i = 0; i < listBillDetail.size(); i++) {
            Bill_Detail_Entity billDetail = listBillDetail.get(i);
            entityManager.persist(billDetail);
            if (i % batchSize == 0 && i > 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    // Lấy danh sách chi tiết hóa đơn với bộ lọc
    public List<Bill_Detail_Entity> getFilteredBillDetails(
            Integer idBillDetail,
            Double totalDetailPrice,
            Double unitPrice,
            Integer idFinalProduct,
            String nameFinalProduct,
            Integer quantitySp,
            String typeQuantity,
            String idBill,
            Boolean available,
            String sortField,
            String sortOrder,
            Integer offset,
            Integer setOff) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bill_Detail_Entity> query = cb.createQuery(Bill_Detail_Entity.class);
        Root<Bill_Detail_Entity> root = query.from(Bill_Detail_Entity.class);

        Join<Bill_Detail_Entity, Product_Final_Entity> productJoin = root.join("productFinalEntity", JoinType.LEFT);

        Predicate conditions = cb.conjunction();

        if (idBillDetail != null) {
            conditions = cb.and(conditions, cb.equal(root.get("idBillDetail"), idBillDetail));
        }
        if (totalDetailPrice != null) {
            conditions = cb.and(conditions, cb.greaterThanOrEqualTo(root.get("totalDetailPrice"), totalDetailPrice));
        }
        if (unitPrice != null) {
            conditions = cb.and(conditions, cb.equal(root.get("unitPrice"), unitPrice));
        }
        if (typeQuantity != null && quantitySp != null) {
            conditions = switch (typeQuantity) {
                case "<" -> cb.and(conditions, cb.lessThan(root.get("quantitySp"), quantitySp));
                case ">=" -> cb.and(conditions, cb.greaterThanOrEqualTo(root.get("quantitySp"), quantitySp));
                case "<=" -> cb.and(conditions, cb.lessThanOrEqualTo(root.get("quantitySp"), quantitySp));
                case ">" -> cb.and(conditions, cb.greaterThan(root.get("quantitySp"), quantitySp));
                case "=" -> cb.and(conditions, cb.equal(root.get("quantitySp"), quantitySp));
                default -> conditions;
            };
        }
        if (idBill != null) {
            conditions = cb.and(conditions, cb.equal(root.get("idBill"), idBill));
        }
        if (idFinalProduct != null) {
            conditions = cb.and(conditions, cb.equal(root.get("idFinalProduct"), idFinalProduct));
        }
        if (nameFinalProduct != null) {
            conditions = cb.and(conditions, cb.like(productJoin.get("nameFinalProduct"), "%" + nameFinalProduct + "%"));
        }
        if (available != null) {
            conditions = cb.and(conditions, cb.equal(root.get("available"), available));
        }

        query.where(conditions);

        if (sortField != null && sortOrder != null) {
            Path<?> sortPath = root.get(sortField);
            query.orderBy("desc".equalsIgnoreCase(sortOrder) ? cb.desc(sortPath) : cb.asc(sortPath));
        }

        TypedQuery<Bill_Detail_Entity> typedQuery = entityManager.createQuery(query);
        if (offset != null) typedQuery.setFirstResult(offset);
        if (setOff != null) typedQuery.setMaxResults(setOff);

        return typedQuery.getResultList();
    }

    // Hủy chi tiết hóa đơn theo ID hóa đơn
    public void cancelBillDetail(String idBill) {
        List<Bill_Detail_Entity> billDetails = entityManager.createQuery(
                        "SELECT bd FROM Bill_Detail_Entity bd WHERE bd.idBill = :idBill", Bill_Detail_Entity.class)
                .setParameter("idBill", idBill)
                .getResultList();

        if (billDetails.isEmpty()) {
            throw new RuntimeException("No Bill Details found for the given ID_BILL");
        }

        for (Bill_Detail_Entity billDetail : billDetails) {
            billDetail.setAvailable(false);
            entityManager.merge(billDetail);
        }
    }

    // Cập nhật chi tiết hóa đơn
    public void updateBillDetail(Integer idBillDetail, Integer quantitySp, Double totalDetailPrice, Integer idFinalProduct) {
        Bill_Detail_Entity billDetail = entityManager.find(Bill_Detail_Entity.class, idBillDetail);
        if (billDetail == null) {
            throw new RuntimeException("Bill Detail not found");
        }
        if (quantitySp != null) billDetail.setQuantitySp(quantitySp);
        if (totalDetailPrice != null) billDetail.setTotalDetailPrice(totalDetailPrice);
        if (idFinalProduct != null) billDetail.setIdFinalProduct(idFinalProduct);
        entityManager.merge(billDetail);
    }

    // Xóa chi tiết hóa đơn
    public void deleteBillDetail(Integer idBillDetail) {
        Bill_Detail_Entity billDetailToDelete = entityManager.find(Bill_Detail_Entity.class, idBillDetail);
        if (billDetailToDelete != null) {
            entityManager.remove(billDetailToDelete);
        } else {
            throw new RuntimeException("No Bill Detail found to delete");
        }
    }
}
