package com.satoken.test;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author wyh
 * @date 2021/11/12 18:56
 */
@Component
public class MyPermissionServiceImpl implements StpInterface {

    private static final String ADMIN_ID = "root";

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String loginIdStr = (String) loginId;
        if (Objects.equals(ADMIN_ID, loginIdStr)) {
            return adminPermissionAndRole();
        }
        return Arrays.asList("user-add", "user-remove", "user-update");
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String loginIdStr = (String) loginId;
        if (Objects.equals(ADMIN_ID, loginIdStr)) {
            return adminPermissionAndRole();
        }
        return Arrays.asList("a", "b", "c");
    }


    private List<String> adminPermissionAndRole() {
        return Collections.singletonList("*");
    }
}
