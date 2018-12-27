package com.moowei.map.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author moowei
 * date 2018/10/26 0026 14:39
 * @version V1.0
 */
public class ReadProperties {
    public static Properties getProperties(Class objClass,String fileName) {
        Properties propertie;
        String filePath = objClass.getProtectionDomain().getCodeSource().getLocation().getPath();
        File file = new File(filePath);
        String dirPath = file.getParentFile().getAbsolutePath();
        propertie = System.getProperties();
        try {
            propertie.load(new FileInputStream(new File(dirPath+"/"+fileName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return propertie;
    }
}