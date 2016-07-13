package com.hugnew.sps.realm;

import com.hugnew.core.common.exception.BusinessException;
import com.hugnew.sps.dao.MemberMapper;
import com.hugnew.sps.dao.domain.Member;
import com.hugnew.sps.realm.token.HmacSHA256Utils;
import com.hugnew.sps.realm.token.StatelessAuthToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 登录用户的验证和授权
 * Created by Martin on 2016/7/01.
 */
public class StatelessAuthRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(StatelessAuthRealm.class);
    @Autowired
    public MemberMapper memberMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持StatelessToken类型的Token
        return token instanceof StatelessAuthToken;
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        Member user = memberMapper.findByUsername(username);
        if (user != null) {
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            return authorizationInfo;
        } else {
            logger.info("授权失败");
            throw new IncorrectCredentialsException();
        }
    }

    /**
     * 验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        StatelessAuthToken statelessToken = (StatelessAuthToken) token;
        String userId = statelessToken.getUserId();
        String username=statelessToken.getUsername();
        String key = null;
        try {
            key = this.getKey(userId,username);
        } catch (Exception e) {
            logger.info("获取加密key失败{}",e);
            throw new BusinessException("获取加密key失败{}",e);
        }
        //在服务器端生成客户端参数消息摘要
        String serverDigest = HmacSHA256Utils.digest(key, statelessToken.getParams());
        logger.info("服务器端的randomKey：{}，time:{},url:{},username:{},digest:{}",statelessToken.getParams().get("randomKey"),
                statelessToken.getParams().get("time"),statelessToken.getParams().get("url"),
                statelessToken.getParams().get("username"),serverDigest);
        //然后进行客户端消息摘要和服务器端消息摘要的匹配
        return new SimpleAuthenticationInfo(
                username,
                serverDigest,
                getName());
    }

    /**
     * 根据用户名获取密码
     * @param username 用户名
     * @return  密码
     */
    private String getKey(String userId,String username){
        // 访客模式，密码为visit$13password经过MD5加密后的字符串
        return "abc";
    }

}
