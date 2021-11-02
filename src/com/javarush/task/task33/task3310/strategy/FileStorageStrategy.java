package com.javarush.task.task33.task3310.strategy;

import java.util.Objects;

public class FileStorageStrategy implements StorageStrategy {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;
    FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    int size;
    long maxBucketSize;

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    int hash(Long k) {
        int h;
        return k == 0 ? 0 : (h = k.hashCode()) ^ (h >>> 16);
    }

    int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    Entry getEntry(Long key) {
        int index = indexFor(hash(key), table.length);

        FileBucket bucket = table[index];
        Entry entry = bucket.getEntry();
        while (entry != null) {
            if (Objects.equals(entry.getKey(), key))
                return entry;
            entry = entry.next;
        }
        return null;
    }

    void resize(int newCapacity) {
        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
        for (FileBucket bucket : table) {
            bucket.remove();
        }
        table = newTable;
    }

    void transfer(FileBucket[] newTable) {
        FileBucket[] src = table;
        int newCapacity = newTable.length;
        for (int j = 0; j < table.length; j++) {
            Entry e = src[j].getEntry();
            if (e != null) {
                do {
                    Entry next = e.next;
                    int i = indexFor(e.hash, newCapacity);
                    e.next = newTable[i].getEntry();
                    newTable[i].putEntry(e);
                    e = next;
                } while (e != null);
            }
        }
    }

    void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = null;
        if(table[bucketIndex] == null)
            table[bucketIndex] = new FileBucket();
        e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        if (table[bucketIndex].getFileSize() > bucketSizeLimit)
            resize(2 * table.length);
    }

    void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
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
        if(table[index] != null) {
            Entry entry = table[index].getEntry();
            while (entry != null) {
                if (entry.getKey().equals(key)) {
                    entry.value = value;
                    return;
                }
                entry = entry.next;
            }
        }
        addEntry(h, key, value, index);
    }

    @Override
    public Long getKey(String value) {
        for (FileBucket bucket : table) {
            if(bucket != null) {
                Entry entry = bucket.getEntry();
                while (entry != null) {
                    if (entry.getValue().equals(value)) {
                        return entry.getKey();
                    }
                    entry = entry.next;
                }
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
