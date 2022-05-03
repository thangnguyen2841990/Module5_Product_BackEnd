package com.codegym.product.repository;

import com.codegym.product.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long> {
    @Query(value = "select * from image where product_id = ?1", nativeQuery = true)
    List<Image> findAllImageByProductId(Long productId);

    @Query(value = "delete from image where product_id = ?1", nativeQuery = true)
    void delteImageByProductId(Long productId);
}
