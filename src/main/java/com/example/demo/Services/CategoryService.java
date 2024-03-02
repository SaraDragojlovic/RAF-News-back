package com.example.demo.Services;

import com.example.demo.Entities.Category;
import com.example.demo.Repositories.Category.CategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class  CategoryService {

    @Inject
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return this.categoryRepository.addCategory(category);
    }
    public List<Category> pageCategory(Integer page) {
        return this.categoryRepository.pageCategory(page);
    }
    public List<Category> allCategories() {
        return this.categoryRepository.allCategories();
    }
    public Category findCategory(Integer id) {
        return this.categoryRepository.findCategory(id);
    }
    public Category updateCategory(Category category) {
        return this.categoryRepository.updateCategory(category);
    }
    public void deleteCategory(Integer id) throws Exception {
        this.categoryRepository.deleteCategory(id);
    }

}
