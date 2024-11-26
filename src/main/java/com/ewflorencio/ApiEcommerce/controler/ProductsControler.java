package com.ewflorencio.ApiEcommerce.controler;


import com.ewflorencio.ApiEcommerce.model.Product;
import com.ewflorencio.ApiEcommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductsControler {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id){
        Product product =  service.getById(id);
        if (product.getId() > 0)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id){
        Product product =  service.getById(id);
        if (product.getId() > 0)
            return new ResponseEntity<>(product.getImageData(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
        List<Product> productList =  service.searchProduct(keyword);
        return new ResponseEntity<>(productList, HttpStatus.OK);

    }

    @PostMapping("/product")
    public ResponseEntity<?> postProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){

        try {
            Product saveProduct = service.addOrUpdateProduct(product, imageFile);
            return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/product")
    public  ResponseEntity<String> putProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){

        try {
            Product saveProduct = service.addOrUpdateProduct(product, imageFile);
            return new ResponseEntity<>("Atualizado com sucesso!!!", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/product/{id}")
    public  ResponseEntity<String> deleteProduct(@PathVariable int id){

        Product product =  service.getById(id);

        if (product.getId() > 0) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deletado com sucesso!!!", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
