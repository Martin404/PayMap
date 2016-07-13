package com.hugnew.core.web.system.listener;

import com.unionpay.acp.sdk.SDKConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;

/**
 * 系统初始化监听器,在系统启动时运行,进行一些初始化工作
 * Created by Martin on 2016/7/01.
 */
public class InitListener implements javax.servlet.ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(InitListener.class);
    public static ApplicationContext context;

    public void contextDestroyed(ServletContextEvent arg0) {
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext());
        //加载银联upop配置文件
        SDKConfig.getConfig().loadPropertiesFromSrc();
        String proPath = servletContextEvent.getServletContext().getRealPath("/");
        SDKConfig config = SDKConfig.getConfig();
        config.setSignCertDir(proPath + config.getSignCertDir());
        config.setSignCertPath(proPath + config.getSignCertPath());
        config.setValidateCertDir(proPath + config.getValidateCertDir());
        //缓存初始化忽略
    }

}
