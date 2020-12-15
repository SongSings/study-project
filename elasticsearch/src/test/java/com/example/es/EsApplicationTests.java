package com.example.es;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.es.config.ElasticSearchUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class EsApplicationTests {

    @Autowired
    ElasticsearchRestTemplate template;
    @Autowired
    ElasticSearchUtil esClient;

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) throws IOException {

        String uname = "river8866";
        String upass = "river870317";

        //1
        String token = login(uname, upass);

//        for (int i = 0; i < 1; i++) {
//            new Thread(){
//                @SneakyThrows
//                @Override
//                public void run() {
//                    System.out.println(Thread.currentThread().getName() + " ---------开始" );
//                    boolean flag =true;
//                    //统计连接的时间
//                    long beginTime = System.currentTimeMillis();
//                    //2
//                    ProxyBean proxy = getProxyIp(uname, token);
//                    while (flag) {
//
//                        if(proxy==null) continue;
//                        //3
//                        try {
//                            usedProxyIp(proxy.proxy_ip, proxy.proxy_port);
//                        } catch (IOException e) {
//                            long endTime = System.currentTimeMillis();
//                            System.out.println(Thread.currentThread().getName() + "total Time (ms)= " + (endTime - beginTime));
//                            //flag = false;
//                            sleep(3000l);
//                        }
//                    }
//                }
//            }.start();
//        }
        for (int i = 0; i < 1; i++) {
            System.out.println(Thread.currentThread().getName() + " ---------开始" );
            boolean flag =true;
            //统计连接的时间
            long beginTime = System.currentTimeMillis();
            //2
            ProxyBean proxy = getProxyIp(uname, token);
            while (flag) {

                if(proxy==null) continue;
                //3
                try {
                    usedProxyIp(proxy.proxy_ip, proxy.proxy_port);
                } catch (IOException | KeyManagementException e) {
                    long endTime = System.currentTimeMillis();
                    System.out.println(Thread.currentThread().getName() + "total Time (ms)= " + (endTime - beginTime));
                    flag = false;
                }
            }
            System.out.println(i+" times = " + (System.currentTimeMillis()-beginTime));
        }




    }

    //步骤1通过登录api获取token
    public static String login(String uname, String upass) {

        String params = "user=" + uname + "&password=" + upass;
        String reqUrl = "http://dvapi.doveip.com/cmapi.php?rq=login";
        try {

            HttpURLConnection httpURLConnection = getUrlConnection(reqUrl);

            OutputStream out = httpURLConnection.getOutputStream();
            out.write(params.getBytes());
            out.flush();
            out.close();

            String msg = "";
            int code = httpURLConnection.getResponseCode();
            System.out.println("code:" + code);
            if (code == 200) {

                msg = getConnectionResult(httpURLConnection);

                System.out.println("msg:" + msg);
                //{"errno":200,"msg":"Success","data":{"token":"OXhRbHd2MHVvaWZKckd2Vml0SVArUT09"}}

                JSONObject jo =  JSONObject.parseObject(msg);
                int errno = jo.getIntValue("errno");
                jo = jo.getJSONObject("data");
                String token = jo.getString("token");

                System.out.println("errno:" + errno);
                System.out.println("token:" + token);

                return token;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    //步骤2 通过api获取代理ip
    public static ProxyBean getProxyIp(String uname, String token) {

        //timeout：有效时间(可选,单位分钟，默认10，最低5，最高20)
        String params = "geo=mx&user=" + uname + "&token=" + token + "&timeout=20";
        String reqUrl = "http://dvapi.doveip.com/cmapi.php?rq=distribute";
        try {

            HttpURLConnection httpURLConnection = getUrlConnection(reqUrl);

            OutputStream out = httpURLConnection.getOutputStream();
            out.write(params.getBytes());
            out.flush();
            out.close();

            String msg = "";
            int code = httpURLConnection.getResponseCode();
            System.out.println("code:" + code);
            if (code == 200) {
                msg = getConnectionResult(httpURLConnection);
            }
            //{"errno":200,"msg":"Success","data":{"geo":"mx","ip":"47.253.12.97","port":40004,"d_ip":"189.203.105.247"}}
            System.out.println("msg:" + msg);
            JSONObject jo = JSONObject.parseObject(msg);
            int errno = jo.getIntValue("errno");
            if(errno!=200){
                return null;
            }

            jo = jo.getJSONObject("data");
            String geo = jo.getString("geo");
            String ip = jo.getString("ip");
            int port = jo.getInteger("port");
            String d_ip = jo.getString("d_ip");

//            System.out.println("errno:" + errno);
//            System.out.println("geo:" + geo);
//            System.out.println("ip:" + ip);
//            System.out.println("port:" + port);
//            System.out.println("d_ip:" + d_ip);

            ProxyBean proxy = new ProxyBean();
            proxy.proxy_ip = ip;
            proxy.proxy_port = port;

            return proxy;

        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("ioexception line 164  "+e.getMessage() );
        }catch (JSONException e) {
            System.out.println("exception line 166  "+e.getMessage() );
        }

        return null;
    }


    //步骤3使用代理ip
    public static void usedProxyIp(String proxyIp, int proxyPort) throws IOException, KeyManagementException {

        try {
            TrustManager[] trustAllCerts = {new TrustAllTrustManager()};
            SSLContext sc = SSLContext.getInstance("SSL");
            SSLSessionContext sslsc = sc.getServerSessionContext();
            sslsc.setSessionTimeout(0);
            sc.init(null, trustAllCerts, null);

            HostnameVerifier hv = new HostnameVerifier() {
                @Override
                public boolean verify(String urlHostName, SSLSession session) {
                    return true;
                }
            };
            //  激活主机认证
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            InetSocketAddress inetSocketAddress = new InetSocketAddress(proxyIp, proxyPort);
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, inetSocketAddress);
            //URL url = new URL("https://faq.whatsapp.com/general/chats/how-to-use-click-to-chat/?lang=en");
            URL url = new URL("https://www.google.com");
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection(proxy);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            httpURLConnection.setConnectTimeout(10000);

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);


            httpURLConnection.connect();

            int code = httpURLConnection.getResponseCode();
            System.out.println("code:" + code);
            if (code == 200) {
                String content = getConnectionResult(httpURLConnection);

                //System.out.println("content:" + content);
                System.out.println("request :google 200");
            }
//            else {
//                usedProxyIp(proxyIp,proxyPort);
//            }
        } catch (MalformedURLException e) {
            //e.printStackTrace();
            System.out.println("exception 206" +e.getMessage());
        } catch (IOException e) {
            System.out.println("exception  line 208"+e.getMessage());
            //e.printStackTrace();
            throw e;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("exception  line 212"+e.getMessage());
            //e.printStackTrace();
        } catch (KeyManagementException e) {
            //e.printStackTrace();
            System.out.println("exception  line 216"+e.getMessage());
            throw e;
        }

    }

    public static class ProxyBean{

        public String proxy_ip;
        public int proxy_port;
    }


    public static String getConnectionResult(HttpURLConnection httpURLConnection) throws IOException {

        InputStream inStream = httpURLConnection.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[512];
        int length = -1;
        String msg;
        while ((length = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, length);
        }

        outStream.close();
        inStream.close();
        msg = outStream.toString();

        return msg;
    }

    public static HttpURLConnection getUrlConnection(String reqUrl) throws IOException {

        URL url = new URL(reqUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setConnectTimeout(3000);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.connect();
        return httpURLConnection;
    }

    public static class TrustAllTrustManager implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

    }




}
