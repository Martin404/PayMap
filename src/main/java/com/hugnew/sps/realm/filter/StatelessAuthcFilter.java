package com.hugnew.sps.realm.filter;

import com.hugnew.core.common.exception.BusinessException;
import com.hugnew.core.util.DateUtils;
import com.hugnew.core.util.StringUtil;
import com.hugnew.sps.realm.token.StatelessAuthToken;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro无状态的web权限拦截器
 * Created by Martin on 2016/7/01.
 */
public class StatelessAuthcFilter extends AccessControlFilter {

    private static Logger logger = LoggerFactory.getLogger(StatelessAuthcFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //获取请求头的参数
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String userId=httpServletRequest.getHeader("userId");
        String authentication = httpServletRequest.getHeader("Authentication");
        String uri=httpServletRequest.getRequestURI();
        String queryString=httpServletRequest.getQueryString();
        String url="";
        if(queryString==null){
            url=uri;
        }else {
            url=uri+"?"+queryString;
        }
        String time = httpServletRequest.getHeader("Time");
        if (StringUtil.isEmpty(authentication) || StringUtil
                .isEmpty(url) || StringUtil.isEmpty(time)) {
            logger.warn("shiro获取验证失败，请求的参数有空值");
            throw new BusinessException("获取验证失败，请求的参数有空值");
        }
        //验证时间的合法性
        if (!this.isAccessTime(time)) {
            logger.warn("shiro获取验证失败，非法的请求时间");
            throw new BusinessException("获取验证失败，非法的请求时间");
        }
        //从字符串参数中获取各个参数值
        Map<String, String> parms = this.getParms(authentication, url, time);

        String username = parms.get("username");
        String clientDigest = parms.get("clientDigest");
        parms.remove("clientDigest");

        //验证随机数的合法性
        String randomKey = parms.get("randomKey");
        logger.info("随机数重复验证:{}",randomKey);

        //委托给realm验证digest的合法性
        StatelessAuthToken token = new StatelessAuthToken(username, parms, clientDigest,userId);
        try {
            logger.info("客户端的randomKey：{}，time:{},url:{},username:{},digest:{}",randomKey,time,url,username,clientDigest);
            getSubject(servletRequest, servletResponse).login(token);
        } catch (Exception e) {
            logger.warn("shiro获取验证失败，消息摘要错误");
            throw new BusinessException("获取验证失败，消息摘要错误");

        }
        return true;
    }

    /**
     * 根据客户端发来的参数来组合成加密参数
     *
     * @param authentication 客户端传来的加密信息
     * @param url            url
     * @param time           客户端时间
     * @return 加密参数Map parms
     */
    private Map<String, String> getParms(String authentication, String url, String time)  {
        String authentications[] = authentication.split(":");
        if(authentications.length<3){
            logger.warn("shiro验证失败，验证参数错误");
            throw new BusinessException("验证失败，验证参数错误");
        }
        String username = authentications[0];
        String randomData = authentications[1];
        String clientDigest = authentications[2];
        Map<String, String> maps = new HashMap<>();
        maps.put("username", username);
        maps.put("randomKey", randomData);
        maps.put("url", url);
        maps.put("time", time);
        maps.put("clientDigest", clientDigest);
        return maps;
    }

    /**
     * @param time 客户端传来的时间
     * @return 该时间是否为可通过的时间
     * @throws ParseException 格式转换异常
     */
    private boolean isAccessTime(String time) throws ParseException {
        long serverTimeStamp = DateUtils.getUnixTimestamp();
        long clientTimeStamp = DateUtils.getUnixTimestamp(time,DateUtils.standard);
        Long deadTime =600L;
        long timeStampTemp = Math.abs(serverTimeStamp - clientTimeStamp);
        if (timeStampTemp > deadTime) {
            return false;
        } else {
            return true;
        }
    }

}
