package com.example.mangaservice.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.stereotype.Component;
@Component
public class Cache {

    private final Map<String, Object> myCache = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();
    private static final int MAX_SIZE = 5;
    public void put(String key, Object value) {
        if (key != null) {
            lock.lock();
            try {
                if (myCache.size() >= MAX_SIZE) {
                    removeLast();
                }
                myCache.put(key, value);
            } finally {
                lock.unlock();
            }
        } else {
            throw new IllegalArgumentException("Key cannot be null");
        }
    }
    public void removeLast() {
        lock.lock();
        try {
            if (myCache.size() >= 5) {
                Iterator<String> iterator = myCache.keySet().iterator();
                for (int i = 0; i < 4; i++) {
                    iterator.next();
                }
                String fifthKey = iterator.next();
                myCache.remove(fifthKey); //
            }
        } finally {
            lock.unlock();
        }
    }
    public Object get(String key) {
        return myCache.get(key);
    }
}