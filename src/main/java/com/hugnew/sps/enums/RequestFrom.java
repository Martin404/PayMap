package com.hugnew.sps.enums;

import com.hugnew.core.common.exception.BusinessException;

/**
 * 请求来源
 * Created by Martin on 2016/7/01.
 */
public enum RequestFrom {

    PC(1, "PC"),
    WAP(2, "WAP"),
    WEIXIN(3, "微信"),
    ANDROID(4, "安卓"),
    IOS(5, "苹果");

    private int _id;

    private String _name;

    RequestFrom(int id, String name){
        _id = id;
        _name = name;
    }

    public int getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }

    public static RequestFrom getById(int id) throws BusinessException {
        RequestFrom result = null;

        for(RequestFrom requestFrom : RequestFrom.values()){
            if(requestFrom._id == id){
                result = requestFrom;
            }
        }
        if(result == null){
            throw new BusinessException("系统中未包含此请求来源类型");
        }else {
            return result;
        }

    }

    public boolean isApp(){
        if(_id==IOS._id||_id==ANDROID._id){
            return true;
        }
        return false;
    }
    public boolean isIOS(){
        if(_id==IOS._id){
            return true;
        }
        return false;
    }
    public boolean isMobile(){
        return _id != PC._id;
    }

    public int transToImageSizeType(){
        return this.equals(PC) ? 0 : 1;
    }

}
