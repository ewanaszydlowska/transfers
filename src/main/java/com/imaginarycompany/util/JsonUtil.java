package com.imaginarycompany.util;

import com.google.gson.Gson;

public class JsonUtil<T> {

    private final Gson gson = new Gson();
    private Class<T> type;

    public String toJson(T obj) {
        return gson.toJson(obj);
    }

    public T toObject(String json) {
        return gson.fromJson(json, type);
    }
}
