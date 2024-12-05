package com.zjry.pms.project.service.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.pms.project.domain.CostNumList;
import org.springframework.stereotype.Service;
import com.zjry.pms.project.mapper.CostProjectNumMapper;
import com.zjry.pms.project.domain.CostProjectNum;
import com.zjry.pms.project.service.ICostProjectNumService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 成本金额管理Service业务层处理
 * 
 * @author mk
 * @date 2022-05-18
 */
@Service
public class CostProjectNumServiceImpl extends ServiceImpl<CostProjectNumMapper, CostProjectNum> implements ICostProjectNumService {
    @Resource
    private CostProjectNumMapper costProjectNumMapper;

    /**
     * 查询成本金额管理
     * 
     * @param id 成本金额管理ID
     * @return 成本金额管理
     */
    @Override
    public CostProjectNum selectCostProjectNumById(Long id) {
        return costProjectNumMapper.selectCostProjectNumById(id);
    }

    /**
     * 查询成本金额管理列表
     * 
     * @param costProjectNum 成本金额管理
     * @return 成本金额管理
     */
    @Override
    public List<CostProjectNum> selectCostProjectNumList(CostProjectNum costProjectNum) {
        return costProjectNumMapper.selectCostProjectNumList(costProjectNum);
    }

    /**
     * 新增成本金额管理
     * 
     * @param costNumList 成本金额管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertCostProjectNum(CostNumList costNumList)throws Exception {
        List<CostProjectNum> list = costNumList.getCostNumList();
        if(list == null || list.size()==0){
            return "数据未录入";
        }
        costProjectNumMapper.deleteByCodeAndDate(costNumList.getDeptCode(),costNumList.getNowDate());
        for(CostProjectNum item : list){
            item.setDeptCode(costNumList.getDeptCode());
            item.setNowDate(costNumList.getNowDate());
            item.preInsert();
            item.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
            costProjectNumMapper.insertCostProjectNum(item);
        }
         return null;
    }

    /**
     * 修改成本金额管理
     * 
     * @param costProjectNum 成本金额管理
     * @return 结果
     */
    @Override
    public int updateCostProjectNum(CostProjectNum costProjectNum) {
    	costProjectNum.preUpdate();
        return costProjectNumMapper.updateCostProjectNum(costProjectNum);
    }

    /**
     * 批量删除成本金额管理
     * 
     * @param ids 需要删除的成本金额管理ID
     * @return 结果
     */
    @Override
    public int deleteCostProjectNumByIds(Long[] ids) {
        return costProjectNumMapper.deleteCostProjectNumByIds(ids);
    }

    /**
     * 删除成本金额管理信息
     * 
     * @param id 成本金额管理ID
     * @return 结果
     */
    @Override
    public int deleteCostProjectNumById(Long id) {
        return costProjectNumMapper.deleteCostProjectNumById(id);
    }

    @Override
    public List<Long> selectCostPNDept(CostProjectNum costProjectNum) {
        return costProjectNumMapper.selectCostPNDept(costProjectNum);
    }

    @Override
    public void importData(List<CostProjectNum> costProjectfies, Long compCode) {
        costProjectfies.stream().forEach(item->{
            item.setCompCode(compCode);
            item.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
            item.preInsert();
            costProjectNumMapper.insertCostProjectNum(item);
        });
    }

    @Override
    public int deleteByCodeAndDate(Long deptCode, Date nowDate) {
        return costProjectNumMapper.deleteByCodeAndDate(deptCode,nowDate);
    }

    /**
     * 查询项目列表
     * 
     * @param costProjectNum 查询项目列表
     * @return 查询项目列表
     */
	@Override
	public List<CostProjectNum> selectCostProjectNumListGroup(CostProjectNum costProjectNum) {
		return costProjectNumMapper.selectCostProjectNumListGroup(costProjectNum);
	}
}
