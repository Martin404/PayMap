package com.hugnew.sps.services.pay.util.app.wechat.util;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Martin on 2016/7/01.
 */
public class HttpUtils {
    private static final HttpClient client = new HttpClient();

    public static void buildPost(String url, String xml, String custId, String tranCode, String tranSid) throws Exception {

        org.apache.http.client.HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("cust_id", custId));

        params.add(new BasicNameValuePair("tran_code", tranCode));

        params.add(new BasicNameValuePair("tran_sid", tranSid));

        params.add(new BasicNameValuePair("ccbParam", xml));

        httpPost.setEntity(new UrlEncodedFormEntity(params, "gbk"));


        HttpResponse response = httpClient.execute(httpPost);
        System.err.println(response.getStatusLine());
        System.err.println(response.getEntity().getContentLength());
        HttpEntity entity = response.getEntity();

        System.err.println(entity.getContent().toString());
        System.err.println(entity.toString());
        System.err.println(readInstream(entity.getContent(), "GBK"));


    }


    public static String buildPostV2(String url, String xml, String custId, String tranCode, String tranSid) throws Exception {

        org.apache.http.client.HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        HttpResponse response = httpClient.execute(httpPost);
        System.err.println(response.getStatusLine());
        System.err.println(response.getEntity().getContentLength());
        HttpEntity entity = response.getEntity();

        System.err.println(entity.getContent().toString());
        System.err.println(entity.toString());
        return readInstream(entity.getContent(), null);


    }

    public static String icbcPost(String url, String method, String version,
                                  String format, String app_key, String timestamp, String req_sid,
                                  String auth_code, String sign, String req_data, String sign_type) throws Exception {

        DefaultHttpClient client = new DefaultHttpClient();

        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(HttpUtils.class.getClassLoader().getResourceAsStream("ready.keystore"), "123456".toCharArray());
        SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
        socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme sch = new Scheme("https", 443, socketFactory);
        client.getConnectionManager().getSchemeRegistry().register(sch);

        HttpPost post = new HttpPost(url);

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("method", method));
        params.add(new BasicNameValuePair("version", version));
        params.add(new BasicNameValuePair("format", format));
        params.add(new BasicNameValuePair("app_key", app_key));
        params.add(new BasicNameValuePair("timestamp", timestamp));
        params.add(new BasicNameValuePair("req_sid", req_sid));
        params.add(new BasicNameValuePair("auth_code", auth_code));
        params.add(new BasicNameValuePair("sign", sign));
        params.add(new BasicNameValuePair("req_data", req_data));

        HttpEntity entity = new UrlEncodedFormEntity(params, "utf-8");
        post.setEntity(entity);

        HttpResponse response = client.execute(post);

        InputStream is = response.getEntity().getContent();

        String result = readInstream(is, null);

        return result;
    }


    public static String buildPostCCB(String url, String xml, String custId, String tranCode, String tranSid) throws Exception {

        org.apache.http.client.HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringBuffer buffer = new StringBuffer();
        buffer.append(" <?xml version=\"1.0\" encoding=\"GBK\" ?> ")
                .append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">")
                .append("<soapenv:Body>")
                .append("<service xmlns=\"http://index.interfaces.ccb.com/\">")
                .append("<arg0 xmlns=\"\">")
                .append("<ccbParam>").append(xml).append("</ccbParam>")
                .append("<cust_id>").append(custId).append("</cust_id> ")
                .append("<tran_code>").append(tranCode).append("</tran_code>")
                .append("<tran_sid>").append(tranSid).append("</tran_sid> ")
                .append("</arg0>")
                .append("</service>")
                .append("</soapenv:Body>")
                .append("</soapenv:Envelope>");
        System.err.println(buffer.toString());
        StringEntity entityRequest = new StringEntity(buffer.toString(), "GBK");
        httpPost.setEntity(entityRequest);
        httpPost.setHeader("Content-Type", "text/xml; charset=GBK");
        HttpResponse response = httpClient.execute(httpPost);
        System.err.println(response.getStatusLine());
        System.err.println(response.getEntity().getContentLength());
        HttpEntity entity = response.getEntity();

        System.err.println(entity.getContent().toString());
        System.err.println(entity.toString());
        return readInstream(entity.getContent(), "GBK");


    }

    public static String postRequest(String urlStr, String xml, String custId, String tranCode, String tranSid) throws IOException {

        URL url = null;
        HttpURLConnection http = null;
        WritableByteChannel channel = null;

        try {
            url = new URL(urlStr);
            http = (HttpURLConnection) url.openConnection();
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setUseCaches(false);
            http.setConnectTimeout(50000);//设置连接超时
            //如果在建立连接之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
            http.setReadTimeout(50000);//设置读取超时
            //如果在数据可读取之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.connect();
            String param = "&tran_code=" + tranCode
                    + "&cust_id=" + custId
                    + "&tran_sid=" + tranSid
                    + "&ccbParam=" + xml;
            channel = Channels.newChannel(http.getOutputStream());
            ByteBuffer byteBuffer = ByteBuffer.wrap(param.getBytes("GBK"));
            byteBuffer.flip();
            channel.write(byteBuffer);
            System.err.println("readInstream(http.getInputStream()) " + readInstream(http.getInputStream(), "GBK"));
            System.err.println("http.getContentLength() :" + http.getContentLength());
            if (http.getResponseCode() == 200) {

                String back = readInstream(http.getInputStream(), "GBK");
                System.err.println("AesUtils.defaultEncrypt(back)   :" + AesUtils.defaultEncrypt(back));
                return back;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("err");
        } finally {
            if (http != null) http.disconnect();
            if (channel.isOpen()) {
                channel.close();
            }
        }
        return null;
    }

    public static String readInstream(InputStream in, String encode) throws IOException {

        //编码默认为utf-8；
        if (StringUtils.isBlank(encode))
            encode = "utf-8";


        List<Byte> byteList = new LinkedList<>();

        try (ReadableByteChannel channel = Channels.newChannel(in)) {
            Byte[] bytes = new Byte[0];
            ByteBuffer byteBuffer = ByteBuffer.allocate(9600);
            while (channel.read(byteBuffer) != -1) {
                byteBuffer.flip();//为读取做好准备

                while (byteBuffer.hasRemaining()) {
                    //builder.append((char)byteBuffer.get());
                    byteList.add(byteBuffer.get());
                }
                byteBuffer.clear();//为下一次写入做好准备
            }
        }
        Byte[] bytes = byteList.toArray(new Byte[byteList.size()]);
        byte[] bytes1 = new byte[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            bytes1[i] = bytes[i].byteValue();
        }
        return new String(bytes1, encode);
    }

    public static String post(String url, NameValuePair... params) throws IOException {
        try {
            // 编码参数
            List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // 请求参数
            for (NameValuePair p : params) {
                formparams.add(p);
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
                    "UTF-8");
            // 创建POST请求
            HttpPost request = new HttpPost(url);
            request.setEntity(entity);
            // 发送请求
            org.apache.http.client.HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException("请求失败");
            }
            HttpEntity resEntity = response.getEntity();
            return (resEntity == null) ? null : EntityUtils.toString(resEntity, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException("连接失败", e);
        }

    }



}
