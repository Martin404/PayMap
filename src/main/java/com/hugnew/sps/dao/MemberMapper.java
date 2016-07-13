package com.hugnew.sps.dao;

import com.hugnew.core.dao.IBaseMapper;
import com.hugnew.sps.dao.domain.Member;

/**
 * Created by Martin on 2016/7/01.
 */
public interface MemberMapper extends IBaseMapper<Member> {
    public Member findByUsername(String username);
}