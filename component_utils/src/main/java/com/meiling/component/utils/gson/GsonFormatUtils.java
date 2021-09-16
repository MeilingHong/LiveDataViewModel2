package com.meiling.component.utils.gson;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonFormatUtils {
    /*
     使用List类型进行转换后，直接使用时可能会抛出该异常
      【java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to】
     */
//    @Deprecated
//    public static <T> T geObjectFromJSON(String str, Type type) {
//        if (!TextUtils.isEmpty(str)) {
//            return Gsons.getInstance().fromJson(str, type);
//        }
//        return null;
//    }
//
//    public static <T> Type getType(T t) {
//        return new TypeToken<T>() {
//        }.getType();
//    }
//
//    public static <T> Type getListType(T t) {
//        return new TypeToken<List<T>>() {
//        }.getType();
//    }

    /**
     * 将String转换成单个实体对象
     */
    public static <T> T getObjectJson(String str, Class<T> cls) {
        if (!TextUtils.isEmpty(str)) {
            return Gsons.getInstance().fromJson(str, cls);
        }
        return null;
    }

	/**
	 * 将String转换成列表实体对象
	 */
    public static <T> List<T> getObjectList(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(Gsons.getInstance().fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
