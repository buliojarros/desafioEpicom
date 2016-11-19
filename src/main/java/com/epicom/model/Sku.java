package com.epicom.model;

import javax.persistence.*;

/**
 * Created by Julio on 13/11/2016.
 */
@Entity
@Table(name = "sku")
public class Sku {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "product_id")
    private Integer productId;

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
