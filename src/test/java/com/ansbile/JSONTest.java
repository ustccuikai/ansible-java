package com.ansbile;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuikai on 2021/5/18.
 */
public class JSONTest {

    public static void main(String[] args) {
        Map<String, Map<String, String>> map = new HashMap<>();
        map.put("mysql", new HashMap<>());
        map.put("alert", new HashMap<>());
        map.get("mysql").put("version", "5.7");
        map.get("mysql").put("path", "/data/");
        map.get("alert").put("name", "name_value");
        map.get("alert").put("freq", "1h");
        String json = new GsonBuilder().create().toJson(map);
        System.out.println(json);


        Type type = new TypeToken<Map<String, Map<String, String>>>() {
        }.getType();
        Map<String, String> paramMap = new GsonBuilder().create().fromJson(json, type);
        System.out.println(paramMap);

    }
}
