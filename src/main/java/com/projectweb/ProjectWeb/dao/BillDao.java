package com.projectweb.ProjectWeb.dao;

import com.projectweb.ProjectWeb.model.Bill_Entity;
import com.projectweb.ProjectWeb.model.User_Entity;
import com.projectweb.ProjectWeb.model.EnumType.Status_Bill;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

public class BillDao {
    private final EntityManager entityManager;

    public BillDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    // Tạo hóa đơn
    public void createBill(Bill_Entity billE) {
        try {
            entityManager.persist(billE);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create bill: " + e.getMessage(), e);
        }
    }

    // Lấy hóa đơn theo ID
    public Bill_Entity getBillByID(String idBill) {
        return entityManager.find(Bill_Entity.class, idBill);
    }

    // Cập nhật tổng giá tiền cho hóa đơn
    public void addSumPrice(String idBill, Double totalPrice) {
        Bill_Entity billToUpdate = entityManager.find(Bill_Entity.class, idBill);
        if (billToUpdate != null) {
            billToUpdate.setTOTAL_PRICE(totalPrice);
            entityManager.merge(billToUpdate);
        } else {
            throw new RuntimeException("Bill not found with ID: " + idBill);
        }
    }

    // Lấy danh sách hóa đơn với bộ lọc và phân trang
    public List<Bill_Entity> getFilteredBills(
            LocalDateTime dateExport,
            String typeDate,
            String idBill,
            String phone,
            String idUser,
            String EMAIL_ACC,
            Status_Bill status,
            String addBill,
            Double totalPrice,
            String typePrice,
            String sortField,
            String sortOrder,
            Integer offset,
            Integer setOff
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bill_Entity> query = cb.createQuery(Bill_Entity.class);
        Root<Bill_Entity> root = query.from(Bill_Entity.class);
        Join<Bill_Entity, User_Entity> userJoin = root.join("userEntity", JoinType.LEFT);

        Predicate conditions = cb.conjunction();

        // Lọc theo email
        if (EMAIL_ACC != null) {
            conditions = cb.and(conditions, cb.like(userJoin.get("email"), "%" + EMAIL_ACC + "%"));
        }

        // Lọc theo ngày xuất hóa đơn
        if (dateExport != null) {
            conditions = switch (typeDate) {
                case "<" -> cb.and(conditions, cb.lessThan(root.get("Date_EXP"), dateExport));
                case ">=" -> cb.and(conditions, cb.greaterThanOrEqualTo(root.get("Date_EXP"), dateExport));
                case "<=" -> cb.and(conditions, cb.lessThanOrEqualTo(root.get("Date_EXP"), dateExport));
                case ">" -> cb.and(conditions, cb.greaterThan(root.get("Date_EXP"), dateExport));
                case "=" -> cb.and(conditions, cb.equal(root.get("Date_EXP"), dateExport));
                default -> conditions;
            };
        }

        // Lọc các trường khác
        if (idBill != null) conditions = cb.and(conditions, cb.equal(root.get("ID_BILL"), idBill));
        if (idUser != null) conditions = cb.and(conditions, cb.equal(root.get("ID_USER"), idUser));
        if (status != null) conditions = cb.and(conditions, cb.equal(root.get("BILL_STATUS"), status));
        if (phone != null) conditions = cb.and(conditions, cb.equal(root.get("PHONE_BILL"), phone));
        if (addBill != null) conditions = cb.and(conditions, cb.equal(root.get("ADD_BILL"), addBill));
        if (typePrice != null && totalPrice != null) {
            conditions = switch (typePrice) {
                case "<" -> cb.and(conditions, cb.lessThan(root.get("TOTAL_PRICE"), totalPrice));
                case ">=" -> cb.and(conditions, cb.greaterThanOrEqualTo(root.get("TOTAL_PRICE"), totalPrice));
                case "<=" -> cb.and(conditions, cb.lessThanOrEqualTo(root.get("TOTAL_PRICE"), totalPrice));
                case ">" -> cb.and(conditions, cb.greaterThan(root.get("TOTAL_PRICE"), totalPrice));
                case "=" -> cb.and(conditions, cb.equal(root.get("TOTAL_PRICE"), totalPrice));
                default -> conditions;
            };
        }

        query.where(conditions);

        // Sắp xếp kết quả
        if (sortField != null && sortOrder != null) {
            Path<?> sortPath = root.get(sortField);
            query.orderBy("desc".equalsIgnoreCase(sortOrder) ? cb.desc(sortPath) : cb.asc(sortPath));
        }

        TypedQuery<Bill_Entity> typedQuery = entityManager.createQuery(query);

        // Phân trang
        if (offset != null) typedQuery.setFirstResult(offset);
        if (setOff != null) typedQuery.setMaxResults(setOff);

        return typedQuery.getResultList();
    }

    // Xóa hóa đơn
    public void deleteBill(String idBill) {
        Bill_Entity billToDelete = entityManager.find(Bill_Entity.class, idBill);
        if (billToDelete != null) {
            if (billToDelete.getBILL_STATUS() == Status_Bill.PEN) {
                billToDelete.setBILL_STATUS(Status_Bill.CANC);
                entityManager.merge(billToDelete);
            } else {
                throw new RuntimeException("Cannot cancel bill in current state: " + billToDelete.getBILL_STATUS());
            }
        } else {
            throw new RuntimeException("Bill does not exist");
        }
    }

    // Thay đổi trạng thái hóa đơn
    public void changeBillStatus(String idBill, Status_Bill status) {
        Bill_Entity billToUpdate = entityManager.find(Bill_Entity.class, idBill);
        if (billToUpdate != null) {
            billToUpdate.setBILL_STATUS(status);
            entityManager.merge(billToUpdate);
        } else {
            throw new RuntimeException("Bill does not exist");
        }
    }
}
