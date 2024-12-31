package com.realone.products.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realone.realonemodel.model.products.Category;
import com.realone.realonemodel.repository.products.CategoryRepository;


@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public void saveCategory(Category category) {
		
		categoryRepository.save(category);
	}

	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}

	public Category getCategoryById(Long id) {
		Category category = categoryRepository.findById(id)
							.orElseThrow(() -> new RuntimeException("No category found with categoryId : " + id));
		return category;
	}

	public void updateCategory(Category category) {
		Category existingCategory = categoryRepository.findById(category.getId())
				.orElseThrow(() -> new RuntimeException("No category found with categoryId : " + category.getId()));
		if(existingCategory != null) {
			categoryRepository.save(category);
		}
	}

	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}

}
