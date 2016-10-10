package com.cydai.cncx.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class GsonUtils<T> {

    public static <T>  T getGsonBean(String json ,Class<T> cls ){
        Gson gson = new Gson();
        T obj = gson.fromJson(json, cls);

        return obj;
    }

    public static <T>  List<T> getGsonListBean(String json ,Class<T> cls){
        Gson gson = new Gson();
        Type listType = new TypeToken<LinkedList<T>>(){}.getType();
        List<T> objs = gson.fromJson(json, listType);

        return objs;
    }
}