package com.moowei.map.utils;

/**
 * @author moowei
 * date 2018/11/9 0009 11:04
 * @version V1.0
 */
public class ColumnFilter {
    /**
     * 将字段中包含hive默认的分隔符过滤掉。包括\n 和 "\u0001"
     * @param columnStr 需要过滤的字段
     * @return 过滤后的字段值
     */
    public static String columnFilterForHive(String columnStr){
        if("".equals(columnStr)){
            columnStr = "\\N";
        } else{
            columnStr = columnStr.replaceAll("(\\r\\n|\\r|\\n|\\n\\r)","");
            columnStr = columnStr.replace("\u0001","");
        }
        return columnStr;
    }
}