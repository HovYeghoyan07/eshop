package eshopportal.service;

import eshopportal.model.Category;

import java.util.List;

public interface CategoryService {

void add(Category category);


Category getCategoryById(int id);

void deleteCategoryById(int id);

List<Category> getAllCategories();

void editCategory(Category category);


}
