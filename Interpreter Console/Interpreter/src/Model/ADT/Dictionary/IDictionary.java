package Model.ADT.Dictionary;

import Exceptions.ADTException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public interface IDictionary<K, V> {
    HashMap<K, V> getDictionary();
    Set<K> keySet();
    Collection<V> values();

    boolean isEmpty();
    boolean isDefined(K key);
    V lookUp(K key) throws ADTException;

    void put(K key, V value);
    void update(K key, V value) throws ADTException;
    void remove(K key) throws ADTException;

    IDictionary<K, V> deepCopy() throws ADTException;
}
