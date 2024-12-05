package com.zjry.pms.bonus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.bonus.domain.PmsAccountDept;

/**
 * 核算科室设置Service接口
 * 
 * @author zjry
 * @date 2022-09-29
 */
public interface IPmsAccountDeptService extends IService<PmsAccountDept> {

    public static String ROOT_PARENT_CODE = "0";

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
     * 批量删除核算科室设置
     * 
     * @param ids 需要删除的核算科室设置ID
     * @return 结果
     */
    public int deletePmsAccountDeptByIds(Long[] ids);

    /**
     * 删除核算科室设置信息
     * 
     * @param id 核算科室设置ID
     * @return 结果
     */
    public int deletePmsAccountDeptById(Long id);

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

    int saveData(PmsAccountDept pmsAccountDept);
}
