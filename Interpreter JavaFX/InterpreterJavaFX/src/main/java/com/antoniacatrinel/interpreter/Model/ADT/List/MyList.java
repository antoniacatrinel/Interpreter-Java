package com.antoniacatrinel.interpreter.Model.ADT.List;

import com.antoniacatrinel.interpreter.Exceptions.ADTException;

import java.util.List;
import java.util.ArrayList;

public class MyList<T> implements IList<T> {
    private List<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public List<T> getList() {
        return this.list;
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public T get(int index) throws ADTException{
        if (index < 0 || index >= this.list.size())
            throw new ADTException(String.format("Index %s does not exist in the list!", index));

        return this.list.get(index);
    }

    @Override
    public void add(T element) {
        this.list.add(element);
    }

    @Override
    public void remove(T element) throws ADTException {
        if (!this.list.contains(element))
            throw new ADTException(String.format("Element %s does not exist in the list!", element));

        this.list.remove(element);
    }

    @Override
    public void removeIndex(int index) throws ADTException {
        if (index < 0 || index >= this.list.size())
            throw new ADTException(String.format("Index %s does not exist in the list!", index));

        this.list.remove(index);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");

        for (T element : this.list) {
            str.append(element.toString()).append(", ");
        }

        if (str.length() > 2) {
            str.setLength(str.length() - 2);
        }

        str.append("}");
        return str.toString();
    }
}