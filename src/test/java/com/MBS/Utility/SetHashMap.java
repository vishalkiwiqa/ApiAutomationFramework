package com.MBS.Utility;

import java.util.LinkedHashMap;
import java.util.Map;

public class SetHashMap {

    private static Map<String, String> map = new LinkedHashMap<String, String>();

    public static Map<String, String> getMap() {
        return map;
    }

}
