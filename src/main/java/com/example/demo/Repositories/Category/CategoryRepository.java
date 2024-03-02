package com.example.demo.Repositories.Category;

import com.example.demo.Entities.Category;

import java.util.List;

public interface CategoryRepository {
    Category addCategory(Category category);
    List<Category> pageCategory(Integer page);
    List<Category> allCategories();
    Category findCategory(Integer id);
    Category updateCategory(Category category);
    void deleteCategory(Integer id) throws Exception;
}
