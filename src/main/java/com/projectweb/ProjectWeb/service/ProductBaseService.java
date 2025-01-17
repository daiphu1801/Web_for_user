package com.projectweb.ProjectWeb.service;

import com.projectweb.ProjectWeb.dao.*;
import com.projectweb.ProjectWeb.model.Product_Attribute_Entity;
import com.projectweb.ProjectWeb.model.Product_Attribute_Values_Entity;
import com.projectweb.ProjectWeb.model.Product_Base_Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service // Để Spring quản lý ProductBaseService
public class ProductBaseService {

    private final ProductBaseDao productBaseDao;
    private final ProductAttributeDao productAttributeDao;
    private final ProductAttributeValuesDao productAttributeValuesDao;

    @PersistenceContext // Spring quản lý EntityManager
    private EntityManager entityManager;

    // Constructor injection
    public ProductBaseService(ProductBaseDao productBaseDao,
                              ProductAttributeDao productAttributeDao,
                              ProductAttributeValuesDao productAttributeValuesDao) {
        this.productBaseDao = productBaseDao;
        this.productAttributeDao = productAttributeDao;
        this.productAttributeValuesDao = productAttributeValuesDao;
    }

    public List<Product_Base_Entity> getProductBaseByCombinedCondition(
            Product_Base_Entity product_BASE_entity,
            String sortField,
            String sortOrder,
            String typeDate,
            String typeQuantity,
            String typeView,
            Integer setOff,
            Integer offset
    ) {
        Integer idBaseProduct = product_BASE_entity.getID_BASE_PRODUCT();
        String NAME_PRODUCT = product_BASE_entity.getNAME_PRODUCT();
        Integer TOTAL_QUANTITY = product_BASE_entity.getTOTAL_QUANTITY();
        LocalDateTime DATE_RELEASE = product_BASE_entity.getDATE_RELEASE();
        Integer VIEW_COUNT = product_BASE_entity.getVIEW_COUNT();
        Integer ID_CATEGORY = product_BASE_entity.getID_CATEGORY();
        Integer ID_BRAND = product_BASE_entity.getID_BRAND();
        String NAME_BRAND = product_BASE_entity.getNAME_BRAND();
        String NAME_CATEGORY = product_BASE_entity.getNAME_CATEGORY();

        return productBaseDao.getFilteredProductBase(
                idBaseProduct, NAME_PRODUCT, ID_CATEGORY, NAME_CATEGORY, ID_BRAND, NAME_BRAND,
                TOTAL_QUANTITY, typeQuantity, DATE_RELEASE, typeDate, VIEW_COUNT, typeView,
                sortField, sortOrder, offset, setOff
        );
    }

    public Integer getCountProductBase(
            Product_Base_Entity product_BASE_entity,
            String typeDate,
            String typeQuantity,
            String typeView
    ) {
        Integer idBaseProduct = product_BASE_entity.getID_BASE_PRODUCT();
        String NAME_PRODUCT = product_BASE_entity.getNAME_PRODUCT();
        Integer TOTAL_QUANTITY = product_BASE_entity.getTOTAL_QUANTITY();
        LocalDateTime DATE_RELEASE = product_BASE_entity.getDATE_RELEASE();
        Integer VIEW_COUNT = product_BASE_entity.getVIEW_COUNT();
        Integer ID_CATEGORY = product_BASE_entity.getID_CATEGORY();
        Integer ID_BRAND = product_BASE_entity.getID_BRAND();
        String NAME_BRAND = product_BASE_entity.getNAME_BRAND();
        String NAME_CATEGORY = product_BASE_entity.getNAME_CATEGORY();

        return productBaseDao.getFilteredProductBaseCount(
                idBaseProduct, NAME_PRODUCT, ID_CATEGORY, NAME_CATEGORY, ID_BRAND, NAME_BRAND,
                TOTAL_QUANTITY, typeQuantity, DATE_RELEASE, typeDate, VIEW_COUNT, typeView
        );
    }

    public Product_Base_Entity getProductBaseByID(Integer id) {
        return productBaseDao.getProductBaseById(id);
    }

    @Transactional // Đảm bảo tất cả thay đổi sẽ được quản lý trong một transaction
    public void deleteProductBase(Integer id) {
        productBaseDao.deleteProductBase(id);
    }

    @Transactional
    public void updateProductBase(Product_Base_Entity product_BASE_entity) {
        Integer idBaseProduct = product_BASE_entity.getID_BASE_PRODUCT();
        String NAME_PRODUCT = product_BASE_entity.getNAME_PRODUCT();
        Integer TOTAL_QUANTITY = product_BASE_entity.getTOTAL_QUANTITY();
        LocalDateTime DATE_RELEASE = product_BASE_entity.getDATE_RELEASE();
        String DES_PRODUCT = product_BASE_entity.getDES_PRODUCT();
        Integer VIEW_COUNT = product_BASE_entity.getVIEW_COUNT();
        Integer ID_CATEGORY = product_BASE_entity.getID_CATEGORY();
        Integer ID_BRAND = product_BASE_entity.getID_BRAND();

        productBaseDao.updateProductBase(idBaseProduct, NAME_PRODUCT, DES_PRODUCT,
                ID_CATEGORY, VIEW_COUNT, TOTAL_QUANTITY, ID_BRAND, DATE_RELEASE);
    }

    @Transactional
    public void createProductBase(Product_Base_Entity product_BASE_entity) {
        product_BASE_entity.setTOTAL_QUANTITY(0);
        productBaseDao.createProductBase(product_BASE_entity);
    }
}
