package com.satoken.test;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author wyh
 * @date 2021/11/12 15:46
 */
@RestController
public class LoginController {

    @GetMapping("/login")
    public String login(String username, String password) {
        if (!Objects.equals("123", username) || !Objects.equals("456", password)) {
            return "登录失败";
        }
        StpUtil.login(1);
        return "登录成功";
    }

    @GetMapping("/logout")
    public String logout() {
        StpUtil.logout(1);
        return "登出成功";
    }

    @GetMapping("/status")
    public Boolean status() {
        return StpUtil.isLogin();
    }

    @GetMapping("/token-info")
    @SaCheckPermission("user-cat")
    public SaTokenInfo tokenInfo() {
        return StpUtil.getTokenInfo();
    }
}
