package com.example.student.utils;

import java.util.Collection;

public class EmptyUtils {
    /**
     * 判断对象是否为null
     * @param object 要检查的对象
     * @return 如果对象为null则返回true，否则返回false
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * 判断字符串是否为null或空
     * @param str 要检查的字符串
     * @return 如果字符串为null或空则返回true，否则返回false
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    public static boolean isNullOrEmpty(Long l) {
        return l == null;
    }
    /**
     * 判断集合是否为null或空
     * @param collection 要检查的集合
     * @return 如果集合为null或空则返回true，否则返回false
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
