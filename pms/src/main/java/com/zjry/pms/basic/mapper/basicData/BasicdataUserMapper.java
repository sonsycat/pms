package com.zjry.pms.basic.mapper.basicData;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.basicData.BasicdataUser;


/**
 * 用户信息Mapper接口
 * 
 * @author cyq
 * @date 2021-07-19
 */
public interface BasicdataUserMapper extends BaseMapper<BasicdataUser> {


    /**
     * 查询用户信息 查单表，防止重复数据
     *
     * @param userId 用户信息ID
     * @return 用户信息
     */
    public BasicdataUser selectBasicdataUserByIdForOne(Long userId);
    /**
     * 查询用户信息
     *
     * @param userId 用户信息ID
     * @return 用户信息
     */
    public BasicdataUser selectBasicdataUserById(Long userId);

}
