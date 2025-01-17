package com.castile.casspringbootweb.demos.sse.cbb;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * SSE访问客户端，用于获取远端接口的流式响应
 *
 * @author castile
 * @date 2025-01-17 21:23
 */
public class SSEClient {
    public static final int DEFAULT_TIMEOUT = 100000;

    /**
     * 获取sse输入流
     * @param url 访问url
     * @param param 请求参数
     * @param timeOutInMills 读取超时
     * @return 输入流
     */
    public static InputStream getSseInputStream(String url, JSONObject param, int timeOutInMills ) {
        HttpURLConnection sseConnection = null;
        try {
            sseConnection = getSseConnection(url, timeOutInMills);
            sendRequest(param, sseConnection);
            // 获取响应
            InputStream inputStream = sseConnection.getInputStream();
            return new BufferedInputStream(inputStream);
        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }

    private static void sendRequest(JSONObject param, HttpURLConnection sseConnection) throws IOException {
        byte[] writeBytes = param.toJSONString().getBytes();
        sseConnection.setRequestProperty("Content-Length", String.valueOf(writeBytes.length));
        DataOutputStream wr = new DataOutputStream(sseConnection.getOutputStream());
        wr.write(writeBytes);
        wr.flush();
        wr.close();
    }

    private static HttpURLConnection getSseConnection(String url, int timeOutInMills) throws IOException {
        URL urlPath = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) urlPath.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("charset", "utf-8");
        urlConnection.setRequestProperty("Connection", "keep-alive");
        urlConnection.setRequestProperty("accept", "text/event-stream");

        // 读过期时间
        urlConnection.setReadTimeout(timeOutInMills);
        return urlConnection;
    }

    public static void readStream(InputStream is, MsgHandler msgHandler) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String line = "";
            while ((line = reader.readLine()) != null) {
                if ("".equals(line)) {
                    continue;
                }
                msgHandler.handle(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 目前这里抛出的显式异常来自与用户手动关闭的连接，此时服务端与算法端的连接也捕获并关闭，无需存储
        } finally {
            // 服务器端主动关闭时，客户端手动关闭
            reader.close();
            is.close();
        }

    }
}
