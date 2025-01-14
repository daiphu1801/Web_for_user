package com.projectweb.ProjectWeb.service;

import org.example.dao.CategoryDao;
import org.example.model.Category_Entity;

import jakarta.persistence.EntityManager;

import java.util.List;

public class CategoryService {
    private final CategoryDao categoryDao;

    public CategoryService(EntityManager entityManager) {
        this.categoryDao = new CategoryDao(entityManager);
    }

    public void createNewCategory(Category_Entity category) {
        // Kiểm tra hợp lệ trước khi thêm mới
        if (category.getNAME_CATEGORY() == null || category.getNAME_CATEGORY().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }
        if (category.getNAME_CATEGORY().length() > 50) {
            throw new IllegalArgumentException("Category name is too long (max 50 characters).");
        }
        // Thêm category vào database
        categoryDao.createCategory(category);
    }

    public List<Category_Entity> getFilteredCategories(
            Category_Entity category,
            String sortField,
            String sortOrder
    ) {
        // Kiểm tra tính hợp lệ của trường sortField
        if (reflectField.isPropertyNameMatched(Category_Entity.class, sortField)) {
            Integer id = category.getID_CATEGORY();
            String name = category.getNAME_CATEGORY();
            return categoryDao.getFilteredCategories(id, name, sortField, sortOrder);
        } else {
            throw new RuntimeException("Error with sort field: " + sortField);
        }
    }

    public void deleteCategory(Integer id) {
        // Kiểm tra xem category có tồn tại không trước khi xóa
        if (id == null || !categoryDao.categoryExists(id)) {
            throw new IllegalArgumentException("Category with ID " + id + " does not exist.");
        }
        // Xóa category
        try {
            categoryDao.deleteCategoryById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete category with ID " + id, e);
        }
    }

    public void updateCategory(Category_Entity category) {
        // Kiểm tra hợp lệ trước khi cập nhật
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
        // Cập nhật category
        try {
            categoryDao.updateCategory(category.getID_CATEGORY(), category.getNAME_CATEGORY());
        } catch (Exception e) {
            throw new RuntimeException("Failed to update category with ID " + category.getID_CATEGORY(), e);
        }
    }
}
