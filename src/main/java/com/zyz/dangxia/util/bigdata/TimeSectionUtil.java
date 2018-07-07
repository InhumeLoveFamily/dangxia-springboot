package com.zyz.dangxia.util.bigdata;

import org.joda.time.DateTime;

public class TimeSectionUtil {

    public static int getT(long time) {
        DateTime dateTime = new DateTime(time);
        int i = dateTime.getHourOfDay();
        if (6 <= i && i < 10) {
            return 1;
        } else if (10 <= i && i < 14) {
            return 2;
        } else if (14 <= i && i < 18) {
            return 3;
        } else if (18 <= i && i < 22) {
            return 4;
        } else return 5;

    }
}
