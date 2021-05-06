package com.planeter.w2auction.controller;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/5/5 17:35
 * @status dev
 */
import com.planeter.w2auction.common.enums.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

@RestControllerAdvice
public class GlobalExceptionHandling {
    /**
     * 处理shiro框架异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ShiroException.class)
    public ResponseData doHandleShiroException(
            ShiroException e) {
        ResponseData r = new ResponseData();
        if (e instanceof AuthorizationException) {
            r = new ResponseData(ExceptionMsg.NoSuchPermission);
        } else {
            e.printStackTrace();
        }
        return r;
    }
    /**
     * JPA异常处理
     */
    @ExceptionHandler(PersistenceException.class)
    public ResponseData doHandlePersistenceException(
            PersistenceException e) {
        ResponseData r = new ResponseData();
        if (e instanceof EntityNotFoundException) {
            r = new ResponseData(ExceptionMsg.NoSuchEntity);
        } else {
            e.printStackTrace();
        }
        return r;
    }

}