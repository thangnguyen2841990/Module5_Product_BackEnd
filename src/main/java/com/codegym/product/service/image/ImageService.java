package com.codegym.product.service.image;

import com.codegym.product.model.entity.Image;
import com.codegym.product.repository.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService implements IImageService{
    @Autowired
    private IImageRepository imageRepository;
    @Override
    public Page<Image> findAll(Pageable pageable) {
        return imageRepository.findAll(pageable);
    }

    @Override
    public Optional<Image> findById(Long id) {
        return this.imageRepository.findById(id);
    }

    @Override
    public Image save(Image image) {
        return this.imageRepository.save(image);
    }

    @Override
    public void delete(Long id) {
            this.imageRepository.deleteById(id);
    }

    @Override
    public List<Image> findAll() {
        return this.imageRepository.findAll();
    }

    @Override
    public List<Image> findAllImageByProductId(Long productId) {
        return this.imageRepository.findAllImageByProductId(productId);
    }

    @Override
    public void delteImageByProductId(Long productId) {
         this.imageRepository.delteImageByProductId(productId);
    }
}
