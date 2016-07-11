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
    "panes",
    "links"
})
public class MRestData<T, U> {

    @JsonProperty("panes")
    private MRestPanes<T, U> panes;
    @JsonProperty("links")
    private MRestLinks links;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The panes
     */
    @JsonProperty("panes")
    public MRestPanes<T, U> getPanes() {
        return panes;
    }

    /**
     * 
     * @param panes
     *     The panes
     */
    @JsonProperty("panes")
    public void setPanes(MRestPanes<T, U> panes) {
        this.panes = panes;
    }

    @JsonProperty("links")
    public MRestLinks getLinks() {
        return links;
    }

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
