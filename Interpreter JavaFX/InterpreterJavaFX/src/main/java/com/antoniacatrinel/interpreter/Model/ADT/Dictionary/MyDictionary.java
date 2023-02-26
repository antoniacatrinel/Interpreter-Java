package com.antoniacatrinel.interpreter.Model.ADT.Dictionary;

import com.antoniacatrinel.interpreter.Exceptions.ADTException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private HashMap<K, V> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<>();
    }

    @Override
    public HashMap<K, V> getDictionary() {
        return this.dictionary;
    }

    @Override
    public Set<K> keySet() {
        return this.dictionary.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.dictionary.values();
    }

    @Override
    public boolean isEmpty() {
        return this.dictionary.isEmpty();
    }

    @Override
    public boolean isDefined(K key) {
        return this.dictionary.containsKey(key);
    }

    @Override
    public V lookUp(K key) throws ADTException{
        if (!this.isDefined(key))
            throw new ADTException(String.format("Key %s is not defined in the dictionary!", key));

        return this.dictionary.get(key);
    }

    @Override
    public void put(K key, V value) {
        this.dictionary.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException{
        if (!this.isDefined(key))
            throw new ADTException(String.format("Key %s is not defined in the dictionary!", key));

        this.dictionary.put(key, value);
    }

    @Override
    public void remove(K key) throws ADTException{
        if (!this.isDefined(key))
            throw new ADTException(String.format("Key %s is not defined in the dictionary!", key));

        this.dictionary.remove(key);
    }

    @Override
    public IDictionary<K, V> deepCopy() throws ADTException {
        IDictionary<K, V> copy = new MyDictionary<>();

        for (K key: this.keySet())
            copy.put(key, this.lookUp(key));

        return copy;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");

        for (K key : this.dictionary.keySet()) {
            str.append(key.toString()).append(" -> ").append(this.dictionary.get(key).toString()).append(", ");
        }

        if (str.length() > 2) {
            str.setLength(str.length() - 2);
        }

        str.append("}");
        return str.toString();
    }
}
