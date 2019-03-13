package com.gpp.api.util;

import java.text.DecimalFormat;

/**
 * 处理数据的工具类
 * 公用的处理数据的方法写在这里
 * Created by shw on 2018/5/4 0004.
 */
public class DataHandleUtil {

    /**
     * 占比计算保留小数的位数方法
     * 转成百分数
     * 当前数除以总数
     *
     * @param num1 ,num2  num1/num2
     * @return rate  保留2位小数的
     */
    public static String division(int num1, int num2) {
        String rate = "0.00";
        //定义格式化起始位数
        String format = "0.00";
        if (num2 != 0 && num1 != 0) {
            DecimalFormat dec = new DecimalFormat(format);
            rate = dec.format((double) num1 / num2 * 100);
            while (true) {
                if (rate.equals(format)) {
                    format = format + "0";
                    DecimalFormat dec1 = new DecimalFormat(format);
                    rate = dec1.format((double) num1 / num2 * 100);
                } else {
                    break;
                }
            }
        } else if (num1 != 0 && num2 == 0) {
            rate = "100";
        }
        return rate;
    }


}
