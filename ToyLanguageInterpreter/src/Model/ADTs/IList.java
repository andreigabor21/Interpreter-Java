package Model.ADTs;

import Model.Exceptions.MyException;

public interface IList<T> {
    void add(T item);
    void remove(T item) throws MyException;
    T get(int index) throws MyException;
    int size();
}