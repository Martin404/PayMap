package com.hugnew.sps.controllers.base;

import com.hugnew.core.common.exception.BusinessException;
import com.hugnew.core.util.StringUtil;
import com.hugnew.sps.dto.MobileInfo;
import com.hugnew.sps.enums.RequestFrom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Martin on 2016/7/01.
 */
public abstract class BaseController {

    private Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 获取用户ID，用户ID可能为NULL,需自行判断
     * @param request
     * @return
     */
    protected Long getUserId(HttpServletRequest request) {
        String sId = request.getHeader("userId");
        if (!StringUtil.isEmpty(sId)) {
            try {
                Long userId = Long.parseLong(sId);
                return userId;
            } catch (NumberFormatException e) {
                logger.warn("请求头userId参数格式错误:{}", sId);
            }
        }
        return null;
    }

    /**
     * 获取用户ID,当userId为空的时候抛出异常
     * @param request
     * @return
     * @throws BusinessException 用户ID不能为空
     */
    protected Long getNotNullUserId(HttpServletRequest request) throws BusinessException {
        Long userId = getUserId(request);
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        return userId;
    }

    /**
     * 获取请求来源类型
     * @param request
     * @return
     * @throws BusinessException
     */
    protected RequestFrom getRequestFrom(HttpServletRequest request) throws BusinessException {
        String from = request.getHeader("from");
        if (StringUtil.isEmpty(from)) {
            throw new BusinessException("请求头错误未包含来源字段");
        }
        try {
            int iFom = Integer.parseInt(from);
            return RequestFrom.getById(iFom);
        } catch (NumberFormatException e) {
            throw new BusinessException("请求头来源字段类型错误");
        }

    }

    /**
     * 获取移动端请求头信息
     * @param request
     * @return MobileInfo
     * @throws BusinessException
     */
    protected MobileInfo getMobileInfo(HttpServletRequest request) throws BusinessException {
        String appVersion = request.getHeader("appVersion");
        String systemVersion = request.getHeader("appSystemVersion");
        String deviceId = request.getHeader("appDeviceId");
        Integer width = null;
        Integer height = null;
        int night = 0;
        try {
            width = Integer.parseInt(request.getHeader("appDeviceWidth"));
            height = Integer.parseInt(request.getHeader("appDeviceHeight"));
            if (request.getHeader("nightMode") != null) {
                night = Integer.parseInt(request.getHeader("nightMode"));
            }
        } catch (NumberFormatException e) {
            throw new BusinessException("移动端请求头不符合约定");
        }
        if (StringUtil.isEmpty(appVersion) || width == null || height == null) {
            throw new BusinessException("移动端请求头不符合约定");
        }
        return new MobileInfo(appVersion, systemVersion, deviceId, width, height, night != 0);
    }

}
