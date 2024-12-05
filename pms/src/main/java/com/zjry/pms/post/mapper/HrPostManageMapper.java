package com.zjry.pms.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.post.domain.HrPostManage;
import com.zjry.pms.post.domain.HrPostManageVo;

import java.util.List;

/**
 * 岗位管理Mapper接口
 * 
 * @author mk
 * @date 2021-07-23
 */
public interface HrPostManageMapper extends BaseMapper<HrPostManage> {
    /**
     * 查询岗位管理
     * 
     * @param id 岗位管理ID
     * @return 岗位管理
     */
    public HrPostManage selectHrPostManageById(Long id);

    /**
     * 查询岗位管理列表
     * 
     * @param hrPostManage 岗位管理
     * @return 岗位管理集合
     */
    public List<HrPostManage> selectHrPostManageList(HrPostManage hrPostManage);

    /**
     * 新增岗位管理
     * 
     * @param hrPostManage 岗位管理
     * @return 结果
     */
    public int insertHrPostManage(HrPostManage hrPostManage);

    /**
     * 修改岗位管理
     * 
     * @param hrPostManage 岗位管理
     * @return 结果
     */
    public int updateHrPostManage(HrPostManage hrPostManage);

    /**
     * 删除岗位管理
     * 
     * @param id 岗位管理ID
     * @return 结果
     */
    public int deleteHrPostManageById(Long id);

    /**
     * 批量删除岗位管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHrPostManageByIds(Long[] ids);

    /**
     * 查询所有岗位数据
     * @return 结果
     */
    List<HrPostManageVo> queryAllPostInfo();
}
