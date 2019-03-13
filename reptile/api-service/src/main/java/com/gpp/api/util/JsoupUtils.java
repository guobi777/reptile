package com.gpp.api.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class JsoupUtils {
    public static Connection getConnection(String url) {

        return getConnection(url, null);
    }

    public static Connection getConnection(String url, Integer time) {
        Connection connection = Jsoup.connect(url);
        if (null == time) {
            connection.timeout(10000);
        } else {
            connection.timeout(time);
        }
        connection.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3610.2 Safari/537.36");
        return connection;
    }

}
