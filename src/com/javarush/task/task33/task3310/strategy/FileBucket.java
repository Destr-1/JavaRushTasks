package com.javarush.task.task33.task3310.strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {
    Path path;

    public static void main(String[] args) {
        Entry e = new Entry(123, 1L, "a", new Entry(1234, 2L, "b", new Entry(12345, 3L, "c", null)));
        FileBucket fb = new FileBucket();
        fb.putEntry(e);
        Entry e1 = fb.getEntry();
    }

    public FileBucket() {
        try {
            this.path = Files.createTempFile("tmp", ".tmp");
            Files.deleteIfExists(path);
            Files.createFile(path);
            path.toFile().deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getFileSize() {
        try {
            return Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void putEntry(Entry entry) {
        try (OutputStream outputStream = Files.newOutputStream(path); ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Entry getEntry() {
        Entry entry = null;
        if (getFileSize() > 0) {
            try (InputStream inputStream = Files.newInputStream(path); ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                entry = (Entry) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return entry;
    }

    public void remove() {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
