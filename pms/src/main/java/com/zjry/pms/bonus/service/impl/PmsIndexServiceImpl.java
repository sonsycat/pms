package com.zjry.pms.bonus.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.common.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.bonus.mapper.PmsIndexMapper;
import com.zjry.pms.bonus.domain.PmsIndex;
import com.zjry.pms.bonus.service.IPmsIndexService;

/**
 * 指标配置维护Service业务层处理
 * 
 * @author zjry
 * @date 2022-10-09
 */
@Service
public class PmsIndexServiceImpl extends ServiceImpl<PmsIndexMapper, PmsIndex> implements IPmsIndexService {
    @Autowired
    private PmsIndexMapper pmsIndexMapper;

    /**
     * 查询指标配置维护
     * 
     * @param id 指标配置维护ID
     * @return 指标配置维护
     */
    @Override
    public PmsIndex selectPmsIndexById(Long id) {
        return pmsIndexMapper.selectPmsIndexById(id);
    }

    /**
     * 查询指标配置维护列表
     * 
     * @param pmsIndex 指标配置维护
     * @return 指标配置维护
     */
    @Override
    public List<PmsIndex> selectPmsIndexList(PmsIndex pmsIndex) {
        return pmsIndexMapper.selectPmsIndexList(pmsIndex);
    }

    /**
     * 新增指标配置维护
     * 
     * @param pmsIndex 指标配置维护
     * @return 结果
     */
    @Override
    public int insertPmsIndex(PmsIndex pmsIndex) {
    	pmsIndex.preInsert();
        pmsIndex.setCompCode(UserUtils.getSysUser().getDeptId());
        return pmsIndexMapper.insertPmsIndex(pmsIndex);
    }

    /**
     * 修改指标配置维护
     * 
     * @param pmsIndex 指标配置维护
     * @return 结果
     */
    @Override
    public int updatePmsIndex(PmsIndex pmsIndex) {
    	pmsIndex.preUpdate();
        return pmsIndexMapper.updatePmsIndex(pmsIndex);
    }

    /**
     * 批量删除指标配置维护
     * 
     * @param ids 需要删除的指标配置维护ID
     * @return 结果
     */
    @Override
    public int deletePmsIndexByIds(Long[] ids) {
        return pmsIndexMapper.deletePmsIndexByIds(ids);
    }

    /**
     * 删除指标配置维护信息
     * 
     * @param id 指标配置维护ID
     * @return 结果
     */
    @Override
    public int deletePmsIndexById(Long id) {
        return pmsIndexMapper.deletePmsIndexById(id);
    }
}
