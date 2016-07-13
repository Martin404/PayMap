package com.hugnew.sps.controllers.front.pc.pay;

import com.hugnew.core.common.exception.BusinessException;
import com.hugnew.core.common.exception.SystemException;
import com.hugnew.core.common.model.json.AjaxResult;
import com.hugnew.sps.controllers.base.BaseController;
import com.hugnew.sps.dto.PayRequestParam;
import com.hugnew.sps.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 支付请求相关接口
 * Created by Martin on 2016/7/01.
 */
@Controller("pc_payRequestController")
@RequestMapping("/pc/payRequest")
public class PayRequestController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(PayRequestController.class);
    @Autowired
    private IPayRouteService payRouteService;

    /**
     * 组装支付请求报文
     * @param payRequestParam
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    @ResponseBody
    @RequestMapping(value = "/getPayParams", method = RequestMethod.POST)
    public AjaxResult getPayParams(@RequestBody PayRequestParam payRequestParam) throws BusinessException, SystemException {
        return AjaxResult.getOK(payRouteService.getPayRetMap(payRequestParam));
    }

}
