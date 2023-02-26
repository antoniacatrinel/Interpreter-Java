package Model.ADT.List;

import Exceptions.ADTException;

import java.util.List;

public interface IList<T> {
    List<T> getList();

    boolean isEmpty();
    int size();
    T get(int index) throws ADTException;

    void add(T element);
    void remove(T element) throws ADTException;
    void removeIndex(int index) throws ADTException;
}
