package com.gpp.api.util;

import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils4Share {

    public static int getSecond(String time) {
        try {
            String[] my = StringUtils.split(time, ":");
            int hour = Integer.parseInt(my[0]);
            int min = Integer.parseInt(my[1]);
            int sec = Integer.parseInt(my[2]);
            return hour * 3600 + min * 60 + sec;
        } catch (Exception e) {
            try {
                return Integer.parseInt(time);
            } catch (Exception ee) {
                return 0;
            }
        }
    }


    /**
     * 得到网页中图片的地址
     */
    public static Set<String> getImgStr(String htmlStr) {
        Set<String> pics = new HashSet<>();
        String img;
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                String imgUrl = m.group(1);
                if (StringUtils.isNotBlank(imgUrl)) {
                    pics.add(imgUrl);
                }
            }
        }
        return pics;
    }
}
