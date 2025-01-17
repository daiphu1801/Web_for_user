package com.projectweb.ProjectWeb.controller;

import com.projectweb.ProjectWeb.model.Brand_Entity;
import com.projectweb.ProjectWeb.model.Category_Entity;
import com.projectweb.ProjectWeb.model.Product_Final_Entity;
import com.projectweb.ProjectWeb.model.DTO.MainDTO;
import com.projectweb.ProjectWeb.service.BrandService;
import com.projectweb.ProjectWeb.service.CategoryService;
import com.projectweb.ProjectWeb.service.ProductFinalService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/index") // Đường dẫn API chính
public class MainController {

    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ProductFinalService productFinalService;

    public MainController(BrandService brandService, CategoryService categoryService, ProductFinalService productFinalService) {
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.productFinalService = productFinalService;
    }

    // Endpoint trả về dữ liệu tổng hợp từ tất cả các Service
    @GetMapping("/homepage")
    public MainDTO getAllData() {
        List<Brand_Entity> brands = brandService.getAllBrands();
        List<Product_Final_Entity> products = productFinalService.getProductsWithHighestDiscount();
        List<Category_Entity> categories = categoryService.getAllCategories();
        List<Product_Final_Entity> productsAll = productFinalService.getAllProductsFinal();

        return new MainDTO(brands, categories, products, productsAll);
    }
}
