package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {

        StorageStrategy strategy = new HashMapStorageStrategy();
        testStrategy(strategy,30000 );
        strategy = new OurHashMapStorageStrategy();
        testStrategy(strategy,30000 );
        strategy = new OurHashBiMapStorageStrategy();
        testStrategy(strategy,30000 );
        strategy = new HashBiMapStorageStrategy();
        testStrategy(strategy,30000 );
        strategy = new DualHashBidiMapStorageStrategy();
        testStrategy(strategy,30000 );
        strategy = new FileStorageStrategy();
        testStrategy(strategy,500 );
    }


    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> set = new HashSet<>();
        for (String s : strings) {
            set.add(shortener.getId(s));
        }
        return set;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> set = new HashSet<>();
        for (Long key : keys)
            set.add(shortener.getString(key));
        return set;
    }

    public static void testStrategy(StorageStrategy strategy, long elemensNumber) {
        Helper.printMessage(strategy.getClass().getSimpleName());
        Set<String> set = new HashSet<>();
        for (int i = 0; i < elemensNumber; i++) {
            set.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(strategy);
        Set<Long> ids = new HashSet<>();
        Set<String> strings = new HashSet<>();
        Date date = new Date();
        ids = getIds(shortener, set);
        Long time = new Date().getTime() - date.getTime();
        Helper.printMessage(time.toString());
        date = new Date();
        strings = getStrings(shortener, ids);
        time = new Date().getTime() - date.getTime();
        Helper.printMessage(time.toString());

        //System.out.println(set);

        if (strings.equals(set))
            Helper.printMessage("Тест пройден.");
        else
            Helper.printMessage("Тест не пройден.");
    }

}
