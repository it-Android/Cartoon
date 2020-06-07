package com.jq.cartoon1.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/1/29 17:23
 **/
public class SpUtils {
    private final static String SP_NAME = "setting";

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    public static boolean putString(Context context, String key, String value) {
        return getEditor(context).putString(key, value).commit();
    }

    public static boolean putInt(Context context, String key, int value) {
        return getEditor(context).putInt(key, value).commit();
    }

    public static boolean putLong(Context context, String key, long value) {
        return getEditor(context).putLong(key, value).commit();
    }


    public static boolean putBoolean(Context context, String key, boolean value) {
        return getEditor(context).putBoolean(key, value).commit();
    }

    public static String getString(Context context, String key) {
        return getSharedPreferences(context).getString(key, "");
    }

    public static int getInt(Context context, String key) {
        return getSharedPreferences(context).getInt(key, 0);
    }

    public static Long getLong(Context context, String key) {
        return getSharedPreferences(context).getLong(key, 0);
    }

    public static boolean getBoolead(Context context, String key) {
        return getSharedPreferences(context).getBoolean(key, false);
    }


}
