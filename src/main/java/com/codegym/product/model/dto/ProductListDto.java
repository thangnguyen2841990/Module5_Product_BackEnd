package com.codegym.product.model.dto;

import com.codegym.product.model.entity.Category;
import com.codegym.product.model.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDto {
    private Long id;

    private String name;

    private double price;

    private int quantity;

    private String description;

    private Category category;

    private List<Image> images;
}
