package com.epicom.controller;

import com.epicom.Repository.SkuRepository;
import com.epicom.model.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Julio on 13/11/2016.
 */
@RestController
public class SkuController {

    private final SkuRepository skuRepository;

    @Autowired
    SkuController(SkuRepository skuRepository){
        this.skuRepository = skuRepository;
    }


    @RequestMapping(value = "/skus",
            method = RequestMethod.GET)
    public ResponseEntity<Collection<Sku>> getSkus(){

        Collection<Sku> skus = skuRepository.findAll();

        return ResponseEntity.ok(skus);
    }

    @RequestMapping(value = "/sku/{id}",
                method = RequestMethod.GET)
    public ResponseEntity<?> getSku(@PathVariable Long id){

        Sku sku = skuRepository.findOne(id);

        if(sku == null){
            return ResponseEntity.badRequest().body("SKU inexistente");
        }
        return ResponseEntity.ok(sku);
    }
}
