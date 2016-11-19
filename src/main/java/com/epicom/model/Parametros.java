package com.epicom.model;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Julio on 19/11/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "idProduto",
        "idSku"
})
public class Parametros {

    @JsonProperty("idProduto")
    private Integer idProduto;
    @JsonProperty("idSku")
    private Integer idSku;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The idProduto
     */
    @JsonProperty("idProduto")
    public Integer getIdProduto() {
        return idProduto;
    }

    /**
     *
     * @param idProduto
     * The idProduto
     */
    @JsonProperty("idProduto")
    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    /**
     *
     * @return
     * The idSku
     */
    @JsonProperty("idSku")
    public Integer getIdSku() {
        return idSku;
    }

    /**
     *
     * @param idSku
     * The idSku
     */
    @JsonProperty("idSku")
    public void setIdSku(Integer idSku) {
        this.idSku = idSku;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
