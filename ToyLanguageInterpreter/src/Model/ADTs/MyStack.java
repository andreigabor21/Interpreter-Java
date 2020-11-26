package Model.ADTs;

import Model.Exceptions.MyException;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    private final Deque<T> stack;

    public MyStack() {
        this.stack = new LinkedList<>();
    }

    @Override
    public T pop() throws MyException {
        if(stack.size() == 0)
            throw new MyException("Stack is empty");
        return stack.pop();
    }

    @Override
    public void push(T value) {
        stack.push(value);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for (T el : stack) {
            result.append(el)
                    .append("|");
        }
        result.append("}");
        return result.toString();
    }
}
