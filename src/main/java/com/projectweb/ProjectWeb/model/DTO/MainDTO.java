package com.projectweb.ProjectWeb.model.DTO;

import com.projectweb.ProjectWeb.model.Brand_Entity;
import com.projectweb.ProjectWeb.model.Category_Entity;
import com.projectweb.ProjectWeb.model.Product_Final_Entity;

import java.util.List;

public class MainDTO {
    private List<Brand_Entity> brands;
    private List<Category_Entity> categories;
    private List<Product_Final_Entity> products;
    private List<Product_Final_Entity> productsAll;

    // Constructor
    public MainDTO(List<Brand_Entity> brands, List<Category_Entity> categories, List<Product_Final_Entity> products, List<Product_Final_Entity> productsAll) {
        this.brands = brands;
        this.categories = categories;
        this.products = products;
        this.productsAll = productsAll;
    }

    // Getters and Setters
    public List<Brand_Entity> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand_Entity> brands) {
        this.brands = brands;
    }

    public List<Category_Entity> getCategories() {
        return categories;
    }

    public void setCategories(List<Category_Entity> categories) {
        this.categories = categories;
    }

    public List<Product_Final_Entity> getProducts() {
        return products;
    }

    public void setProducts(List<Product_Final_Entity> products) {
        this.products = products;
    }

    public List<Product_Final_Entity> getProductsAll() { return productsAll; }

    public void setProductsAll(List<Product_Final_Entity> productsAll) {this.productsAll = productsAll; }
}
