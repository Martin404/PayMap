package com.hugnew.sps.realm.token;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

/**
 * Created by Liujishuai on 2015/6/3.
 */
public class StatelessAuthToken implements AuthenticationToken {
    private String username;
    private Map<String, String> params;
    private String clientDigest;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public StatelessAuthToken(String username, Map<String, String> params, String clientDigest,String userId)
    {

        this.username=username;
        this.params=params;
        this.clientDigest=clientDigest;
        this.userId=userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientDigest() {
        return clientDigest;
    }

    public void setClientDigest(String clientDigest) {
        this.clientDigest = clientDigest;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return clientDigest;
    }
}
