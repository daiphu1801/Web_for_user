package com.projectweb.ProjectWeb.service;

import com.projectweb.ProjectWeb.dao.ProductOptionDao;
import com.projectweb.ProjectWeb.dao.ProductOptionValuesDao;
import com.projectweb.ProjectWeb.dao.ProductFinalDao;
import com.projectweb.ProjectWeb.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // Để Spring quản lý class này
public class ProductFinalService {

    private final ProductFinalDao productFinalDao;
    private final ProductOptionDao productOptionDao;
    private final ProductOptionValuesDao productOptionValuesDao;

    // Sử dụng @Autowired để Spring Boot tự động inject các dependency
    @Autowired
    public ProductFinalService(ProductFinalDao productFinalDao, ProductOptionDao productOptionDao, ProductOptionValuesDao productOptionValuesDao) {
        this.productFinalDao = productFinalDao;
        this.productOptionDao = productOptionDao;
        this.productOptionValuesDao = productOptionValuesDao;
    }

    public List<Product_Final_Entity> getProductsWithHighestDiscount() {
        return productFinalDao.findProductsWithHighestDiscount();
    }


    public List<Product_Final_Entity> getAllProductsFinal() {
        return productFinalDao.getAllProductsFinal();
    }


    public List<Product_Final_Entity> getProductFinalByCombinedCondition(
            Product_Final_Entity product_Final_Entity,
            String typePrice,
            String typeDiscount,
            String typeQuantity,
            String sortField,
            String sortOrder,
            Integer setOff,
            Integer offset) {

        Integer ID_SP = product_Final_Entity.getID_SP();
        String NAME_PRODUCT = product_Final_Entity.getNAME_PRODUCT();
        Double PRICE_SP = product_Final_Entity.getPRICE_SP();
        Integer QUANTITY = product_Final_Entity.getQUANTITY();
        Double DISCOUNT = product_Final_Entity.getDISCOUNT();
        Integer ID_BASE_PRODUCT = product_Final_Entity.getID_BASE_PRODUCT();
        String NAME_PRODUCT_BASE = product_Final_Entity.getNAME_PRODUCT_BASE();

        return productFinalDao.getFilteredProductFinal(
                ID_SP, ID_BASE_PRODUCT, NAME_PRODUCT_BASE, NAME_PRODUCT, PRICE_SP, typePrice, QUANTITY, DISCOUNT, typeDiscount, typeQuantity, sortField, sortOrder, offset, setOff
        );
    }

    public Integer getCountProductFinalByCombinedCondition(
            Product_Final_Entity product_Final_Entity,
            String typePrice,
            String typeDiscount,
            String typeQuantity) {

        Integer ID_SP = product_Final_Entity.getID_SP();
        String NAME_PRODUCT = product_Final_Entity.getNAME_PRODUCT();
        Double PRICE_SP = product_Final_Entity.getPRICE_SP();
        Integer QUANTITY = product_Final_Entity.getQUANTITY();
        Double DISCOUNT = product_Final_Entity.getDISCOUNT();
        Integer ID_BASE_PRODUCT = product_Final_Entity.getID_BASE_PRODUCT();
        String NAME_PRODUCT_BASE = product_Final_Entity.getNAME_PRODUCT_BASE();

        return productFinalDao.getFilteredProductFinalCount(
                ID_SP, ID_BASE_PRODUCT, NAME_PRODUCT_BASE, NAME_PRODUCT, PRICE_SP, typePrice, QUANTITY, DISCOUNT, typeDiscount, typeQuantity);
    }

    public Product_Final_Entity getProductByID(Integer id) {
        return productFinalDao.getProductFinalById(id);
    }

    @Transactional // Đảm bảo tất cả thay đổi được thực hiện trong một transaction
    public void deleteProductFinal(Integer id) {
        productFinalDao.deleteProductFinal(id);
    }

    @Transactional
    public void updateProductFinal(Product_Final_Entity product_Final_Entity) {
        Integer ID_SP = product_Final_Entity.getID_SP();
        String NAME_PRODUCT = product_Final_Entity.getNAME_PRODUCT();
        String DES_PRODUCT = product_Final_Entity.getDES_PRODUCT();
        Double PRICE_SP = product_Final_Entity.getPRICE_SP();
        String IMAGE_SP = product_Final_Entity.getIMAGE_SP();
        Integer QUANTITY = product_Final_Entity.getQUANTITY();
        Double DISCOUNT = product_Final_Entity.getDISCOUNT();
        Integer ID_BASE_PRODUCT = product_Final_Entity.getID_BASE_PRODUCT();

        productFinalDao.updateProductFinal(ID_SP, ID_BASE_PRODUCT, NAME_PRODUCT, DES_PRODUCT, QUANTITY, DISCOUNT, IMAGE_SP, PRICE_SP);
    }

    @Transactional
    public void createProductFinal(Product_Final_Entity product_Final_Entity) {
        product_Final_Entity.setQUANTITY(0);
        productFinalDao.createProductFinal(product_Final_Entity);
    }

    public List<Product_Option_Entity> getProductOptionCombinedCondition(
            Product_Option_Entity productOptionEntity,
            String sortField,
            String sortOrder,
            Integer setOff,
            Integer offset) {

        Integer ID = productOptionEntity.getID_OPTION();
        String NAME = productOptionEntity.getNAME_OPTION();

        return productOptionDao.getFilteredProductOption(ID, NAME, sortField, sortOrder, setOff, offset);
    }

    public Integer getCountProductOptionCombinedCondition(Product_Option_Entity productOptionEntity) {
        Integer ID = productOptionEntity.getID_OPTION();
        String NAME = productOptionEntity.getNAME_OPTION();

        return productOptionDao.getFilteredProductOptionCount(ID, NAME);
    }

    @Transactional
    public void deleteProductOption(Integer id) {
        productOptionDao.deleteProductOptionById(id);
    }

    @Transactional
    public void updateProductOption(Product_Option_Entity productOption) {
        Integer idOption = productOption.getID_OPTION();
        String nameOption = productOption.getNAME_OPTION();

        productOptionDao.updateProductOption(idOption, nameOption);
    }

    @Transactional
    public void createProductOption(Product_Option_Entity productOption) {
        productOptionDao.createProductOption(productOption);
    }

    public List<Product_Option_Values_Entity> getProductOptionValuesCombinedCondition(
            Product_Option_Values_Entity productOptionValues,
            String sortField,
            String sortOrder,
            Integer setOff,
            Integer offset) {

        Integer ID = productOptionValues.getID();
        Integer ID_OPTION = productOptionValues.getID_OPTION();
        String VALUE = productOptionValues.getVALUE();
        Integer ID_FINAL_PRODUCT = productOptionValues.getID_FINAL_PRODUCT();
        String NAME_OPTION = productOptionValues.getName_Option();
        String NAME_PRODUCT = productOptionValues.getNAME_FINAL_PRODUCT();

        return productOptionValuesDao.getFilteredProductOptionValue(ID, ID_OPTION, NAME_OPTION, VALUE, ID_FINAL_PRODUCT, NAME_PRODUCT, sortField, sortOrder, setOff, offset);
    }

    public Integer getCountProductOptionValuesCombinedCondition(Product_Option_Values_Entity productOptionValues) {
        Integer ID = productOptionValues.getID();
        Integer ID_OPTION = productOptionValues.getID_OPTION();
        String VALUE = productOptionValues.getVALUE();
        Integer ID_FINAL_PRODUCT = productOptionValues.getID_FINAL_PRODUCT();
        String NAME_OPTION = productOptionValues.getName_Option();
        String NAME_PRODUCT = productOptionValues.getNAME_FINAL_PRODUCT();

        return productOptionValuesDao.getFilteredProductOptionValueCount(ID, ID_OPTION, NAME_OPTION, VALUE, ID_FINAL_PRODUCT, NAME_PRODUCT);
    }

    @Transactional
    public void deleteProductOptionValues(Integer id, Integer idOption, Integer idFinalProduct) {
        productOptionValuesDao.deleteOptionValues(id, idOption, idFinalProduct);
    }

    @Transactional
    public void updateProductOptionValues(Product_Option_Values_Entity productOptionValues) {
        Integer ID = productOptionValues.getID();
        Integer ID_OPTION = productOptionValues.getID_OPTION();
        String VALUE = productOptionValues.getVALUE();
        Integer ID_FINAL_PRODUCT = productOptionValues.getID_FINAL_PRODUCT();

        productOptionValuesDao.updateProductOptionValues(ID, VALUE, ID_OPTION, ID_FINAL_PRODUCT);
    }

    @Transactional
    public void createProductOptionValues(Product_Option_Values_Entity productOptionValues) {
        productOptionValuesDao.createProductOptionValues(productOptionValues);
    }


}
