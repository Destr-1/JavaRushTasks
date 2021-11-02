package com.javarush.task.task33.task3310.strategy;

public class OurHashMapStorageStrategy implements StorageStrategy {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    int size;
    int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    float loadFactor = DEFAULT_LOAD_FACTOR;

    int hash(Long k) {
        int h;
        return k == 0 ? 0 : (h = k.hashCode()) ^ (h >>> 16);
    }

    int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    Entry getEntry(Long key) {
        int index = indexFor(hash(key), table.length);
        Entry entry = table[index];
        while (entry != null) {
            if (entry.getKey() == key)
                return entry;
            entry = entry.next;
        }
        return null;
    }

    void resize(int newCapacity) {
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }

    void transfer(Entry[] newTable) {
        Entry[] src = table;
        int newCapacity = newTable.length;
        for (int j = 0; j < table.length; j++) {
            Entry e = src[j];
            if (e != null) {
                src[j] = null;
                do {
                    Entry next = e.next;
                    int i = indexFor(e.hash, newCapacity);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                } while (e != null);
            }
        }
    }

    void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
        if (size++ >= threshold)
            resize(2 * table.length);
    }

    void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
        size++;
    }

    @Override
    public boolean containsValue(String value) {
        return getKey(value) != null;
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public void put(Long key, String value) {
        int h = hash(key);
        int index = indexFor(h, table.length);
        addEntry(h, key, value, index);
    }

    @Override
    public Long getKey(String value) {
        for (Entry entry : table) {
            while (entry != null) {
                if (entry.getValue().equals(value)) {
                    return entry.getKey();
                }
                entry = entry.next;
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        Entry entry = getEntry(key);
        if (entry != null)
            return entry.getValue();
        return null;
    }
}
