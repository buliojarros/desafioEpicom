package com.epicom.controller;

import com.epicom.Repository.SkuRepository;
import com.epicom.model.Notificacao;
import com.epicom.model.Sku;
import com.epicom.util.HttpEpicomConnector;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Julio on 13/11/2016.
 */
@RestController
@RequestMapping(value="/v1/")
public class SkuController {

    private SkuRepository skuRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    public SkuController() {
    }

    @Autowired
    public SkuController(SkuRepository skuRepository){
        this.skuRepository = skuRepository;
    }

    /**
     *      GET /sku/list - LISTA SKUS
     *      @return Todos skus
     * */
    @RequestMapping(value = "/sku/list",
            method = RequestMethod.GET)
    public ResponseEntity<Collection<Sku>> getSkus(){

        Collection<Sku> skus = skuRepository.findAll();

        return ResponseEntity.ok(skus);
    }

    /**
     *      GET /sku/priceRange - LISTA SKUS COM PRECOS EM [10,40]
     *      @return Skus no dado intervalo de precos
     * */
    @RequestMapping(value = "/sku/priceRange",
            method = RequestMethod.GET)
    public ResponseEntity<Collection<Sku>> getSkusPrice(){

        Collection<Sku> skus = skuRepository.findAll();
        ArrayList<Sku> skuList = new ArrayList(skus);

        Integer threadCounter = 0;
        CyclicBarrier barrier;
        barrier = new CyclicBarrier(skuList.size() + 1);
        for (Sku sku : skuList) {
            //get https://sandboxmhubapi.epicom.com.br/v1/marketplace/produtos/{idProduto}/skus/{id}
            Thread sendReqThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection get = null;
                    String params = "?fields=preco";

                    try {
                        get = HttpEpicomConnector.sendGet("https://sandboxmhubapi.epicom.com.br/v1/marketplace/produtos/"
                                + sku.getProductId() + "/skus/" + sku.getId().toString(), params);
                        if (get.getResponseCode() == 200) {
                            InputStream in = get.getInputStream();
                            LinkedHashMap response = mapper.readValue(in, LinkedHashMap.class);
                            Double preco = (Double) response.get("preco");
                            System.out.println("\nSku id : " + sku.getId() + " - Preço : " + preco);
                            if (preco < 10 || preco > 40) {
                                skus.remove(sku);
                            }
                        } else skus.remove(sku);
                        barrier.await();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            sendReqThread.start();

        }
        try {
            barrier.await(); // finalizado
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(skus);
    }

    /**
     *      GET /sku/[id] - GET SKU
     *      @param id             id do Sku
     *      @return dado sku, se existente
     * */
    @RequestMapping(value = "/sku/{id}",
                method = RequestMethod.GET)
    public ResponseEntity<?> getSku(@PathVariable Integer id){

        Sku sku = skuRepository.findOne(id);

        if(sku == null){
            return ResponseEntity.badRequest().body("SKU inexistente");
        }
        return ResponseEntity.ok(sku);
    }

    /**
     *      DELETE /sku/[id] - DELETA SKU
     *      @param id             Id do Sku
     *      @return mensagem de sucesso ou erro
     * */
    @RequestMapping(value = "/sku/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSku(@PathVariable Integer id){

        Sku sku = skuRepository.findOne(id);

        if(sku == null){
            return ResponseEntity.badRequest().body("SKU inexistente");
        } else{
            skuRepository.delete(sku);
            return ResponseEntity.ok("Sku deletado");
        }
    }

    /**
     *      POST /sku/[id] - CRIA OU ATUALIZA SKU
     *      @param id             id do Sku
     *      @param productId      id do produto do sku
     *      @return sku atualizado
     * */
    @RequestMapping(value = "/sku/{id}",
            method = RequestMethod.POST)
    public ResponseEntity<?> updateSku(@PathVariable Integer id, @RequestParam Integer productId){

        Sku sku = skuRepository.findOne(id);
        if(sku==null){
            sku = new Sku(id,productId);
        } else {
            sku.setProductId(productId);
        }
        skuRepository.save(sku);

        return ResponseEntity.ok(sku);
    }


    /**
     *      POST /sku/notify - NOTIFICA SKU
     *      @param notificacao      Notificação
     *      @return sku modificado
     * */
    @RequestMapping(value = "/sku/notify",
            method = RequestMethod.POST)
    public ResponseEntity<?> notificaSku(@RequestBody Notificacao notificacao){

        if (notificacao != null && ("criacao_sku").equals(notificacao.getTipo())){
            Sku sku = new Sku();
            sku.setProductId(notificacao.getParametros().getIdProduto());
            sku.setId(notificacao.getParametros().getIdSku());
            skuRepository.save(sku);

            return ResponseEntity.ok(sku);
        }
        return ResponseEntity.badRequest().body("Notificação não esperada");

    }

}
