package com.satchain.service;

import com.satchain.bean.model.Userinfo;
import com.satchain.dao.UserinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class LoginService {

    @Autowired
    private UserinfoMapper userinfoMapper;

    public String getPassword(String loginName) {
        Userinfo user = userinfoMapper.queryUserInfoByName(loginName);
        Assert.notNull(user,"没有此用户！");
        return user.getPassword();
    }
}
