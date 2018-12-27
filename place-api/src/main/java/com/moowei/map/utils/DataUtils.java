package com.moowei.map.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author moowei
 * date 2018/11/8 0008 10:49
 * @version V1.0
 */
public class DataUtils {
    /**
     * 在给定日期的基础上进行小时加减计算，并以给定的日期格式返回，返回格式为String格式
     *
     * @param inputeDate             要计算的日期
     * @param simpleDateFormatOutput 返回的日期的格式
     * @param hoursAmount            需要计算的小时数
     * @return 按指定的日期对结果进行格式化并返回
     */
    public static String dateComputeForHourADD(Date inputeDate, SimpleDateFormat simpleDateFormatOutput, int hoursAmount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputeDate);
        calendar.add(Calendar.HOUR, hoursAmount);
        String outputStr = simpleDateFormatOutput.format(calendar.getTime());
        return outputStr;
    }
}