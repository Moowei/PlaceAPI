package com.moowei.map.utils;


import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * @author mooeri
 * date 2018/11/8 0008 10:51
 * @version V1.0
 */
public class FileUtils {
    /**
     * 字符缓冲写入文件，注意在源文件基础上添加内容，不会删除文件原有数据。
     *
     * @param stringBuffer 要写入的数据
     * @param file     要写入的文件（需要确保已经存在）
     * @param encoding     写文件时的文件编码
     */
    public static void bufferedWriteAndFileWriter(StringBuffer stringBuffer, File file, String encoding) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),encoding));
            bufferedWriter.write(stringBuffer.toString());
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bufferedWriter);
        }
    }
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines(String fileName) {

    }
}