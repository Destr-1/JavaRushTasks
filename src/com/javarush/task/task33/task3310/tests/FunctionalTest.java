package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;

public class FunctionalTest {
    private String s1, s2, s3;
    private Long id1, id2, id3;
    private String actualString1, actualString2, actualString3;

    public void testStorage(Shortener shortener){
        s1 = "Text for shortener";
        s2 = "Another text for shortener";
        s3 = "Text for shortener";

        id1 = shortener.getId(s1);
        id2 = shortener.getId(s2);
        id3 = shortener.getId(s3);

        Assert.assertEquals(id1, id3);
        Assert.assertNotEquals(id1, id2);

        actualString1 = shortener.getString(id1);
        actualString2 = shortener.getString(id2);
        actualString3 = shortener.getString(id3);

        Assert.assertEquals(s1, actualString1);
        Assert.assertEquals(s2, actualString2);
        Assert.assertEquals(s3, actualString3);
    }

    @Test
    public void testHashMapStorageStrategy(){
        StorageStrategy strategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy(){
        StorageStrategy strategy = new OurHashMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testFileStorageStrategy(){
        StorageStrategy strategy = new FileStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testHashBiMapStorageStrategy(){
        StorageStrategy strategy = new HashBiMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testDualHashBidiMapStorageStrategy(){
        StorageStrategy strategy = new DualHashBidiMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashBiMapStorageStrategy(){
        StorageStrategy strategy = new OurHashBiMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }
}
