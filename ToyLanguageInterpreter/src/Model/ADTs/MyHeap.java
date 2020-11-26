package Model.ADTs;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyHeap<T> implements IHeap<T> {
    private Map<Integer, T> map;
    private int freeValue;

    public MyHeap() {
        this.map = new ConcurrentHashMap<>();
        this.freeValue = 0;
    }

    @Override
    public int allocate(T value) {
         freeValue += 1;
         map.put(freeValue, value);
         return freeValue;
    }

    @Override
    public T get(int addr) {
        return map.get(addr);
    }

    @Override
    public void put(int addr, T value) {
        map.put(addr, value);
    }

    @Override
    public T deallocate(int addr) {
        return map.remove(addr);
    }

    @Override
    public Map<Integer, T> getContent() {
        return map;
    }

    @Override
    public void setContent(Map<Integer, T> content) {
        map.clear();
        for(int i : content.keySet())
            map.put(i, content.get(i));
    }

    @Override
    public String toString() {
        StringBuilder s= new StringBuilder("{");
        for(HashMap.Entry<Integer,T> entry : map.entrySet()){
            s.append(entry.getKey())
                    .append("->")
                    .append(entry.getValue())
                    .append(", ");
        }
        s.append("}");
        return s.toString();
    }
}
