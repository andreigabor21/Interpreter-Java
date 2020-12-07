package Model.ADTs;

import Model.Exceptions.MyException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T>{
    private final List<T> list;

    public MyList() {
        this.list = new ArrayList<T>();
    }

    @Override
    public void add(T item) {
        list.add(item);
    }

    @Override
    public void remove(T item) throws MyException {
        if (!this.list.contains(item))
            throw new MyException("Object doesn't exist!");
        list.remove(item);
    }

    @Override
    public T get(int index) throws MyException {
        try {
            return list.get(index);
        }
        catch(IndexOutOfBoundsException e) {
            throw new MyException("Index out of bounds");
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List getContent() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");

        for (T el : list) {
            result.append(el.toString())
                    .append("|");
        }
        result.append("}");
        return result.toString();
    }
}
