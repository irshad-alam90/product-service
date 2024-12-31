package com.realone.products.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.realone.realonemodel.model.products.Product;
import com.realone.realonemodel.repository.products.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	public void saveProductDetails(Product product) {
		productRepository.save(product);
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("No Product found with productId" + id));
		return product;
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	public void updateProductDetails(Product product) {
		Product existingProduct = productRepository.findById(product.getId()).
				orElseThrow(() -> new RuntimeException(product.getName() + "Not Found"));
		if(existingProduct != null) {
			
			productRepository.save(product);
		}
		
	}

	public String uploadImage(String path, MultipartFile file) {
		
		// File Name
		String name = file.getOriginalFilename();
		
		// Full path
		String filePath = path + File.separator + name;
		
		// Create folder if not created
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		// FIle copy
		try {
			Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return name;
	}

}
