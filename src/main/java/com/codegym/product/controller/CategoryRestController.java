package com.codegym.product.controller;

import com.codegym.product.model.entity.Category;
import com.codegym.product.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/categories")
public class CategoryRestController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        List<Category> categories = this.categoryService.findAll();
        if (categories.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        Optional<Category> category = this.categoryService.findById(id);
        if (!category.isPresent()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category.get(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Category> createNewCategory(@RequestBody Category category){
        return new ResponseEntity<>(this.categoryService.save(category), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Category> editCategory(@PathVariable Long id, @RequestBody Category category){
        Optional<Category> oldCategory = this.categoryService.findById(id);
        if (!oldCategory.isPresent()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        oldCategory.get().setId(id);
        oldCategory.get().setName(category.getName());
        this.categoryService.save(oldCategory.get());
        return new ResponseEntity<>(oldCategory.get(), HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id){
        Optional<Category> category = this.categoryService.findById(id);
        if (!category.isPresent()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.categoryService.delete(id);
        return new ResponseEntity<>(category.get(),HttpStatus.OK);
    }

}
