package com.satoken.test;

import cn.dev33.satoken.exception.DisableLoginException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 全局异常拦截（拦截项目中的所有异常）
    @ExceptionHandler
    public String handlerException(Exception e, HttpServletRequest request, HttpServletResponse response) {

        // 打印堆栈，以供调试
        log.error("exception handler ->", e);

        // 不同异常返回不同状态码
        String message;
        if (e instanceof NotRoleException) {
            // 如果是角色异常
            NotRoleException ee = (NotRoleException) e;
            message = "无此角色：" + ee.getRole();
        } else if (e instanceof NotPermissionException) {
            // 如果是权限异常
            NotPermissionException ee = (NotPermissionException) e;
            message = "无此权限：" + ee.getCode();
        } else if (e instanceof DisableLoginException) {
            // 如果是被封禁异常
            DisableLoginException ee = (DisableLoginException) e;
            message = "账号被封禁：" + ee.getDisableTime() + "秒后解封";
        } else {
            // 普通异常
            message = "系统异常";
        }

        // 返回给前端
        return message;
    }

    @ExceptionHandler(NotLoginException.class)
    public String handlerNotLoginException(NotLoginException nle, HttpServletRequest request, HttpServletResponse response) {

        // 打印堆栈，以供调试
        log.error("exception handler ->", nle);

        // 判断场景值，定制化异常信息
        String message = "";
        if (nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未提供token";
        } else if (nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token无效";
        } else if (nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token已过期";
        } else if (nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "token已被顶下线";
        } else if (nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "token已被踢下线";
        } else {
            message = "当前会话未登录";
        }

        // 返回给前端
        return message;
    }

}