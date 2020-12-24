package cn.krain.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author CC
 * @data 2020/7/27 - 21:15
 */
public class DataTimeUtil {

    /**
     * 获取当前系统时间
     * @return
     */
    public static String getSysTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentTime = sdf.format(date);
        return currentTime;
    }

    /**
     * 获取当前系统时间戳
     * @return
     */
    public static long getTimestamp(){
        return System.currentTimeMillis();
    }

}
