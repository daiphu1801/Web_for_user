package com.projectweb.ProjectWeb.service;

import com.projectweb.ProjectWeb.dao.CategoryDao;
import com.projectweb.ProjectWeb.model.Category_Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    //Lấy tất cả dữ liệu từ category
    public List<Category_Entity> getAllCategories() {
        return categoryDao.getAllCategories();
    }


    // Phương thức thêm mới Category
    @Transactional
    public void createNewCategory(Category_Entity category) {
        if (category.getNAME_CATEGORY() == null || category.getNAME_CATEGORY().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }
        if (category.getNAME_CATEGORY().length() > 50) {
            throw new IllegalArgumentException("Category name is too long (max 50 characters).");
        }
        categoryDao.createCategory(category);
    }


    // Phương thức lấy danh sách Category theo bộ lọc
    public List<Category_Entity> getFilteredCategories(
            Category_Entity category,
            String sortField,
            String sortOrder
    ) {
        Integer id = category.getID_CATEGORY();
        String name = category.getNAME_CATEGORY();

        return categoryDao.getFilteredCategories(id, name, sortField, sortOrder);
    }

    // Phương thức xóa Category
    @Transactional
    public void deleteCategory(Integer id) {
        if (id == null || !categoryDao.categoryExists(id)) {
            throw new IllegalArgumentException("Category with ID " + id + " does not exist.");
        }
        categoryDao.deleteCategoryById(id);
    }

    // Phương thức cập nhật Category
    @Transactional
    public void updateCategory(Category_Entity category) {
        if (category.getID_CATEGORY() == null) {
            throw new IllegalArgumentException("Category ID cannot be null.");
        }
        if (!categoryDao.categoryExists(category.getID_CATEGORY())) {
            throw new IllegalArgumentException("Category with ID " + category.getID_CATEGORY() + " does not exist.");
        }
        if (category.getNAME_CATEGORY() == null || category.getNAME_CATEGORY().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }
        if (category.getNAME_CATEGORY().length() > 50) {
            throw new IllegalArgumentException("Category name is too long (max 50 characters).");
        }
        categoryDao.updateCategory(category.getID_CATEGORY(), category.getNAME_CATEGORY());
    }
}
