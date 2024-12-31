package com.realone.products.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.realone.products.service.CategoryService;
import com.realone.realonemodel.model.products.Category;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	private Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@PostMapping("/save_category")
	public ResponseEntity<?> saveCategory(@RequestBody Category category) {
		try {
			categoryService.saveCategory(category);
			return new ResponseEntity<>("created", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error creating category for : "+ category.getName(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/categories")
	public ResponseEntity<?> getCategories(){
		try {
			return new ResponseEntity<>(categoryService.getCategories(),HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error getting categories : ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/category/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Long id){
		try {
			return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error getting category: ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
	}
	
	@PutMapping("/update_category")
	public ResponseEntity<?> updateCategory(@RequestBody Category category) {
		try {
			categoryService.updateCategory(category);
			return new ResponseEntity<>("edited", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error editing product : "+ category.getName(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/delete_category/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable Long id){
		try {
			categoryService.deleteCategory(id);
			return new ResponseEntity<>("deleted", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error deleting category : ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
