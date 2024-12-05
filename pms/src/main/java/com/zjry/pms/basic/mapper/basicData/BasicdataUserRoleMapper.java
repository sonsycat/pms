package com.zjry.pms.basic.mapper.basicData;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.basicData.BasicdataUserRole;

import java.util.List;

/**
 * 用户和角色关联Mapper接口
 * 
 * @author 王超
 * @date 2021-07-22
 */
public interface BasicdataUserRoleMapper extends BaseMapper<BasicdataUserRole> {

    /**
     * 查询用户和角色关联列表
     * 
     * @param basicdataUserRole 用户和角色关联
     * @return 用户和角色关联集合
     */
    public List<BasicdataUserRole> selectBasicdataUserRoleList(BasicdataUserRole basicdataUserRole);
    /**
     * 修改用户角色对应关系use_flag
     * @param basicdataUserRole
     * @return
     */
    public int updateBasicdataUserRoleUseFlag(BasicdataUserRole basicdataUserRole);

}
