package com.codegym.product.model.dto;

import com.codegym.product.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ProductForm {

    private String name;

    private double price;

    private int quantity;

    private String description;

    private MultipartFile[] image;

    private Category category;
}
