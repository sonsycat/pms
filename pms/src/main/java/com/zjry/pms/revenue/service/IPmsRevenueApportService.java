package com.zjry.pms.revenue.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.revenue.domain.PmsApportInfo;
import com.zjry.pms.revenue.domain.PmsRevenueApport;
import com.zjry.pms.revenue.domain.PmsRevenueNum;

/**
 * 收入生成Service接口
 * 
 * @author mk
 * @date 2022-06-28
 */
public interface IPmsRevenueApportService extends IService<PmsRevenueApport> {
    /**
     * 查询收入生成
     * 
     * @param id 收入生成ID
     * @return 收入生成
     */
    public PmsRevenueApport selectPmsRevenueApportById(Long id);

    /**
     * 查询收入生成列表
     * 
     * @param pmsRevenueApport 收入生成
     * @return 收入生成集合
     */
    public List<PmsRevenueApport> selectPmsRevenueApportList(PmsRevenueApport pmsRevenueApport);

    /**
     * 新增收入生成
     * 
     * @param pmsRevenueApport 收入生成
     * @return 结果
     */
    public int insertPmsRevenueApport(PmsRevenueApport pmsRevenueApport);

    /**
     * 修改收入生成
     * 
     * @param pmsRevenueApport 收入生成
     * @return 结果
     */
    public int updatePmsRevenueApport(PmsRevenueApport pmsRevenueApport);

    /**
     * 批量删除收入生成
     * 
     * @param ids 需要删除的收入生成ID
     * @return 结果
     */
    public int deletePmsRevenueApportByIds(Long[] ids);

    /**
     * 删除收入生成信息
     * 
     * @param id 收入生成ID
     * @return 结果
     */
    public int deletePmsRevenueApportById(Long id);

    /**
     * 删除收入生成信息
     *
     * @param list 收入录入list
     *   deptType 科室类型 门诊 住院
     *    srlx  收入类型 正常  附加
     * @return 结果
     */

    void generate1(List<PmsRevenueNum> list,String deptType,String srlx) throws Exception;

    public void deleteByState();
    public void updateByState();

    List<PmsApportInfo> beforeBreak(PmsRevenueApport pmsRevenueApport);

    List<PmsApportInfo> afterBreak(PmsRevenueApport pmsRevenueApport);
}
