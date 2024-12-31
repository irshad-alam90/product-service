package com.realone.products.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.realone.products.service.ProductService;
import com.realone.realonemodel.model.products.FileResponse;
import com.realone.realonemodel.model.products.Product;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	private Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/upload")
	public ResponseEntity<?> fileUpload(@RequestParam("image") MultipartFile image){
		String fileName = null;
		try {
			fileName = this.productService.uploadImage(path, image);
		} catch (Exception e) {
			logger.error("error in uploading file for : " + fileName);
			return new ResponseEntity<>(new FileResponse(null, "Image uploading error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new FileResponse(fileName, "Image uploaded successfully"), HttpStatus.OK);
	}
	
	@PostMapping("/save_product")
	public ResponseEntity<?> saveProduct(@RequestBody Product product) {
		try {
			productService.saveProductDetails(product);
			return new ResponseEntity<>("created", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error creating product for : "+ product.getName(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@GetMapping("/get-products")
	public ResponseEntity<?> getAllProducts(){
		try {
			return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error getting products : ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Long id){
		try {
			return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error getting product: ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
	}
	
	@PutMapping("/update_product")
	public ResponseEntity<?> updateProduct(@RequestBody Product product) {
		try {
			productService.updateProductDetails(product);
			return new ResponseEntity<>("edited", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error editing product : "+ product.getName(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable Long id){
		try {
			productService.deleteProduct(id);
			return new ResponseEntity<>("deleted", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error deleting product : ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
