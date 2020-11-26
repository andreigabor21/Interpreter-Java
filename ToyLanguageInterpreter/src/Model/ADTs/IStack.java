package Model.ADTs;

import Model.Exceptions.MyException;

public interface IStack<T> {
    T pop() throws MyException;
    void push(T value);
    boolean isEmpty();
}
