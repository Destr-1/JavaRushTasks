package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {
    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
        Date date = new Date();
        ids.clear();
        for (String string : strings) {
            ids.add(shortener.getId(string));
        }
        return new Date().getTime() - date.getTime();
    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Date date = new Date();
        strings.clear();
        for (Long id : ids) {
            strings.add(shortener.getString(id));
        }
        return new Date().getTime() - date.getTime();
    }

    @Test
    public void testHashMapStorage(){
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origString = new HashSet<>();
        Set<Long> origIds = new HashSet<>();
        for(int i =0; i<10000; i++){
            origString.add(Helper.generateRandomString());
        }
        long time1 = getTimeToGetStrings(shortener1, origIds, origString);
        long time2 = getTimeToGetStrings(shortener2, origIds, origString);
        //System.out.println(time1+" " + time2);
        Assert.assertEquals(time1, time2, 30);

        time1 = getTimeToGetIds(shortener1, origString, origIds);
        time2 = getTimeToGetIds(shortener2, origString, origIds);

        Assert.assertTrue(time1 > time2);

    }

    
}
