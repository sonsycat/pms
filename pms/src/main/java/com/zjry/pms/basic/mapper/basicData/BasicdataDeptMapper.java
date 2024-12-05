package com.zjry.pms.basic.mapper.basicData;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.zjry.pms.basic.domain.basicData.BasicdataDept;
import com.zjry.pms.basic.domain.basicData.BasicdataDeptVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 单位Mapper接口
 * 
 * @author cyq
 * @date 2021-07-15
 */
public interface BasicdataDeptMapper extends BaseMapper<BasicdataDept> {
    /**
     * 查询单位
     * 
     * @param deptId 单位ID
     * @return 单位
     */
    public BasicdataDeptVo selectBasicdataDeptById(Long deptId);

    /**
     * 查询单位列表
     * 
     * @param basicdataDeptVo 单位
     * @return 单位集合
     */
    public List<BasicdataDeptVo> selectBasicdataDeptList(BasicdataDeptVo basicdataDeptVo);

    /**
     * 通过角色id查询单位
     * @param roleId
     * @param deptCheckStrictly
     * @return
     */
    public List<Long> selectDeptListByRoleId(@Param("roleId") Long roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);

    /**
     * 新增单位
     * 
     * @param basicdataDept 单位
     * @return 结果
     */
    public int insertBasicdataDept(BasicdataDept basicdataDept);

    /**
     * 修改单位
     * 
     * @param basicdataDept 单位
     * @return 结果
     */
    public int updateBasicdataDept(BasicdataDept basicdataDept);

    /**
     * 删除单位
     * 
     * @param deptId 单位ID
     * @return 结果
     */
    public int deleteBasicdataDeptById(Long deptId);

    /**
     * 批量删除单位
     * 
     * @param deptIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBasicdataDeptByIds(Long[] deptIds);

    /**
     * 根据单位ID，查询单位列表
     *
     * @param basicdataDept 单位ID
     * @return 结果
     */
    List<BasicdataDeptVo> getListByDeptId(BasicdataDept basicdataDept);

    /**
     * 查询单位数量
     *
     * @param basicdataDept 单位信息
     * @return 单位数量
     */
    int getSize(BasicdataDept basicdataDept);

    /**
     * 查询单位管理数据
     *
     * @param dept 单位信息
     * @return 单位信息集合
     */
    public List<BasicdataDeptVo> selectDeptList(BasicdataDeptVo dept);

    /**
     * 查询根节点的机构
     * @return
     */
    public List<Long> selectRootDeptParentId();

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    public BasicdataDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);
    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    int selectNormalChildrenDeptById(Long deptId);

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int hasChildByDeptId(Long deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int checkDeptExistUser(Long deptId);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);


}
