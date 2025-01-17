package com.projectweb.ProjectWeb.service;

import com.projectweb.ProjectWeb.dao.BrandDao;
import com.projectweb.ProjectWeb.model.Brand_Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    private final BrandDao brandDao;

    @Autowired
    public BrandService(BrandDao brandDao) {
        this.brandDao = brandDao;
    }

    public void createNewBrand(Brand_Entity brand) {
        // Add input validation if necessary
        brandDao.createBrand(brand);
    }

    public List<Brand_Entity> getAllBrands() {
        return brandDao.getAllBrands();
    }


    public List<Brand_Entity> getFilteredBrand(
            Brand_Entity brand,
            String sortField,
            String sortOrder
    ) {
        Integer id = brand.getID_BRAND();
        String name = brand.getNAME_BRAND();

        return brandDao.getFilteredBrand(id, name, sortField, sortOrder);
    }

    public Integer getCountFilteredBrand(Brand_Entity brand) {
        Integer id = brand.getID_BRAND();
        String name = brand.getNAME_BRAND();
        return brandDao.getFilteredBrandCount(id, name);
    }

    public List<Brand_Entity> getBrandBy(
            Brand_Entity brand,
            String sortField,
            String sortOrder
    ) {
        Integer id = brand.getID_BRAND();
        String name = brand.getNAME_BRAND();
        return brandDao.getFilteredBrand(id, name, sortField, sortOrder);
    }

    public void deleteBrand(Integer id) {
        brandDao.deleteBrandById(id);
    }

    public void updateBrand(Brand_Entity brand) {
        Integer id = brand.getID_BRAND();
        String name = brand.getNAME_BRAND();
        String detail = brand.getDETAIL();
        brandDao.updateBrand(id, name, detail);
    }
}
