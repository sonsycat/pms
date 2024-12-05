package com.zjry.pms.workload.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.workload.mapper.PmsWorkloadHisMapper;
import com.zjry.pms.workload.domain.PmsWorkloadHis;
import com.zjry.pms.workload.service.IPmsWorkloadHisService;

/**
 * 工作量-HIS收费项目Service业务层处理
 *
 * @author zjry
 * @date 2022-09-29
 */
@Service
public class PmsWorkloadHisServiceImpl extends ServiceImpl<PmsWorkloadHisMapper, PmsWorkloadHis> implements IPmsWorkloadHisService {
    @Autowired
    private PmsWorkloadHisMapper pmsWorkloadHisMapper;

    /**
     * 查询工作量-HIS收费项目
     *
     * @param id 工作量-HIS收费项目ID
     * @return 工作量-HIS收费项目
     */
    @Override
    public PmsWorkloadHis selectPmsWorkloadHisById(Long id) {
        return pmsWorkloadHisMapper.selectPmsWorkloadHisById(id);
    }

    /**
     * 查询工作量-HIS收费项目列表
     *
     * @param pmsWorkloadHis 工作量-HIS收费项目
     * @return 工作量-HIS收费项目
     */
    @Override
    public List<PmsWorkloadHis> selectPmsWorkloadHisList(PmsWorkloadHis pmsWorkloadHis) {
        return pmsWorkloadHisMapper.selectPmsWorkloadHisList(pmsWorkloadHis);
    }

    /**
     * 新增工作量-HIS收费项目
     *
     * @param pmsWorkloadHis 工作量-HIS收费项目
     * @return 结果
     */
    @Override
    public int insertPmsWorkloadHis(PmsWorkloadHis pmsWorkloadHis) {
    	pmsWorkloadHis.preInsert();
        return pmsWorkloadHisMapper.insertPmsWorkloadHis(pmsWorkloadHis);
    }

    /**
     * 修改工作量-HIS收费项目
     *
     * @param pmsWorkloadHis 工作量-HIS收费项目
     * @return 结果
     */
    @Override
    public int updatePmsWorkloadHis(PmsWorkloadHis pmsWorkloadHis) {
    	pmsWorkloadHis.preUpdate();
        return pmsWorkloadHisMapper.updatePmsWorkloadHis(pmsWorkloadHis);
    }

    /**
     * 批量删除工作量-HIS收费项目
     *
     * @param ids 需要删除的工作量-HIS收费项目ID
     * @return 结果
     */
    @Override
    public int deletePmsWorkloadHisByIds(Long[] ids) {
        return pmsWorkloadHisMapper.deletePmsWorkloadHisByIds(ids);
    }

    /**
     * 删除工作量-HIS收费项目信息
     *
     * @param id 工作量-HIS收费项目ID
     * @return 结果
     */
    @Override
    public int deletePmsWorkloadHisById(Long id) {
        return pmsWorkloadHisMapper.deletePmsWorkloadHisById(id);
    }
}
