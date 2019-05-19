package com.satchain.controller.web;

import com.satchain.commons.result.Result;
import com.satchain.commons.utils.TokenUtil;
import com.satchain.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录
 */
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录验证
     * @param loginName
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result getLogin(@RequestParam("username") String loginName, @RequestParam("password") String password, HttpServletRequest request){
        HttpSession session = request.getSession();
        String zhangxing = TokenUtil.genetateToken();
        session.setAttribute("SESSION_TOKEN",zhangxing);

        boolean login = false;
        //储存token
        String pwd = loginService.getPassword(loginName);
        Map<String,Object> map = new HashMap<String, Object>();
        if(pwd.equals(password)){
            login = true;
        }
        map.put("token",zhangxing);
        return Result.success(map);
    }
}
