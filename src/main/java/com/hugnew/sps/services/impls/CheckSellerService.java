package com.hugnew.sps.services.impls;

import com.hugnew.sps.services.ICacheService;
import com.hugnew.sps.services.ICheckSellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 商家校验业务
 * Created by Martin on 2016/7/01.
 */
@Service
public class CheckSellerService implements ICheckSellerService {
    @Autowired
    private ICacheService cacheService;

    @Override
    public boolean isUseGlobalPay(String sellerCode) {
        String value = cacheService.getDictEntryByKey("seller:classification", "国际支付宝支付商家");
        return getDictionaryList(value, sellerCode);
    }

    private Boolean getDictionaryList(String value, String sellerCode) {
        Boolean result = false;
        if (value == null || value.isEmpty()) {
            return result;
        }
        List<String> stringList = Arrays.asList(value.split("[,，]"));
        if (stringList == null || stringList.isEmpty()) {
            return result;
        }
        if (stringList.contains(sellerCode)) {
            result = true;
        }
        return result;
    }
}
