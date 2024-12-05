package com.zjry.framework.handler;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.exception.CustomException;
import com.zjry.common.utils.SecurityUtils;

/**
 * 注册mybatis-plus填充handler处理类
 *
 * @author lf
 * @date 2021年06月24日 08:51
 */
@Component
public class MybatisFillHandler implements MetaObjectHandler {

    private static final String CREATE_BY = "createBy";
    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_BY = "updateBy";
    private static final String UPDATE_TIME = "updateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter(CREATE_BY)) {
        	// 没有登录状态的赋值匿名用户
            String username = null;
            try {
            	username = SecurityUtils.getUsername();
            } catch (CustomException customException) {
            	username = new String("anonymous");
            }
            this.setFieldValByName(CREATE_BY, username, metaObject);
        }
        if (metaObject.hasGetter(CREATE_TIME)) {
            this.setFieldValByName(CREATE_TIME, new Date(), metaObject);
        }
        if (metaObject.hasGetter(UPDATE_TIME)) {
            this.setFieldValByName(UPDATE_TIME, new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter(UPDATE_BY)) {
        	String username = null;
            try {
            	username = SecurityUtils.getUsername();
            } catch (CustomException customException) {
            	username = new String("anonymous");
            }
            this.setFieldValByName(UPDATE_BY, username, metaObject);
        }
        if (metaObject.hasGetter(UPDATE_TIME)) {
            this.setFieldValByName(UPDATE_TIME, new Date(), metaObject);
        }
    }
}
