package Model.ADT.Heap;

import Exceptions.ADTException;

import java.util.HashMap;
import java.util.Set;

public class MyHeap<V> implements IHeap<V> {
    private HashMap<Integer, V> heap;
    private Integer freeAddress;

    public MyHeap() {
        this.heap = new HashMap<>();
        this.freeAddress = 1;
    }

    private Integer newValue() {
        this.freeAddress += 1;

        while (this.freeAddress == 0 || this.heap.containsKey(this.freeAddress))
            this.freeAddress += 1;

        return this.freeAddress;
    }

    @Override
    public HashMap<Integer, V> getHeap() {
        return this.heap;
    }

    @Override
    public void setHeap(HashMap<Integer, V> heap) {
        this.heap = heap;
    }

    @Override
    public Set<Integer> keySet() {
        return this.heap.keySet();
    }

    @Override
    public boolean containsKey(Integer key) {
        return this.heap.containsKey(key);
    }

    @Override
    public V get(Integer key) throws ADTException {
        if (!this.containsKey(key))
            throw new ADTException(String.format("Key %s is not defined in the heap!", key));

        return this.heap.get(key);
    }

    @Override
    public Integer allocate(V value) {
        int address = this.freeAddress;
        this.heap.put(address, value);

        this.freeAddress = newValue();
        return address;
    }

    @Override
    public void put(Integer key, V value) {
        this.heap.put(key,value);
    }

    @Override
    public void update(Integer key, V value) throws ADTException {
        if (!this.containsKey(key))
            throw new ADTException(String.format("Key %s is not defined in the heap!", key));

        this.heap.put(key, value);
    }

    @Override
    public void remove(Integer key) throws ADTException {
        if (!this.containsKey(key))
            throw new ADTException(String.format("Key %s is not defined in the heap!", key));

        this.heap.remove(key);
        this.freeAddress = key;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");

        for (Integer key : this.heap.keySet()) {
            str.append(key.toString()).append(" -> ").append(this.heap.get(key).toString()).append(", ");
        }

        if (str.length() > 2) {
            str.setLength(str.length() - 2);
        }

        str.append("}");
        return str.toString();
    }
}
