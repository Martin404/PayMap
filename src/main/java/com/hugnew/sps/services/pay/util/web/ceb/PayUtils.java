package com.hugnew.sps.services.pay.util.web.ceb;

import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bill on 2015/11/25.
 */
public class PayUtils {
    public static String cebPost(String url, NameValuePair... params) throws IOException {
        try {
            // 编码参数
            List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // 请求参数
            for (NameValuePair p : params) {
                formparams.add(p);
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
                    "UTF-8");
            entity.setContentType("application/x-www-form-urlencoded");
            // 创建POST请求
            HttpPost request = new HttpPost(url);

            request.setHeader("Content-Type","application/x-www-form-urlencoded");
            request.setEntity(entity);
            // 发送请求
            ProtocolSocketFactory fcty = new SpsSecureProtocolSocketFactory();
            Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException("请求失败");
            }
            String result = readInputStream(response);
            return result;
        } catch (UnsupportedEncodingException e) {
            return null;
        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException("连接失败", e);
        }

    }

    public static Map<String, String> genMapCEB(String plain) {
        Pattern pattern = Pattern.compile("([\\w]+)=(.*)");
        Map<String, String> toRet = new HashMap<>();
        if (StringUtils.isNotBlank(plain)) {
            String[] temp = plain.split("~\\|~");
            if (temp.length > 0) {
                for (int i = 0; i < temp.length; i++) {
                    Matcher matcher = pattern.matcher(temp[i]);
                    while (matcher.find()) {
                        toRet.put(matcher.group(1), matcher.group(2));
                    }
                }
            }
        }

        return toRet;
    }
    public static String readInputStream(HttpResponse in) throws IOException {
        List<Byte> byteList = new LinkedList<>();
        try( ReadableByteChannel channel = Channels.newChannel(in.getEntity().getContent())) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(9600);
            while (channel.read(byteBuffer) != -1){
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()){
                    byteList.add(byteBuffer.get());
                }
                byteBuffer.clear();
            }
        }
        Byte[] bytes = byteList.toArray(new Byte[byteList.size()]);
        byte[] bytes1 = new byte[bytes.length];
        for (int i=0;i<bytes.length;i++){
            bytes1[i] = bytes[i];
        }
        return new String(bytes1,"utf-8");
    }
}
