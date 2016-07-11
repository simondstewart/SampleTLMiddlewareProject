package com.deltek.domain.maconomy;

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
    "card",
    "table"
})
public class MRestPanes<T, U> {

    @JsonProperty("card")
    private MRestCard<T> card;
    @JsonProperty("table")
    private MRestTable<U> table;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The card
     */
    @JsonProperty("card")
    public MRestCard<T> getCard() {
        return card;
    }

    /**
     * 
     * @param card
     *     The card
     */
    @JsonProperty("card")
    public void setCard(MRestCard<T> card) {
        this.card = card;
    }

    /**
     *
     * @return
     *     The table
     */
    @JsonProperty("table")
    public MRestTable<U> getTable() {
        return table;
    }

    /**
     *
     * @param table
     *     The table
     */
    @JsonProperty("table")
    public void setTable(MRestTable<U> table) {
        this.table = table;
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
