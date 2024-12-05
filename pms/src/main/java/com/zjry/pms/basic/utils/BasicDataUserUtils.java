package com.zjry.pms.basic.utils;

import com.zjry.pms.basic.domain.basicData.BasicdataUser;
import com.zjry.pms.basic.service.IBasicdataUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * @author Administrator
 */
@Component
public class BasicDataUserUtils {
    @Autowired
    private IBasicdataUserService service;

    private static IBasicdataUserService basicdataUserService;

    @PostConstruct
    public void init() {
        basicdataUserService = service;
    }

    public static BasicdataUser getUserWithRelations(){
        BasicdataUser user = basicdataUserService.initLoginMsgById(null,null);
        return user;
    }
}
