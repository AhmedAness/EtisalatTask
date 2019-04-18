package com.etisalat.sampletask.bases.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root (name ="menu")
public class menu {

    @ElementList(entry="item", inline=true)
    private List<item> item;



    public menu(List<item> item) {
        this.item = item;
    }

    public menu() {
    }

    public List<item> getItem() {
        return item;
    }

    public void setItem(List<item> item) {
        this.item = item;
    }
}
