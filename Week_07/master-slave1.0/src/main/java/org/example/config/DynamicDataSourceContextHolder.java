package org.example.config;

import java.util.ArrayList;
import java.util.List;

public class DynamicDataSourceContextHolder {


    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>() {
        /**
         * 将 master 数据源的 key 作为默认数据源的 key
         */
        @Override
        protected String initialValue() {
            return "master";
        }
    };

    /**
     * 数据源的 key 集合，用于切换时判断数据源是否存在
     */
    public static List<Object> dataSourceKeys = new ArrayList<>();

    /**
     * 设置 DataSource
     *
     * @param key
     */
    public static void setDataSourceKey(String key) {
        contextHolder.set(key);
    }

    /**
     * 获取当前 DataSrouce
     *
     * @return key
     */
    public static String getDataSourceKey() {
        return contextHolder.get();
    }

    /**
     * 清除当前key
     */
    public static void clear() {
        contextHolder.remove();
    }

    /**
     * Check if give DataSource is in current DataSource list
     *
     * @param key the key
     * @return boolean boolean
     */
    public static boolean containDataSourceKey(String key) {
        return dataSourceKeys.contains(key);
    }
}