package com.epicom.Repository;

import com.epicom.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Julio on 18/11/2016.
 */
public interface SkuRepository extends JpaRepository<Sku, Long> {
    Optional<Sku> findByProductId(String productId);
}
