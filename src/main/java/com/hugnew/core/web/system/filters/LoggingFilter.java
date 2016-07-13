package com.hugnew.core.web.system.filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * request response log记录过滤器
 * Created by Martin on 2016/7/01.
 */
public class LoggingFilter extends OncePerRequestFilter {

    protected static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private static final String REQUEST_PREFIX = "Request: ";
    private static final String RESPONSE_PREFIX = "Response: ";
    private AtomicLong id = new AtomicLong(1);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        if (logger.isDebugEnabled()) {
            long requestId = id.incrementAndGet();
            request = new RequestWrapper(requestId, request);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            if (logger.isDebugEnabled()) {
                logRequest(request);
            }
        }
    }

    private void logRequest(final HttpServletRequest request) {
        StringBuilder msg = new StringBuilder();
        msg.append(REQUEST_PREFIX);
        if (request instanceof RequestWrapper) {
            msg.append("request id=").append(((RequestWrapper) request).getId()).append("; ");
        }
        msg.append("user id=").append(request.getHeader("userId")).append("; ");
        msg.append("from=").append(request.getHeader("from")).append("; ");

        if ("application/json".equalsIgnoreCase(request.getContentType())) {
            RequestWrapper requestWrapper = (RequestWrapper) request;
            try {
                String charEncoding = requestWrapper.getCharacterEncoding() != null ? requestWrapper.getCharacterEncoding() :
                        "UTF-8";
                msg.append("params=").append(new String(requestWrapper.toByteArray(), charEncoding)).append("; ");
            } catch (UnsupportedEncodingException e) {
                logger.warn("Failed to parse request payload", e);
            }
        } else {
            msg.append("params=").append(getPostParamsStr(request));
        }
        logger.debug(msg.toString());
    }

    private boolean isMultipart(final HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().startsWith("multipart/form-data");
    }

    private void logResponse(final ResponseWrapper response) {
        StringBuilder msg = new StringBuilder();
        msg.append(RESPONSE_PREFIX);
        msg.append("request id=").append((response.getId()));
        try {
            msg.append("; payload=").append(new String(response.toByteArray(), response.getCharacterEncoding()));
        } catch (UnsupportedEncodingException e) {
            logger.warn("Failed to parse response payload", e);
        }
        logger.debug(msg.toString());
    }

    private String getPostParamsStr(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            sb.append(name).append("=").append(Arrays.toString(request.getParameterValues(name))).append("&");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
