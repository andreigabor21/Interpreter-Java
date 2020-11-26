package Model.ADTs;

import Model.Exceptions.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyDictionary<K,V> implements IDictionary<K, V> {
    private Map<K,V> dictionary;

    public MyDictionary(){
        dictionary = new ConcurrentHashMap<>();
    }

    @Override
    public V lookup(K key) throws MyException {
        return dictionary.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public void update(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public void delete(K key) throws MyException {
        if (!dictionary.containsKey(key))
            throw new MyException("Key doesn't exist.");
        dictionary.remove(key);
    }

    @Override
    public Map<K, V> getContent() {
        return dictionary;
    }

    @Override
    public void setContent(Map<K, V> content) {
        dictionary = content;
    }


    @Override
    public IDictionary<K,V> clone() {
        IDictionary<K,V> copy = new MyDictionary<>();
        for (K key : dictionary.keySet()) {
            copy.update(key,dictionary.get(key));
        }
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String prefix = "";
        for(Map.Entry<K,V> entry : dictionary.entrySet()) {
            builder.append(prefix);
            prefix = ", ";
            builder.append("(")
                    .append(entry.getKey())
                    .append("->")
                    .append(entry.getValue())
                    .append(")");
        }
        return builder.toString();
    }
}
