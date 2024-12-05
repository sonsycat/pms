package com.zjry.pms.apportionment.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjry.pms.apportionment.mapper.CostApportionmentDictionaryDetailMapper;
import com.zjry.pms.apportionment.mapper.CostApportionmentDictionaryMapper;
import com.zjry.pms.apportionment.mapper.CostDatabaseMapper;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.apportionment.domain.CostApportionmentDictionary;
import com.zjry.pms.apportionment.domain.CostApportionmentDictionaryDetail;
import com.zjry.pms.apportionment.domain.CostDatabase;
import com.zjry.pms.apportionment.service.DBChangeService;
import com.zjry.pms.apportionment.service.ICostApportionmentDictionaryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * 比例分摊字典管理Service业务层处理
 * 
 * @author yzh
 * @date 2022-05-10
 */
@Service
public class CostApportionmentDictionaryServiceImpl extends ServiceImpl<CostApportionmentDictionaryMapper, CostApportionmentDictionary> implements ICostApportionmentDictionaryService {
    @Autowired
    private CostApportionmentDictionaryMapper costApportionmentDictionaryMapper;
    @Autowired
    private CostApportionmentDictionaryDetailMapper costApportionmentDictionaryDetailMapper;
    @Autowired
    private DBChangeService dBChangeService;
    @Autowired
    private CostDatabaseMapper costDatabaseMapper;

    /**
     * 查询比例分摊字典管理
     * 
     * @param id 比例分摊字典管理ID
     * @return 比例分摊字典管理
     */
    @Override
    public CostApportionmentDictionary selectCostApportionmentDictionaryById(Long id) {
    	List<CostApportionmentDictionaryDetail>costApportionmentDictionaryDetailList1 = new ArrayList<CostApportionmentDictionaryDetail>();
    	List<CostApportionmentDictionaryDetail>costApportionmentDictionaryDetailList2 = new ArrayList<CostApportionmentDictionaryDetail>();
    	List<CostApportionmentDictionaryDetail>costApportionmentDictionaryDetailList3 = new ArrayList<CostApportionmentDictionaryDetail>();
    	List<CostApportionmentDictionaryDetail>costApportionmentDictionaryDetailList4 = new ArrayList<CostApportionmentDictionaryDetail>();
    	CostApportionmentDictionary costApportionmentDictionary = costApportionmentDictionaryMapper.selectCostApportionmentDictionaryById(id);
    	CostApportionmentDictionaryDetail costApportionmentDictionaryDetail = new CostApportionmentDictionaryDetail();
    	costApportionmentDictionaryDetail.setRatioCode(costApportionmentDictionary.getRatioCode());
    	List<CostApportionmentDictionaryDetail>costApportionmentDictionaryDetailList = costApportionmentDictionaryDetailMapper.selectCostApportionmentDictionaryDetailList(costApportionmentDictionaryDetail);
        if(costApportionmentDictionaryDetailList.size()>0){
        	for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail2 : costApportionmentDictionaryDetailList) {
    			if(costApportionmentDictionaryDetail2.getLevelType()=="1"||"1".equals(costApportionmentDictionaryDetail2.getLevelType())){
    				costApportionmentDictionaryDetailList1.add(costApportionmentDictionaryDetail2);
    			}else if(costApportionmentDictionaryDetail2.getLevelType()=="2"||"2".equals(costApportionmentDictionaryDetail2.getLevelType())){
    				costApportionmentDictionaryDetailList2.add(costApportionmentDictionaryDetail2);
    			}else if(costApportionmentDictionaryDetail2.getLevelType()=="3"||"3".equals(costApportionmentDictionaryDetail2.getLevelType())){
    				costApportionmentDictionaryDetailList3.add(costApportionmentDictionaryDetail2);
    			}else if(costApportionmentDictionaryDetail2.getLevelType()=="4"||"4".equals(costApportionmentDictionaryDetail2.getLevelType())){
    				costApportionmentDictionaryDetailList4.add(costApportionmentDictionaryDetail2);
    			}
    		}
        }
        costApportionmentDictionary.setCostApportionmentDictionaryDetailList1(costApportionmentDictionaryDetailList1);
        costApportionmentDictionary.setCostApportionmentDictionaryDetailList2(costApportionmentDictionaryDetailList2);
        costApportionmentDictionary.setCostApportionmentDictionaryDetailList3(costApportionmentDictionaryDetailList3);
        costApportionmentDictionary.setCostApportionmentDictionaryDetailList4(costApportionmentDictionaryDetailList4);
    	return costApportionmentDictionary;
    }

    /**
     * 查询比例分摊字典管理列表
     * 
     * @param costApportionmentDictionary 比例分摊字典管理
     * @return 比例分摊字典管理
     */
    @Override
    public List<CostApportionmentDictionary> selectCostApportionmentDictionaryList(CostApportionmentDictionary costApportionmentDictionary) {
        return costApportionmentDictionaryMapper.selectCostApportionmentDictionaryList(costApportionmentDictionary);
    }
    
    /**
     * 查询比例分摊字典管理列表
     * 
     * @param costApportionmentDictionary 比例分摊字典管理
     * @return 比例分摊字典管理
     * @throws Exception 
     */
    @Override
	@Transactional(rollbackFor = Exception.class)
    public CostApportionmentDictionary selectCostApportionmentDictionaryLists(CostApportionmentDictionary costApportionmentDictionary) throws Exception {
    	CostDatabase costDatabase = costDatabaseMapper.selectCostDatabaseByCode(costApportionmentDictionary.getCoonectCode());
    	List<CostApportionmentDictionaryDetail>list = new ArrayList<CostApportionmentDictionaryDetail>();
    	costApportionmentDictionary.setCostApportionmentDictionaryDetailList1(list);
    	costApportionmentDictionary.setCostApportionmentDictionaryDetailList2(list);
    	costApportionmentDictionary.setCostApportionmentDictionaryDetailList3(list);
    	costApportionmentDictionary.setCostApportionmentDictionaryDetailList4(list);
    	if(costApportionmentDictionary.getRatioType()=="1"||"1".equals(costApportionmentDictionary.getRatioType())){
//    		List<CostApportionmentDictionaryDetail>list1 = dBChangeService.changeDb(costDatabase, costApportionmentDictionary.getFirstLevelSql(), "1",costApportionmentDictionary.getRatioCode());
//    		List<CostApportionmentDictionaryDetail>list2 = dBChangeService.changeDb(costDatabase, costApportionmentDictionary.getSecondLevelSql(), "2",costApportionmentDictionary.getRatioCode());
//    		List<CostApportionmentDictionaryDetail>list3 = dBChangeService.changeDb(costDatabase, costApportionmentDictionary.getThirdLevelSql(), "3",costApportionmentDictionary.getRatioCode());
//    		if(list1.size()>0){
//				int sum = 0;
//				for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail : list1) {
//					sum += Integer.parseInt(costApportionmentDictionaryDetail.getUseRatio());
//					costApportionmentDictionaryDetail.setNum(costApportionmentDictionaryDetail.getUseRatio());
//					CostRevenue costRevenue = costRevenueMapper.selectDeptByDeptCode(costApportionmentDictionaryDetail.getDeptCode());
//					if (ObjectUtils.isEmpty(costRevenue)) {
//						if (StringUtils.isNotEmpty(costApportionmentDictionary.getException())) {
//							if (!costApportionmentDictionary.getException().contains("科室"+costApportionmentDictionaryDetail.getDeptCode()+"不存在")) {
//								costApportionmentDictionary.setException(costApportionmentDictionary.getException() + "; 科室"+costApportionmentDictionaryDetail.getDeptCode()+"不存在");
//							}
//						} else {
//							costApportionmentDictionary.setException("科室"+costApportionmentDictionaryDetail.getDeptCode()+"不存在");
//						}
//					}
//					String type = costRevenue.getType();
//					if (!"2".equals(type) && !"3".equals(type) && !"4".equals(type)) {
//						if (StringUtils.isNotEmpty(costApportionmentDictionary.getException())) {
//							if (!costApportionmentDictionary.getException().contains("一级分摊中只能有医辅、医技、临床的科室类型")) {
//								costApportionmentDictionary.setException(costApportionmentDictionary.getException() + "; 一级分摊中只能有医辅、医技、临床的科室类型");
//							}
//						} else {
//							costApportionmentDictionary.setException("一级分摊中只能有医辅、医技、临床的科室类型");
//						}
//					}
//				}
//				for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail : list1) {
//					costApportionmentDictionaryDetail.setUseRatio(new BigDecimal(costApportionmentDictionaryDetail.getNum()).divide(new BigDecimal(sum),2,BigDecimal.ROUND_HALF_EVEN).toString());
//				}
//    			costApportionmentDictionary.setCostApportionmentDictionaryDetailList1(list1);
//    		}
//    		if(list2.size()>0){
//				int sum = 0;
//				for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail : list2) {
//					sum += Integer.parseInt(costApportionmentDictionaryDetail.getUseRatio());
//					costApportionmentDictionaryDetail.setNum(costApportionmentDictionaryDetail.getUseRatio());
//					CostRevenue costRevenue = costRevenueMapper.selectDeptByDeptCode(costApportionmentDictionaryDetail.getDeptCode());
//					if (ObjectUtils.isEmpty(costRevenue)) {
//						if (StringUtils.isNotEmpty(costApportionmentDictionary.getException())) {
//							if (!costApportionmentDictionary.getException().contains("科室"+costApportionmentDictionaryDetail.getDeptCode()+"不存在")) {
//								costApportionmentDictionary.setException(costApportionmentDictionary.getException() + "; 科室"+costApportionmentDictionaryDetail.getDeptCode()+"不存在");
//							}
//						} else {
//							costApportionmentDictionary.setException("科室"+costApportionmentDictionaryDetail.getDeptCode()+"不存在");
//						}
//					}
//					//String type = costRevenue.getType();
//					if (!"3".equals(type) && !"4".equals(type)) {
//						if (StringUtils.isNotEmpty(costApportionmentDictionary.getException())) {
//							if (!costApportionmentDictionary.getException().contains("二级分摊中只能有医技、临床科室类型")) {
//								costApportionmentDictionary.setException(costApportionmentDictionary.getException() + "; 二级分摊中只能有医技、临床科室类型");
//							}
//						} else {
//							costApportionmentDictionary.setException("二级分摊中只能有医技、临床科室类型");
//						}
//					}
//				}
//				for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail : list2) {
//					costApportionmentDictionaryDetail.setUseRatio(new BigDecimal(costApportionmentDictionaryDetail.getNum()).divide(new BigDecimal(sum),2,BigDecimal.ROUND_HALF_EVEN).toString());
//				}
//				costApportionmentDictionary.setCostApportionmentDictionaryDetailList2(list2);
//    		}
//    		if(list3.size()>0){
//				int sum = 0;
//				for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail : list3) {
//					sum += Integer.parseInt(costApportionmentDictionaryDetail.getUseRatio());
//					costApportionmentDictionaryDetail.setNum(costApportionmentDictionaryDetail.getUseRatio());
//					CostRevenue costRevenue = costRevenueMapper.selectDeptByDeptCode(costApportionmentDictionaryDetail.getDeptCode());
//					if (ObjectUtils.isEmpty(costRevenue)) {
//						if (StringUtils.isNotEmpty(costApportionmentDictionary.getException())) {
//							if (!costApportionmentDictionary.getException().contains("科室"+costApportionmentDictionaryDetail.getDeptCode()+"不存在")) {
//								costApportionmentDictionary.setException(costApportionmentDictionary.getException() + "; 科室"+costApportionmentDictionaryDetail.getDeptCode()+"不存在");
//							}
//						} else {
//							costApportionmentDictionary.setException("科室"+costApportionmentDictionaryDetail.getDeptCode()+"不存在");
//						}
//					}
//					String type = costRevenue.getType();
//					if (!"4".equals(type)) {
//						if (StringUtils.isNotEmpty(costApportionmentDictionary.getException())) {
//							if (!costApportionmentDictionary.getException().contains("三级分摊中只能有临床的科室类型")) {
//								costApportionmentDictionary.setException(costApportionmentDictionary.getException() + "; 三级分摊中只能有临床的科室类型");
//							}
//						} else {
//							costApportionmentDictionary.setException("三级分摊中只能有临床的科室类型");
//						}
//					}
//				}
//				for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail : list3) {
//					costApportionmentDictionaryDetail.setUseRatio(new BigDecimal(costApportionmentDictionaryDetail.getNum()).divide(new BigDecimal(sum),2,BigDecimal.ROUND_HALF_EVEN).toString());
//				}
//				costApportionmentDictionary.setCostApportionmentDictionaryDetailList3(list3);
//    		}
   	}else if(costApportionmentDictionary.getRatioType()=="2"||"2".equals(costApportionmentDictionary.getRatioType())){
    		List<CostApportionmentDictionaryDetail>list4 = dBChangeService.changeDb(costDatabase, costApportionmentDictionary.getCountSql(), "4",costApportionmentDictionary.getRatioCode());
    		if(list4.size()>0){
				int sum = 0;
				for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail : list4) {
					sum += Integer.parseInt(costApportionmentDictionaryDetail.getUseRatio());
					costApportionmentDictionaryDetail.setNum(costApportionmentDictionaryDetail.getUseRatio());
				}
				for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail : list4) {
					BigDecimal num = new BigDecimal(costApportionmentDictionaryDetail.getNum());
					BigDecimal sums = new BigDecimal(sum);
					BigDecimal divide = num.divide(sums, 2, BigDecimal.ROUND_HALF_EVEN);
					costApportionmentDictionaryDetail.setUseRatio(String.valueOf(divide));
				}
    			costApportionmentDictionary.setCostApportionmentDictionaryDetailList4(list4);
    		}
    	}
        return costApportionmentDictionary;
    }
    
    /**
     * 查询比例分摊字典管理列表
     * 
     * @param costApportionmentDictionary 比例分摊字典管理
     * @return 比例分摊字典管理
     */
    @Override
    public List<CostApportionmentDictionary> selectCostApportionmentDictionaryListByName(CostApportionmentDictionary costApportionmentDictionary) {
        return costApportionmentDictionaryMapper.selectCostApportionmentDictionaryListByName(costApportionmentDictionary);
    }

    /**
     * 新增比例分摊字典管理
     * 
     * @param costApportionmentDictionary 比例分摊字典管理
     * @return 结果
     */
    @Override
    public int insertCostApportionmentDictionary(CostApportionmentDictionary costApportionmentDictionary) {
    	if(StringUtils.isNotEmpty(costApportionmentDictionary.getCostApportionmentDictionaryDetailList1())){
    		if(costApportionmentDictionary.getCostApportionmentDictionaryDetailList1().size()>0){
    			for (int i = 0; i < costApportionmentDictionary.getCostApportionmentDictionaryDetailList1().size(); i++) {
    				CostApportionmentDictionaryDetail costApportionmentDictionaryDetail =costApportionmentDictionary.getCostApportionmentDictionaryDetailList1().get(i);
    				costApportionmentDictionaryDetail.setRatioCode(costApportionmentDictionary.getRatioCode());
    				costApportionmentDictionaryDetail.preInsert();
    				costApportionmentDictionaryDetailMapper.insertCostApportionmentDictionaryDetail(costApportionmentDictionaryDetail);
				}
    		}
    	}
    	if(StringUtils.isNotEmpty(costApportionmentDictionary.getCostApportionmentDictionaryDetailList2())){
    		if(costApportionmentDictionary.getCostApportionmentDictionaryDetailList2().size()>0){
    			for (int i = 0; i < costApportionmentDictionary.getCostApportionmentDictionaryDetailList2().size(); i++) {
    				CostApportionmentDictionaryDetail costApportionmentDictionaryDetail =costApportionmentDictionary.getCostApportionmentDictionaryDetailList2().get(i);
    				costApportionmentDictionaryDetail.setRatioCode(costApportionmentDictionary.getRatioCode());
    				costApportionmentDictionaryDetail.preInsert();
    				costApportionmentDictionaryDetailMapper.insertCostApportionmentDictionaryDetail(costApportionmentDictionaryDetail);
				}
    		}
    	}
    	if(StringUtils.isNotEmpty(costApportionmentDictionary.getCostApportionmentDictionaryDetailList3())){
    		if(costApportionmentDictionary.getCostApportionmentDictionaryDetailList3().size()>0){
    			for (int i = 0; i < costApportionmentDictionary.getCostApportionmentDictionaryDetailList3().size(); i++) {
    				CostApportionmentDictionaryDetail costApportionmentDictionaryDetail =costApportionmentDictionary.getCostApportionmentDictionaryDetailList3().get(i);
    				costApportionmentDictionaryDetail.setRatioCode(costApportionmentDictionary.getRatioCode());
    				costApportionmentDictionaryDetail.preInsert();
    				costApportionmentDictionaryDetailMapper.insertCostApportionmentDictionaryDetail(costApportionmentDictionaryDetail);
				}
    		}
    	}
    	if(StringUtils.isNotEmpty(costApportionmentDictionary.getCostApportionmentDictionaryDetailList4())){
    		if(costApportionmentDictionary.getCostApportionmentDictionaryDetailList4().size()>0){
    			for (int i = 0; i < costApportionmentDictionary.getCostApportionmentDictionaryDetailList4().size(); i++) {
    				CostApportionmentDictionaryDetail costApportionmentDictionaryDetail =costApportionmentDictionary.getCostApportionmentDictionaryDetailList4().get(i);
    				costApportionmentDictionaryDetail.setRatioCode(costApportionmentDictionary.getRatioCode());
    				costApportionmentDictionaryDetail.preInsert();
    				costApportionmentDictionaryDetailMapper.insertCostApportionmentDictionaryDetail(costApportionmentDictionaryDetail);
				}
    		}
    	}
    	costApportionmentDictionary.preInsert();
        return costApportionmentDictionaryMapper.insertCostApportionmentDictionary(costApportionmentDictionary);
    }

    /**
     * 修改比例分摊字典管理
     * 
     * @param costApportionmentDictionary 比例分摊字典管理
     * @return 结果
     */
    @Override
    public int updateCostApportionmentDictionary(CostApportionmentDictionary costApportionmentDictionary) {
    	costApportionmentDictionaryDetailMapper.deleteCostApportionmentDictionaryDetailByCode(costApportionmentDictionary.getRatioCode());
    	if(StringUtils.isNotEmpty(costApportionmentDictionary.getCostApportionmentDictionaryDetailList1())){
    		if(costApportionmentDictionary.getCostApportionmentDictionaryDetailList1().size()>0){
    			for (int i = 0; i < costApportionmentDictionary.getCostApportionmentDictionaryDetailList1().size(); i++) {
    				CostApportionmentDictionaryDetail costApportionmentDictionaryDetail =costApportionmentDictionary.getCostApportionmentDictionaryDetailList1().get(i);
    				costApportionmentDictionaryDetail.preInsert();
    				costApportionmentDictionaryDetailMapper.insertCostApportionmentDictionaryDetail(costApportionmentDictionaryDetail);
				}
    		}
    	}
    	if(StringUtils.isNotEmpty(costApportionmentDictionary.getCostApportionmentDictionaryDetailList2())){
    		if(costApportionmentDictionary.getCostApportionmentDictionaryDetailList2().size()>0){
    			for (int i = 0; i < costApportionmentDictionary.getCostApportionmentDictionaryDetailList2().size(); i++) {
    				CostApportionmentDictionaryDetail costApportionmentDictionaryDetail =costApportionmentDictionary.getCostApportionmentDictionaryDetailList2().get(i);
    				costApportionmentDictionaryDetail.preInsert();
    				costApportionmentDictionaryDetailMapper.insertCostApportionmentDictionaryDetail(costApportionmentDictionaryDetail);
				}
    		}
    	}
    	if(StringUtils.isNotEmpty(costApportionmentDictionary.getCostApportionmentDictionaryDetailList3())){
    		if(costApportionmentDictionary.getCostApportionmentDictionaryDetailList3().size()>0){
    			for (int i = 0; i < costApportionmentDictionary.getCostApportionmentDictionaryDetailList3().size(); i++) {
    				CostApportionmentDictionaryDetail costApportionmentDictionaryDetail =costApportionmentDictionary.getCostApportionmentDictionaryDetailList3().get(i);
    				costApportionmentDictionaryDetail.preInsert();
    				costApportionmentDictionaryDetailMapper.insertCostApportionmentDictionaryDetail(costApportionmentDictionaryDetail);
				}
    		}
    	}
    	if(StringUtils.isNotEmpty(costApportionmentDictionary.getCostApportionmentDictionaryDetailList4())){
    		if(costApportionmentDictionary.getCostApportionmentDictionaryDetailList4().size()>0){
    			for (int i = 0; i < costApportionmentDictionary.getCostApportionmentDictionaryDetailList4().size(); i++) {
    				CostApportionmentDictionaryDetail costApportionmentDictionaryDetail =costApportionmentDictionary.getCostApportionmentDictionaryDetailList4().get(i);
    				costApportionmentDictionaryDetail.preInsert();
    				costApportionmentDictionaryDetailMapper.insertCostApportionmentDictionaryDetail(costApportionmentDictionaryDetail);
				}
    		}
    	}
    	costApportionmentDictionary.preUpdate();
        return costApportionmentDictionaryMapper.updateCostApportionmentDictionary(costApportionmentDictionary);
    }

    /**
     * 批量删除比例分摊字典管理
     * 
     * @param ids 需要删除的比例分摊字典管理ID
     * @return 结果
     */
    @Override
    public int deleteCostApportionmentDictionaryByIds(Long[] ids) {
        return costApportionmentDictionaryMapper.deleteCostApportionmentDictionaryByIds(ids);
    }

    /**
     * 删除比例分摊字典管理信息
     * 
     * @param id 比例分摊字典管理ID
     * @return 结果
     */
    @Override
    public int deleteCostApportionmentDictionaryById(Long id) {
        return costApportionmentDictionaryMapper.deleteCostApportionmentDictionaryById(id);
    }
}
