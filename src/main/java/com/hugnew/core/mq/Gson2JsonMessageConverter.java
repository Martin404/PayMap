package com.hugnew.core.mq;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractJsonMessageConverter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.google.gson.Gson;

/**
 * MQ MSG序列化JSON转换器
 * Created by Martin on 2016/7/01.
 */
public class Gson2JsonMessageConverter extends AbstractJsonMessageConverter {

    private static Logger logger = LoggerFactory.getLogger(Gson2JsonMessageConverter.class);
    private static ClassMapper classMapper = new DefaultClassMapper();
    private static Gson gson = new Gson();

    public Gson2JsonMessageConverter() {
        super();
    }

    @Override
    protected Message createMessage(Object object,
                                    MessageProperties messageProperties) {
        byte[] bytes = null;
        try {
            String jsonString = gson.toJson(object);
            bytes = jsonString.getBytes(getDefaultCharset());
        } catch (IOException e) {
            throw new MessageConversionException(
                    "Failed to convert Message content", e);
        }
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(getDefaultCharset());
        if (bytes != null) {
            messageProperties.setContentLength(bytes.length);
        }
        classMapper.fromClass(object.getClass(), messageProperties);
        return new Message(bytes, messageProperties);
    }

    @Override
    public Object fromMessage(Message message)
            throws MessageConversionException {
        Object content = null;
        MessageProperties properties = message.getMessageProperties();
        if (properties != null) {
            String contentType = properties.getContentType();
            if (contentType != null && contentType.contains("json")) {
                String encoding = properties.getContentEncoding();
                if (encoding == null) {
                    encoding = getDefaultCharset();
                }
                try {
                    Class<?> targetClass = getClassMapper().toClass(
                            message.getMessageProperties());
                    content = convertBytesToObject(message.getBody(),
                            encoding, targetClass);
                } catch (IOException e) {
                    throw new MessageConversionException(
                            "Failed to convert Message content", e);
                }
            } else {
                logger.warn("Could not convert incoming message with content-type {}", contentType);
            }
        }
        if (content == null) {
            content = message.getBody();
        }
        return content;
    }

    private Object convertBytesToObject(byte[] body, String encoding,
                                        Class<?> clazz) throws UnsupportedEncodingException {
        String contentAsString = new String(body, encoding);
        return gson.fromJson(contentAsString, clazz);
    }

    @Override
    public ClassMapper getClassMapper() {
        return new DefaultClassMapper();
    }
}  