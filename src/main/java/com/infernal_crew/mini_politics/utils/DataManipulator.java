package com.infernal_crew.mini_politics.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DataManipulator {
    public static <class_ extends Identifiable> Map<String, class_> toIdMap(Collection<class_> items) {
        Map<String, class_> map = new HashMap<>();
        for (class_ obj : items) {
            map.put(obj.getId(),obj);
        }
        return map;
    }
}
