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
        "meta",
        "data",
        "links"
})
public class MRestRecord<T> {

    @JsonProperty("meta")
    private MRestRecordMeta meta;

    @JsonProperty("data")
    private T data;

    @JsonProperty("links")
    private MRestLinks links;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The data
     */
    @JsonProperty("data")
    public T getData() {
        return data;
    }

    /**
     * @param data The data
     */
    @JsonProperty("data")
    public void setData(T data) {
        this.data = data;
    }

    /**
     * @return The meta
     */
    @JsonProperty("meta")
    public MRestRecordMeta getMeta() {
        return meta;
    }

    /**
     * @param meta The meta
     */
    @JsonProperty("meta")
    public void setMeta(MRestRecordMeta meta) {
        this.meta = meta;
    }


    /**
     * @return The links
     */
    @JsonProperty("links")
    public MRestLinks getLinks() {
        return links;
    }

    /**
     * @param links The links
     */
    @JsonProperty("links")
    public void setLinks(MRestLinks links) {
        this.links = links;
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
