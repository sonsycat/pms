package com.zjry.pms.bonus.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.bonus.domain.PmsAccountDept;
import org.apache.ibatis.annotations.Param;

/**
 * 核算科室设置Mapper接口
 * 
 * @author zjry
 * @date 2022-09-29
 */
public interface PmsAccountDeptMapper extends BaseMapper<PmsAccountDept> {
    /**
     * 查询核算科室设置
     * 
     * @param id 核算科室设置ID
     * @return 核算科室设置
     */
    public PmsAccountDept selectPmsAccountDeptById(Long id);

    /**
     * 查询核算科室设置列表
     * 
     * @param pmsAccountDept 核算科室设置
     * @return 核算科室设置集合
     */
    public List<PmsAccountDept> selectPmsAccountDeptList(PmsAccountDept pmsAccountDept);

    /**
     * 新增核算科室设置
     * 
     * @param pmsAccountDept 核算科室设置
     * @return 结果
     */
    public int insertPmsAccountDept(PmsAccountDept pmsAccountDept);

    /**
     * 修改核算科室设置
     * 
     * @param pmsAccountDept 核算科室设置
     * @return 结果
     */
    public int updatePmsAccountDept(PmsAccountDept pmsAccountDept);

    /**
     * 删除核算科室设置
     * 
     * @param id 核算科室设置ID
     * @return 结果
     */
    public int deletePmsAccountDeptById(Long id);

    /**
     * 批量删除核算科室设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsAccountDeptByIds(Long[] ids);

    /**
     * 单表查询列表数据
     * @param pmsAccountDept
     * @return
     */
    List<PmsAccountDept> queryList(PmsAccountDept pmsAccountDept);

    /**
     * 复制数据
     * @param pmsAccountDept
     */
    void copyData(PmsAccountDept pmsAccountDept);

    /**
     * 通过ID批量删除
     * @param nowDate
     * @param ids
     * @return
     */
    int deleteByIds(@Param("nowDate") String nowDate, @Param("array") Long[] ids);

    /**
     * 删除自身和下级
     * @param nowDate
     * @param deptRoomId
     * @return
     */
    int deleteSelfAndSub(@Param("nowDate") String nowDate, @Param("deptRoomCode") String deptRoomCode);
}
