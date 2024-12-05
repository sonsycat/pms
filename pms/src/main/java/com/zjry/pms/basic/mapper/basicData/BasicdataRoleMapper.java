package com.zjry.pms.basic.mapper.basicData;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.basicData.BasicdataRole;

import java.util.List;

/**
 * 角色信息Mapper接口
 * 
 * @author 王超
 * @date 2021-07-20
 */
public interface BasicdataRoleMapper extends BaseMapper<BasicdataRole> {
    /**
     * 查询角色信息
     * 
     * @param roleId 角色信息ID
     * @return 角色信息
     */
    public BasicdataRole selectBasicdataRoleById(Long roleId);
    /**
     * 查询角色信息
     *
     * @param roleIds 角色信息
     * @return 结果
     */
    public List<BasicdataRole> selectBasicdataRoleByIds(Long[] roleIds);

}
