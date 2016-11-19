        package com.epicom.model;

        import java.util.HashMap;
        import java.util.Map;
        import javax.annotation.Generated;
        import com.fasterxml.jackson.annotation.JsonAnyGetter;
        import com.fasterxml.jackson.annotation.JsonAnySetter;
        import com.fasterxml.jackson.annotation.JsonIgnore;
        import com.fasterxml.jackson.annotation.JsonInclude;
        import com.fasterxml.jackson.annotation.JsonProperty;
        import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "tipo",
        "dataEnvio",
        "parametros"
})
public class Notificacao {

    @JsonProperty("tipo")
    private String tipo;
    @JsonProperty("dataEnvio")
    private String dataEnvio;
    @JsonProperty("parametros")
    private Parametros parametros;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The tipo
     */
    @JsonProperty("tipo")
    public String getTipo() {
        return tipo;
    }

    /**
     *
     * @param tipo
     * The tipo
     */
    @JsonProperty("tipo")
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     *
     * @return
     * The dataEnvio
     */
    @JsonProperty("dataEnvio")
    public String getDataEnvio() {
        return dataEnvio;
    }

    /**
     *
     * @param dataEnvio
     * The dataEnvio
     */
    @JsonProperty("dataEnvio")
    public void setDataEnvio(String dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    /**
     *
     * @return
     * The parametros
     */
    @JsonProperty("parametros")
    public Parametros getParametros() {
        return parametros;
    }

    /**
     *
     * @param parametros
     * The parametros
     */
    @JsonProperty("parametros")
    public void setParametros(Parametros parametros) {
        this.parametros = parametros;
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
