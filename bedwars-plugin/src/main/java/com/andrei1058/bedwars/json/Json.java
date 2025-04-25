package com.andrei1058.bedwars.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Json {
    public static void printAsJson(Object obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);
        for (String line : json.split("\n")) {
            System.out.println(line);
        }
    }
}
