package com.codegym.product.controller;

import com.codegym.product.model.entity.Image;
import com.codegym.product.service.image.IImageService;
import com.codegym.product.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private IImageService imageService;


    @GetMapping("/{productId}")
    public ResponseEntity<List<Image>> findAllImage(@PathVariable Long productId) {
        List<Image> images = this.imageService.findAllImageByProductId(productId);
        if (images.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
    @GetMapping("/details/{id}")
    public ResponseEntity<Image> findById(@PathVariable Long id) {
        Optional<Image> image = this.imageService.findById(id);
        if (!image.isPresent()) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(image.get(), HttpStatus.OK);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<Image> deleteImage(@PathVariable Long productId) {
        List<Image> images = this.imageService.findAllImageByProductId(productId);
        for (int i = 0; i < images.size(); i++) {
            this.imageService.delete(images.get(i).getId());
        }
        return new ResponseEntity<>( HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Image> delete(@PathVariable Long id) {
        this.imageService.delete(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
