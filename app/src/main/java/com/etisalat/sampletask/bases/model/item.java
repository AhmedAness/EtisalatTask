package com.etisalat.sampletask.bases.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name ="item")
public class item {

    @Element(name = "id")
    private String id;
    @Element (name = "name")
    private String name;
    @Element (name = "cost")
    private String cost;
    @Element (name = "description")
    private String description;

    public item(String id, String name, String cost, String description) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
    }
    public item() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
