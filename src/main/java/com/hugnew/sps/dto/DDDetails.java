package com.hugnew.sps.dto;

import java.io.Serializable;

/**
 * 数据字典中关于id和name的json串处理DTO
 * Created by Martin on 2016/7/01.
 */
public class DDDetails implements Serializable {

    /**
     * The Id.
     */
    private String id;

    /**
     * The Name.
     */
    private String name;

    /**
     * 获取 id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置 id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}
