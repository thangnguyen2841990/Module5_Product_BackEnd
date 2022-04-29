package com.codegym.product.service.product;

import com.codegym.product.model.entity.Product;
import com.codegym.product.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService extends IGeneralService<Product> {
    Page<Product> findByName(String name, Pageable pageable);
    List<Product>  findAll();
}
