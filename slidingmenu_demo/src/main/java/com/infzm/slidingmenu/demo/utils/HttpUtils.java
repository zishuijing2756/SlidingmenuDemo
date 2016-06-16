package com.infzm.slidingmenu.demo.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpUtils {

    public static String getHttpRequest(String httpUrl) {
        StringBuffer result = new StringBuffer();
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(httpUrl);
            conn = (HttpURLConnection) url.openConnection();

            if (conn == null) {
                return null;
            }
            // 读取超时时间 毫秒
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            /** 设置请求的编码方式 */
            conn.setRequestProperty("Charset", "utf-8");
            conn.setRequestProperty("apikey",  "3ae0c260ff3aeb61b434fc9d21785e85");
            conn.setDoInput(true);
            conn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String lines;
                while ((lines = reader.readLine()) != null) {
                    result.append(lines);
                }
            }
            reader.close();

        } catch (Exception e) {
        	Log.e("HttpUtils", e.getMessage());
        } finally {
            conn.disconnect();
        }
        return result.toString();
    }
}
