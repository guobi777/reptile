package com.gpp.api.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

public class OKHttpUtils {

    public static String client(String url) throws Exception {
        return client(url, null);
    }

    public static String client(String url, Integer seconds) throws Exception {
        if (null != seconds) {
            TimeUnit.SECONDS.sleep(seconds);
        }
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(url);
        builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3610.2 Safari/537.36");
        builder.addHeader("x-forwarded-for", IPUtils.getRandomIp());
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        String retText = response.body().string();
        return retText;

    }
}
