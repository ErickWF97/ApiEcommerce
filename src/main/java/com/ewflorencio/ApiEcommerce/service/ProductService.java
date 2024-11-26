package com.ewflorencio.ApiEcommerce.service;


import com.ewflorencio.ApiEcommerce.model.Product;
import com.ewflorencio.ApiEcommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getById(int id) {
        return repository.findById(id).orElse(new Product(-1));
    }

    public Product addOrUpdateProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getName());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repository.save(product);

    }

    public void deleteProduct(int id) {
        repository.deleteById(id);
    }

    public List<Product> searchProduct(String keyword) {
        return repository.searchProducts(keyword);
    }
}
