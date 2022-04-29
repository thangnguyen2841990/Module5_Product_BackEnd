package com.codegym.product.service.category;

import com.codegym.product.model.entity.Category;
import com.codegym.product.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService extends IGeneralService<Category> {
    Page<Category> findByName(String name, Pageable pageable);

    List<Category> findAll();
}
