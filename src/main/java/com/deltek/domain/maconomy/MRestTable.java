package com.deltek.domain.maconomy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "meta",
    "records"
})
public class MRestTable<T> {

    @JsonProperty("meta")
    private MRestTableMeta meta;
    @JsonProperty("links")
    private MRestLinks links;
    @JsonProperty("records")
    private List<MRestRecord<T>> records = new ArrayList<>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The meta
     */
    @JsonProperty("meta")
    public MRestTableMeta getMeta() {
        return meta;
    }

    /**
     * 
     * @param meta
     *     The meta
     */
    @JsonProperty("meta")
    public void setMeta(MRestTableMeta meta) {
        this.meta = meta;
    }

    @JsonProperty("links")
    public MRestLinks getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(MRestLinks links) {
        this.links = links;
    }

    /**
     * 
     * @return
     *     The records
     */
    @JsonProperty("records")
    public List<MRestRecord<T>> getRecords() {
        return records;
    }

    /**
     * 
     * @param records
     *     The records
     */
    @JsonProperty("records")
    public void setRecords(List<MRestRecord<T>> records) {
        this.records = records;
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
