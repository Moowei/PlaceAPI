
package com.moowei.map.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Description: Http url地址解析类
 *
 * @author moowei
 * date 2018/7/11 10:32
 * @version V1.0
 */
public class HttpUrlAnalysis {

    public static JSONObject analysisHttpUrl(String url)
            throws IOException {
        URL urlObj = new URL(url);
        URLConnection con = urlObj.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) con;
        // http 头部
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Content-Type", "text/html;charset=UTF-8");
        // 建立连接，将数据写入内存
        DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
        out.flush();
        out.close();
        BufferedReader in;
        StringBuilder result = new StringBuilder();
        // 将数据发送给服务端，并获取返回结果
        in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        //开始解析json
        JSONObject jsonResultObj = JSON.parseObject(result.toString());
        return jsonResultObj;

    }

    public static void main(String[] args) throws IOException {
    }
}
