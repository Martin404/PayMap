package com.hugnew.sps.services.impls;

import com.hugnew.core.common.exception.BusinessException;
import com.hugnew.core.dao.IBaseMapper;
import com.hugnew.core.service.impl.BaseService;
import com.hugnew.core.util.DateUtils;
import com.hugnew.sps.dao.PayMapMapper;
import com.hugnew.sps.dao.domain.PayMap;
import com.hugnew.sps.enums.PlatformType;
import com.hugnew.sps.services.IPayMapService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 交易流水业务
 * Created by Martin on 2016/7/01.
 */
@Service
public class PayMapService extends BaseService<PayMap> implements IPayMapService {

    private static Logger logger = LoggerFactory.getLogger(PayMapService.class);
    @Autowired
    private PayMapMapper payMapMapper;

    @Override
    public IBaseMapper<PayMap> getBaseMapper() {
        return payMapMapper;
    }

    @Override
    public PayMap updatePayMapByPayCode(String tempPayCode, String msg, String msg2, PlatformType platformType, String ssn, String remark2) {
        PayMap param = new PayMap();
        param.setTempPayCode(tempPayCode);
        param.setPlatform(platformType.value());
        List<PayMap> payMaps = payMapMapper.select(param);
        Assert.notNull(payMaps);
        if (payMaps != null && !payMaps.isEmpty()) {
            PayMap payMap = payMaps.get(0);
            payMap.setRetMsg(msg);
            payMap.setRetMsg2(msg2);
            payMap.setSwiftNumber(ssn);
            payMap.setIsPaid("1");
            payMap.setNotifyTime(DateUtils.getUnixTimestamp());
            if (StringUtils.isNotBlank(remark2)) {
                payMap.setRemark2(remark2);
            }
            payMapMapper.updateByPrimaryKeySelective(payMap);
            return payMap;
        } else {
            throw new BusinessException("数据库异常，交易记录查询为Null");
        }
    }

}
