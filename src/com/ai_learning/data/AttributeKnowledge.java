/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ai_learning.data;

import java.util.ArrayList;

/**
 *
 * @author natanelia
 */
public class AttributeKnowledge {
    private String name;
    private String type;
    private ArrayList<String> allowedValues;

    public AttributeKnowledge(String name, String type) {
        this.name = name;
        this.type = type;
        this.allowedValues = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(ArrayList<String> allowedValues) {
        this.allowedValues = allowedValues;
    }
    
    public void addAllowedValues(String allowedValue) {
        this.allowedValues.add(allowedValue);
    }
    
    public boolean isNumeric() {
        return (this.type != "array");
    }
}
