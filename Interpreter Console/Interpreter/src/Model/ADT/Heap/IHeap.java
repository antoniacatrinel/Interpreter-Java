package Model.ADT.Heap;

import Exceptions.ADTException;

import java.util.HashMap;
import java.util.Set;

public interface IHeap<V> {
    HashMap<Integer, V> getHeap();
    void setHeap(HashMap<Integer, V> heap);
    Set<Integer> keySet();

    boolean containsKey(Integer key);
    V get(Integer key) throws ADTException;
    Integer allocate(V value);

    void put(Integer key, V value);
    void update(Integer key, V value) throws ADTException;
    void remove(Integer key) throws ADTException;
}
