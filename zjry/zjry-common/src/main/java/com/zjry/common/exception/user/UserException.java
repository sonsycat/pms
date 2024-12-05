package com.zjry.common.exception.user;

import com.zjry.common.exception.BaseException;

/**
 * 用户信息异常类
 * 
 * @author zjry
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
