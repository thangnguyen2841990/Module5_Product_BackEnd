package com.codegym.product.controller;

import com.codegym.product.model.dto.ProductForm;
import com.codegym.product.model.entity.Product;
import com.codegym.product.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductRestController {
    @Autowired
    private IProductService productService;

    @Value("C:/Users/nguye/OneDrive/Desktop/image/")
    private String uploadPath;

    @GetMapping
    public ResponseEntity<Page<Product>> findAll(@RequestParam(name = "q") Optional<String> q, @PageableDefault(value = 5)Pageable pageable){
        Page<Product> products;
        if (q.isPresent()){
            products = this.productService.findByName(q.get(), pageable);
        }else {
            products = this.productService.findAll(pageable);
        }
        if (products.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        Optional<Product> product = this.productService.findById(id);
        if (!product.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product.get(),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Product> createoldProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> oldProduct = this.productService.findById(id);
        Product newProduct = oldProduct.get();
        newProduct.setId(id);
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct.setQuantity(product.getQuantity());
        newProduct.setDescription(product.getDescription());
        newProduct.setImage(product.getImage());
        newProduct.setCategory(product.getCategory());
        return new ResponseEntity<>(this.productService.save(newProduct), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id){
        Optional<Product> product = this.productService.findById(id);
        if (!product.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.productService.delete(id);
        return new ResponseEntity<>(product.get(),HttpStatus.OK);
    }

}
