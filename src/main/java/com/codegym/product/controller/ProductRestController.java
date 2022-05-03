package com.codegym.product.controller;
import com.codegym.product.model.dto.ProductForm;
import com.codegym.product.model.dto.ProductListDto;
import com.codegym.product.model.entity.Image;
import com.codegym.product.model.entity.Product;
import com.codegym.product.service.image.IImageService;
import com.codegym.product.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductRestController {
    @Autowired
    private IProductService productService;

    @Value("C:/Users/nguye/OneDrive/Desktop/Image/")
    private String uploadPath;

    @Autowired
    private IImageService imageService;

    @GetMapping
    public ResponseEntity<PagedListHolder<ProductListDto>> findAll(@RequestParam(name = "q") Optional<String> q, @PageableDefault(value = 5)Pageable pageable) {
        Page<Product> products;
        List<ProductListDto> productList = new ArrayList<>();
        if (q.isPresent()) {
            products = this.productService.findByName(q.get(), pageable);
        } else {
            products = this.productService.findAll(pageable);
        }
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (int i = 0; i < products.getContent().size(); i++) {
            productList.add(new ProductListDto(products.getContent().get(i).getId(),
                    products.getContent().get(i).getName(),
                    products.getContent().get(i).getPrice(),
                    products.getContent().get(i).getQuantity(),
                    products.getContent().get(i).getDescription(),
                    products.getContent().get(i).getCategory(),
                    this.imageService.findAllImageByProductId(products.getContent().get(i).getId())));
        }
        PagedListHolder<ProductListDto> products1 = new PagedListHolder<>(productList);
        products1.setPageSize(5);
        products1.setPage(0);
        return new ResponseEntity<>(products1, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductListDto> findById(@PathVariable Long id) {
        Optional<Product> product = this.productService.findById(id);
        if (!product.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Image> images = this.imageService.findAllImageByProductId(id);
        ProductListDto productListDto = new ProductListDto(product.get().getId(),
                product.get().getName(),
                product.get().getPrice(),
                product.get().getQuantity(),
                product.get().getDescription(),
                product.get().getCategory(),
                images);
        return new ResponseEntity<>(productListDto, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Product> createProduct(@ModelAttribute ProductForm productForm) {
        Product newProduct = new Product();
        newProduct.setName(productForm.getName());
        newProduct.setPrice(productForm.getPrice());
        newProduct.setQuantity(productForm.getQuantity());
        newProduct.setDescription(productForm.getDescription());
        newProduct.setCategory(productForm.getCategory());
        this.productService.save(newProduct);
        MultipartFile[] listImageFile = productForm.getImage();
        List<String> listFileName = new ArrayList<>();
        for (int i = 0; i < listImageFile.length; i++) {
            long currentTime = System.currentTimeMillis();
            listFileName.add(currentTime + listImageFile[i].getOriginalFilename());
        }
        for (int i = 0; i < listFileName.size(); i++) {
            Image newImage = new Image(listFileName.get(i), newProduct);
            this.imageService.save(newImage);
            try {
                FileCopyUtils.copy(listImageFile[i].getBytes(), new File(uploadPath + listFileName.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable Long id, @ModelAttribute ProductForm productForm) {
        Optional<Product> oldProduct = this.productService.findById(id);
        Product newProduct = oldProduct.get();
        newProduct.setId(newProduct.getId());
        newProduct.setName(productForm.getName());
        newProduct.setPrice(productForm.getPrice());
        newProduct.setQuantity(productForm.getQuantity());
        newProduct.setDescription(productForm.getDescription());
        newProduct.setCategory(productForm.getCategory());
        this.productService.save(newProduct);
        MultipartFile[] listImageFile = productForm.getImage();
        List<String> listFileName = new ArrayList<>();
        if (listImageFile != null) {
            for (int i = 0; i < listImageFile.length; i++) {
                long currentTime = System.currentTimeMillis();
                listFileName.add(currentTime + listImageFile[i].getOriginalFilename());
            }
            for (int i = 0; i < listFileName.size(); i++) {
                Image newImage = new Image(listFileName.get(i), newProduct);
                this.imageService.save(newImage);
                try {
                    FileCopyUtils.copy(listImageFile[i].getBytes(), new File(uploadPath + listFileName.get(i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        Optional<Product> product = this.productService.findById(id);
        if (!product.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.productService.delete(id);
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }
}
