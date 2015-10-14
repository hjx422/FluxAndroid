package com.example.fluxandroid.utils;

import java.lang.reflect.Field;

/**
 * @author hjx
 * @date 10/12/2015
 * @time 13:39
 * @description
 */
public class ReflcetUtil {

    /**
     *
     * 合并对象，把src对象的值合并进target
     * 使用时请确认需要合并的两个对象的field的定义一致
     * @param src 源对象
     * @param target 目标对象
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     */
    public static <S, T> void merge(S src, T target) {
        if(src == null || target == null) {
            return;
        }
        Field[] srcFields = src.getClass().getDeclaredFields();
        for(Field field : srcFields) {
            try {
                Field tf = target.getClass().getDeclaredField(field.getName());
                if(tf == null) {
                    throw new NoSuchFieldException("no such field: " + field.getName());
                }
                field.setAccessible(true);
                Object value = field.get(src);
                field.setAccessible(false);
                if (value != null) {
                    tf.setAccessible(true);
                    tf.set(target, value);
                    tf.setAccessible(false);
                }
            } catch (Exception e) {
                LogUtil.e("merge_objects", e.getMessage());
            }
        }
    }
}
