package com.ai_learning.data;

/**
 * Created by dancinggrass on 11/13/15.
 */

import java.util.*;

public class Instance implements Iterable<String> {
    private  ArrayList<String> data;

    public Instance(ArrayList<String> data) {
        this.data = new ArrayList<>();
        for (final String datum : data) {
            this.data.add(new String(datum));
        }
    }
    public Instance(String[] data) {
        this.data = new ArrayList<>();
        for (final String datum : data) {
            this.data.add(new String(datum));
        }
    }

    public Instance(Instance instance) {
        this.data = new ArrayList<>();
        for (final String datum : instance.data) {
            this.data.add(new String(datum));
        }
    }

    public final String getField(int i) {
        return data.get(i);
    }

    public void setField(int i, String str) {
        data.set(i,str); 
    }

    public final ArrayList<String> toList() {
        return data;

    }

    public int size() {
        return this.data.size();
    }

    class InstanceIterator implements Iterator<String> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return this.index < size();
        }

        @Override
        public String next() {
            return getField(this.index++);
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new InstanceIterator();
    }
}
