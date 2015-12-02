package com.ai_learning.data;

/**
 * Created by dancinggrass on 11/13/15.
 */

import java.util.*;

public class DataFrame implements Iterable<Instance> {
    private Integer classIndex = null;

    private ArrayList<Instance> data;
    private String relationName = "";
    private ArrayList<AttributeKnowledge> attributes = new ArrayList<>();

    public DataFrame(ArrayList<Instance> dataset) {
        this.data = new ArrayList<>();
        for (final Instance instance : dataset) {
            this.data.add(new Instance(instance));
        }
    }

    public DataFrame(ArrayList<Instance> dataset, String relationName, ArrayList<AttributeKnowledge> attributes) {
        this.data = new ArrayList<>();
        this.relationName = relationName;
        
        for (final AttributeKnowledge ak : attributes) {
            this.attributes.add(ak);
        }
        
        for (final Instance instance : dataset) {
            this.data.add(new Instance(instance));
        }
    }

    public DataFrame(ArrayList<Instance> dataset, String relationName, ArrayList<AttributeKnowledge> attributes, int classIndex) {
        this.data = new ArrayList<>();
        this.relationName = relationName;
        
        for (final AttributeKnowledge ak : attributes) {
            this.attributes.add(ak);
        }
        
        for (final Instance instance : dataset) {
            this.data.add(new Instance(instance));
        }
        this.classIndex = new Integer(classIndex);
    }

    public DataFrame(DataFrame dataset) {
        this.data = new ArrayList<>();
        this.attributes = dataset.attributes;
        this.relationName = dataset.relationName;
        for (final Instance instance : dataset) {
            this.data.add(new Instance(instance));
        }
        this.classIndex = dataset.classIndex;
    }

    public Instance row(int i) { return this.getInstance(i); }
    public Instance getInstance(int i) {
        return this.data.get(i);
    }

    public Instance col(int i) { return this.getField(i); }
    public Instance getField(int i) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (final Instance instance: this.data) {
            arrayList.add(instance.getField(i));
        }
        return new Instance(arrayList);
    }

    public int getClassIndex() {
        return this.classIndex;
    }

    public Instance getClassField()  {
        if (classIndex == null) {
            if (data.size() > 0) {
                return this.getField(this.getInstance(0).size()-1);
            }
            else {
                return null;
            }
        }
        else {
            return this.getField(this.classIndex);
        }
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public ArrayList<AttributeKnowledge> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<AttributeKnowledge> attributes) {
        this.attributes = attributes;
    }    

    public int size() {
        return this.data.size();
    }

    class DFIterator implements Iterator<Instance> {
        private int index = 0;
        @Override
        public boolean hasNext() {
            return this.index < size();
        }

        @Override
        public Instance next() {
            return data.get(this.index++);
        }
    }
    @Override
    public Iterator<Instance> iterator() {
        return new DFIterator();
    }
}
