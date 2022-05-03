package com.codegym.product.service.image;

import com.codegym.product.model.entity.Image;
import com.codegym.product.service.IGeneralService;

import java.util.List;

public interface IImageService extends IGeneralService<Image> {
    List<Image> findAll();

    List<Image> findAllImageByProductId(Long productId);

    void delteImageByProductId(Long productId);


}
