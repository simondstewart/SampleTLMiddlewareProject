package com.deltek.domain.maconomy;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AlesHavlik on 23/06/2015.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MRestLinks {

    @JsonIgnore
    private Map<String, MRestLink> links = new HashMap<String, MRestLink>();

    @JsonAnyGetter
    public Map<String, MRestLink> getLinks() {
        return links;
    }

    @JsonAnySetter
    public void setLinks(String name, MRestLink value) {
        this.links.put(name, value);
    }
}
