package com.hugnew.sps.services;

import com.hugnew.core.service.IBaseService;
import com.hugnew.sps.dao.domain.PayMap;
import com.hugnew.sps.enums.PlatformType;

/**
 * 交易流水业务接口
 * Created by Martin on 2016/7/01.
 */
public interface IPayMapService extends IBaseService<PayMap> {

    /**
     * 支付通知更新交易记录
     * @param tempPayCode
     * @param msg
     * @param msg2
     * @param platformType
     * @param ssn
     * @param remark2
     * @return
     */
    PayMap updatePayMapByPayCode(String tempPayCode, String msg, String msg2, PlatformType platformType, String ssn, String remark2);

}
