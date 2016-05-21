package io.github.maxwell_nc.imageloader.conn.impl.web;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * URL连接管理
 */
public class ConnController {

    /**
     * HTTP类型地址
     */
    public static final int TYPE_HTTP = 1;

    /**
     * HTTPS类型地址
     */
    public static final int TYPE_HTTPS = 2;

    /**
     * 连接超时时间
     */
    private static final int CONNECT_TIMEOUT = 10000;

    /**
     * 读取超时时间
     */
    private static final int READ_TIMEOUT = 15000;

    /**
     * 自动判断HTTP还是HTTPS并获得HttpURLConnection
     *
     * @param url 地址
     * @return 如果地址非法则返回null
     */
    public static HttpURLConnection getURLConnection(String url)
            throws Exception {

        int type = 0;

        // HTTPS类型
        if (url.startsWith("https://")) {
            type = TYPE_HTTPS;
        }

        // HTTP类型
        else if (url.startsWith("http://")) {
            type = TYPE_HTTP;
        }

        HttpURLConnection connection = null;

        switch (type) {
            default:// 非法地址
                return null;

            case TYPE_HTTP:// 创建HttpURLConnection

                connection = (HttpURLConnection) new URL(url).openConnection();

                break;
            case TYPE_HTTPS:// 创建HttpsURLConnection

                // 信任所有HTTPS连接
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                    public boolean verify(String string, SSLSession ssls) {
                        return true;
                    }
                });

                connection = (HttpsURLConnection) new URL(url).openConnection();
                break;
        }

        // 连接超时时间
        connection.setConnectTimeout(CONNECT_TIMEOUT);

        // 读取超时时间
        connection.setReadTimeout(READ_TIMEOUT);

        // User-Agent
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android ;) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");

        return connection;

    }

}
