package com.javarush.task.task33.task3310.strategy;

public interface StorageStrategy {
    boolean containsValue(String value);
    boolean containsKey(Long key);
    void put(Long key, String value);
    Long getKey(String value);
    String getValue(Long key);
}
