package Model.ADT.Stack;

import Exceptions.ADTException;

import java.util.*;

public class MyStack<T> implements IStack<T> {
    private Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public List<T> getElements() {
        List<T> elements = new ArrayList<>(this.stack);
        Collections.reverse(elements);

        return elements;
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public T peek() throws ADTException {
        if (this.isEmpty())
            throw new ADTException("Stack is empty!");

        return this.stack.peek();
    }

    @Override
    public void push(T element) {
        this.stack.push(element);
    }

    @Override
    public T pop() throws ADTException{
        if (this.isEmpty())
            throw new ADTException("Stack is empty!");

        return this.stack.pop();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");

        for (T element: this.stack) {
            str.append(element.toString()).append(" | ");
        }

        if (str.length() > 3) {
            str.setLength(str.length() - 3);
        }

        str.append("}");
        return str.toString();
    }
}
