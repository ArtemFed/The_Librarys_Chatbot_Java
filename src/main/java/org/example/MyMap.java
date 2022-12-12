package org.example;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class MyMap extends HashMap<Book, Integer> implements Map<Book, Integer>, Iterable<Book> {
    private final HashMap<Book, Integer> map;

    MyMap() {
        map = new HashMap<>();
    }

    MyMap(MyMap original) {
        map = new HashMap<>();
        for (Entry<Book, Integer> entry : original.entrySet()) {
            map.put(entry.getKey(), 0);
        }
    }

    public Integer put(Book key, Integer val) {
        return map.put(key, val);
    }

    public Integer get(Book key) {
        return map.get(key);
    }

    public boolean contains(Book book) {
        return map.containsKey(book);
    }

    public Set<Entry<Book, Integer>> entrySet() {
        return map.entrySet();
    }

    @Override
    public Iterator<Book> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public void forEach(Consumer action) {
        Iterable.super.forEach(action);
    }
}
