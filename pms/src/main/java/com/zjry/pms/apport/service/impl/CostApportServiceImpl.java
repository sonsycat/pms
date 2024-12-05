package com.zjry.pms.apport.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjry.pms.apport.mapper.CostApportDetailMapper;
import com.zjry.pms.apport.mapper.CostApportMapper;
import com.zjry.pms.apport.domain.CostApport;
import com.zjry.pms.apport.domain.CostApportDetail;
import com.zjry.pms.apport.service.ICostApportService;
import com.zjry.pms.apportionment.domain.CostApportionmentDictionaryDetail;
import com.zjry.pms.apportionment.domain.CostApportionmentProject;
import com.zjry.pms.apportionment.domain.CostApportionmentProjectDept;
import com.zjry.pms.apportionment.domain.CostApportionmentProjectRatio;
import com.zjry.pms.apportionment.service.ICostApportionmentDictionaryDetailService;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectDeptService;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectRatioService;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectService;
import com.zjry.pms.project.domain.CostProject;
import com.zjry.pms.project.domain.CostProjectNum;
import com.zjry.pms.project.service.ICostProjectNumService;
import com.zjry.pms.project.service.ICostProjectService;

/**
 * 分摊计算Service业务层处理
 * 
 * @author yzh
 * @date 2022-05-17
 */
@Service
public class CostApportServiceImpl extends ServiceImpl<CostApportMapper, CostApport> implements ICostApportService {
    @Autowired
    private CostApportMapper costApportMapper;
    @Autowired
    private CostApportDetailMapper costApportDetailMapper;
    @Autowired
    private ICostApportionmentProjectDeptService costApportionmentProjectDeptService;
    @Autowired
    private ICostProjectNumService costProjectNumService;
    @Autowired
    private ICostApportionmentProjectService costApportionmentProjectService;
    @Autowired
    private ICostProjectService costProjectService;
    @Autowired
    private ICostApportionmentProjectRatioService costApportionmentProjectRatioService;
    @Autowired
    private ICostApportionmentDictionaryDetailService costApportionmentDictionaryDetailService;
    
    /**
     * 查询分摊计算
     * 
     * @param id 分摊计算ID
     * @return 分摊计算
     */
    @Override
    public CostApport selectCostApportById(Long id) {
    	CostApport costApport = costApportMapper.selectCostApportById(id);
    	//分摊成本数据
    	CostProjectNum costProjectNum = new CostProjectNum();
    	costProjectNum.setCompCode(costApport.getCompCode());
    	costProjectNum.setNowDate(costApport.getNowDate());
    	List<CostProjectNum>List2 = costProjectNumService.selectCostProjectNumListGroup(costProjectNum);
    	List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
    	List<Map<String, Object>> newList1 = new ArrayList<Map<String, Object>>();
    	List<Object> newListTemp = new ArrayList<Object>();
    	Map map1 = new HashMap<String, Object>();
        /*map1.put("label", "年月");
        map1.put("prop", "nowDate");
        newList.add(map1);*/
        Map map2 = new HashMap<String, Object>();
        map2.put("label", "科室");
        map2.put("prop", "deptCode");
        newList.add(map2);
        Map map3 = new HashMap<String, Object>();
        map3.put("label", "合计");
        map3.put("prop", "money");
        newList.add(map3);
    	for (CostProjectNum costProjectNum2 : List2) {
    		Map map = new HashMap<String, Object>();
            map.put("label",costProjectNum2.getProjectName());
            map.put("prop", costProjectNum2.getProjectCode());
            newList.add(map);
		}
    	costApport.setTableColumns1(newList);
    	List<Long> deptCost = costProjectNumService.selectCostPNDept(costProjectNum);
    	for (Long long1 : deptCost) {
    		Map map = new HashMap<String, Object>();
            //map.put("nowDate", DateUtils.formatDate(costApport.getNowDate(),"yyyy-MM"));
            map.put("deptCode",  deptNameById(long1));
            Double allmoney = 0.0;
    		CostProjectNum costProjectNumNew = new CostProjectNum();
    		costProjectNum.setCompCode(costApport.getCompCode());
    		costProjectNum.setNowDate(costApport.getNowDate());
    		costProjectNum.setDeptCode(long1);
    		List<CostProjectNum> allList = costProjectNumService.selectCostProjectNumList(costProjectNum);
    		for (CostProjectNum costProjectNum2 : allList) {
    			allmoney += costProjectNum2.getMoney();
			}
    		map.put("money", allmoney);
    		for (CostProjectNum costProjectNum2 : allList) {
    			map.put(costProjectNum2.getProjectCode(), costProjectNum2.getMoney());
			}
    		newList1.add(map);
		}
    	costApport.setTableData1(newList1);
    	for (int i = 1; i <= Integer.parseInt(costApport.getApportType()); i++) {
    		List<Map<String, Object>> newList2 = new ArrayList<Map<String, Object>>();
    		CostApportDetail costApportDetailks = new CostApportDetail();
        	costApportDetailks.setApportCode(costApport.getApportCode());
        	fz(i+"",costApportDetailks);
        	List<CostApportDetail>costApportDetailksList = costApportDetailMapper.selectCostApportDetailListGroupDept(costApportDetailks);
        	for (CostApportDetail costApportDetailkss : costApportDetailksList) {
        		Map map = new HashMap<String, Object>();
                //map.put("nowDate", DateUtils.formatDate(costApport.getNowDate(),"yyyy-MM"));
                map.put("deptCode",  deptNameById(costApportDetailkss.getDeptCode()));
                Double allmoney = 0.0;
    	    	CostApportDetail costApportDetailTemp1 = new CostApportDetail();
    	    	costApportDetailTemp1.setApportCode(costApport.getApportCode());
    	    	fz1(i+"",costApportDetailTemp1,costApportDetailkss.getDeptCode());
                List<CostApportDetail>costApportDetailNew1 = costApportDetailMapper.selectCostApportDetailLists(costApportDetailTemp1);
                for (CostApportDetail costApportDetail : costApportDetailNew1) {
                	allmoney += costApportDetail.getAmount();
    			}
                if(i==2){
                	CostApportDetail costApportDetailTemp11 = new CostApportDetail();
        	    	costApportDetailTemp11.setApportCode(costApport.getApportCode());
        	    	costApportDetailTemp11.setDeptCode(costApportDetailkss.getDeptCode());
        	    	costApportDetailTemp11.setDeptType("2");
        	    	costApportDetailTemp11.setCostType("1");
                	CostApportDetail costApportDetailNew11 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp11);
                	if(StringUtils.isNotNull(costApportDetailNew11)){
                		allmoney += costApportDetailNew11.getAmount();
                	}
                	CostApportDetail costApportDetailTemp12 = new CostApportDetail();
        	    	costApportDetailTemp12.setApportCode(costApport.getApportCode());
        	    	costApportDetailTemp12.setDeptCode(costApportDetailkss.getDeptCode());
        	    	costApportDetailTemp12.setApportType("1");
        	    	costApportDetailTemp12.setCostType("2");
        	    	costApportDetailTemp12.setDeptType("2");
                	CostApportDetail costApportDetailNew12 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp12);
                	if(StringUtils.isNotNull(costApportDetailNew12)){
                		allmoney+= costApportDetailNew12.getAmount();
                	}
                }else if(i==3){
                	CostApportDetail costApportDetailTemp11 = new CostApportDetail();
        	    	costApportDetailTemp11.setApportCode(costApport.getApportCode());
        	    	costApportDetailTemp11.setDeptCode(costApportDetailkss.getDeptCode());
        	    	costApportDetailTemp11.setDeptType("3");
        	    	costApportDetailTemp11.setCostType("1");
                	CostApportDetail costApportDetailNew11 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp11);
                	if(StringUtils.isNotNull(costApportDetailNew11)){
                		allmoney += costApportDetailNew11.getAmount();
                	}
                	CostApportDetail costApportDetailTemp12 = new CostApportDetail();
        	    	costApportDetailTemp12.setApportCode(costApport.getApportCode());
        	    	costApportDetailTemp12.setDeptCode(costApportDetailkss.getDeptCode());
        	    	costApportDetailTemp12.setApportType("1");
        	    	costApportDetailTemp12.setCostType("2");
        	    	costApportDetailTemp12.setDeptType("3");
                	CostApportDetail costApportDetailNew12 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp12);
                	if(StringUtils.isNotNull(costApportDetailNew12)){
                		allmoney+= costApportDetailNew12.getAmount();
                	}
                	CostApportDetail costApportDetailTemp13 = new CostApportDetail();
        	    	costApportDetailTemp13.setApportCode(costApport.getApportCode());
        	    	costApportDetailTemp13.setDeptCode(costApportDetailkss.getDeptCode());
        	    	costApportDetailTemp13.setApportType("2");
        	    	costApportDetailTemp13.setCostType("2");
        	    	costApportDetailTemp13.setDeptType("3");
                	CostApportDetail costApportDetailNew13 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp13);
                	if(StringUtils.isNotNull(costApportDetailNew13)){
                		allmoney+= costApportDetailNew13.getAmount();
                	}
                }else if(i==4){
                	CostApportDetail costApportDetailTemp11 = new CostApportDetail();
        	    	costApportDetailTemp11.setApportCode(costApport.getApportCode());
        	    	costApportDetailTemp11.setDeptCode(costApportDetailkss.getDeptCode());
        	    	costApportDetailTemp11.setDeptType("4");
        	    	costApportDetailTemp11.setCostType("1");
                	CostApportDetail costApportDetailNew11 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp11);
                	if(StringUtils.isNotNull(costApportDetailNew11)){
                		allmoney += costApportDetailNew11.getAmount();
                	}
                	CostApportDetail costApportDetailTemp12 = new CostApportDetail();
        	    	costApportDetailTemp12.setApportCode(costApport.getApportCode());
        	    	costApportDetailTemp12.setDeptCode(costApportDetailkss.getDeptCode());
        	    	costApportDetailTemp12.setApportType("1");
        	    	costApportDetailTemp12.setCostType("2");
        	    	costApportDetailTemp12.setDeptType("4");
                	CostApportDetail costApportDetailNew12 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp12);
                	if(StringUtils.isNotNull(costApportDetailNew12)){
                		allmoney+= costApportDetailNew12.getAmount();
                	}
                	CostApportDetail costApportDetailTemp13 = new CostApportDetail();
        	    	costApportDetailTemp13.setApportCode(costApport.getApportCode());
        	    	costApportDetailTemp13.setDeptCode(costApportDetailkss.getDeptCode());
        	    	costApportDetailTemp13.setApportType("2");
        	    	costApportDetailTemp13.setCostType("2");
        	    	costApportDetailTemp13.setDeptType("4");
                	CostApportDetail costApportDetailNew13 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp13);
                	if(StringUtils.isNotNull(costApportDetailNew13)){
                		allmoney+= costApportDetailNew13.getAmount();
                	}
                	CostApportDetail costApportDetailTemp14 = new CostApportDetail();
        	    	costApportDetailTemp14.setApportCode(costApport.getApportCode());
        	    	costApportDetailTemp14.setDeptCode(costApportDetailkss.getDeptCode());
        	    	costApportDetailTemp14.setApportType("3");
        	    	costApportDetailTemp14.setCostType("2");
        	    	costApportDetailTemp14.setDeptType("4");
                	CostApportDetail costApportDetailNew14 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp14);
                	if(StringUtils.isNotNull(costApportDetailNew14)){
                		allmoney+= costApportDetailNew14.getAmount();
                	}
                }
                map.put("money", allmoney);
                for (CostProjectNum costProjectNum2 : List2) { 
                	CostApportDetail costApportDetailTemp2 = new CostApportDetail();
                	costApportDetailTemp2.setProjectCode(costProjectNum2.getProjectCode());
                	costApportDetailTemp2.setApportCode(costApport.getApportCode());
                	fz1(i+"",costApportDetailTemp2,costApportDetailkss.getDeptCode());
                	List<CostApportDetail>costApportDetailNew2 = costApportDetailMapper.selectCostApportDetailLists(costApportDetailTemp2);
                	BigDecimal amount = new BigDecimal(0.0);
                	if(i==2){
                    	CostApportDetail costApportDetailTemp11 = new CostApportDetail();
            	    	costApportDetailTemp11.setApportCode(costApport.getApportCode());
            	    	costApportDetailTemp11.setProjectCode(costProjectNum2.getProjectCode());
            	    	costApportDetailTemp11.setDeptCode(costApportDetailkss.getDeptCode());
            	    	costApportDetailTemp11.setDeptType("2");
            	    	costApportDetailTemp11.setCostType("1");
                    	CostApportDetail costApportDetailNew11 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp11);
                    	if(StringUtils.isNotNull(costApportDetailNew11)){
                    		amount= amount.add(new BigDecimal(costApportDetailNew11.getAmount()));
                    	}
                    	CostApportDetail costApportDetailTemp12 = new CostApportDetail();
            	    	costApportDetailTemp12.setApportCode(costApport.getApportCode());
            	    	costApportDetailTemp12.setProjectCode(costProjectNum2.getProjectCode());
            	    	costApportDetailTemp12.setDeptCode(costApportDetailkss.getDeptCode());
            	    	costApportDetailTemp12.setApportType("1");
            	    	costApportDetailTemp12.setCostType("2");
            	    	costApportDetailTemp12.setDeptType("2");
                    	CostApportDetail costApportDetailNew12 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp12);
                    	if(StringUtils.isNotNull(costApportDetailNew12)){
                    		amount= amount.add(new BigDecimal(costApportDetailNew12.getAmount()));
                    	}
                    }else if(i==3){
                    	CostApportDetail costApportDetailTemp11 = new CostApportDetail();
            	    	costApportDetailTemp11.setApportCode(costApport.getApportCode());
            	    	costApportDetailTemp11.setProjectCode(costProjectNum2.getProjectCode());
            	    	costApportDetailTemp11.setDeptCode(costApportDetailkss.getDeptCode());
            	    	costApportDetailTemp11.setDeptType("3");
            	    	costApportDetailTemp11.setCostType("1");
                    	CostApportDetail costApportDetailNew11 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp11);
                    	if(StringUtils.isNotNull(costApportDetailNew11)){
                    		amount= amount.add(new BigDecimal(costApportDetailNew11.getAmount()));
                    	}
                    	CostApportDetail costApportDetailTemp12 = new CostApportDetail();
            	    	costApportDetailTemp12.setApportCode(costApport.getApportCode());
            	    	costApportDetailTemp12.setProjectCode(costProjectNum2.getProjectCode());
            	    	costApportDetailTemp12.setDeptCode(costApportDetailkss.getDeptCode());
            	    	costApportDetailTemp12.setApportType("1");
            	    	costApportDetailTemp12.setCostType("2");
            	    	costApportDetailTemp12.setDeptType("3");
                    	CostApportDetail costApportDetailNew12 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp12);
                    	if(StringUtils.isNotNull(costApportDetailNew12)){
                    		amount= amount.add(new BigDecimal(costApportDetailNew12.getAmount()));
                    	}
                    	CostApportDetail costApportDetailTemp13 = new CostApportDetail();
            	    	costApportDetailTemp13.setApportCode(costApport.getApportCode());
            	    	costApportDetailTemp13.setDeptCode(costApportDetailkss.getDeptCode());
            	    	costApportDetailTemp13.setProjectCode(costProjectNum2.getProjectCode());
            	    	costApportDetailTemp13.setApportType("2");
            	    	costApportDetailTemp13.setCostType("2");
            	    	costApportDetailTemp13.setDeptType("3");
                    	CostApportDetail costApportDetailNew13 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp13);
                    	if(StringUtils.isNotNull(costApportDetailNew13)){
                    		amount= amount.add(new BigDecimal(costApportDetailNew13.getAmount()));
                    	}
                    }else if(i==4){
                    	CostApportDetail costApportDetailTemp11 = new CostApportDetail();
            	    	costApportDetailTemp11.setApportCode(costApport.getApportCode());
            	    	costApportDetailTemp11.setProjectCode(costProjectNum2.getProjectCode());
            	    	costApportDetailTemp11.setDeptCode(costApportDetailkss.getDeptCode());
            	    	costApportDetailTemp11.setDeptType("4");
            	    	costApportDetailTemp11.setCostType("1");
                    	CostApportDetail costApportDetailNew11 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp11);
                    	if(StringUtils.isNotNull(costApportDetailNew11)){
                    		amount= amount.add(new BigDecimal(costApportDetailNew11.getAmount()));
                    	}
                    	CostApportDetail costApportDetailTemp12 = new CostApportDetail();
            	    	costApportDetailTemp12.setApportCode(costApport.getApportCode());
            	    	costApportDetailTemp12.setProjectCode(costProjectNum2.getProjectCode());
            	    	costApportDetailTemp12.setDeptCode(costApportDetailkss.getDeptCode());
            	    	costApportDetailTemp12.setApportType("1");
            	    	costApportDetailTemp12.setCostType("2");
            	    	costApportDetailTemp12.setDeptType("4");
                    	CostApportDetail costApportDetailNew12 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp12);
                    	if(StringUtils.isNotNull(costApportDetailNew12)){
                    		amount= amount.add(new BigDecimal(costApportDetailNew12.getAmount()));
                    	}
                    	CostApportDetail costApportDetailTemp14 = new CostApportDetail();
            	    	costApportDetailTemp14.setApportCode(costApport.getApportCode());
            	    	costApportDetailTemp14.setDeptCode(costApportDetailkss.getDeptCode());
            	    	costApportDetailTemp14.setApportType("3");
            	    	costApportDetailTemp14.setProjectCode(costProjectNum2.getProjectCode());
            	    	costApportDetailTemp14.setCostType("2");
            	    	costApportDetailTemp14.setDeptType("4");
                    	CostApportDetail costApportDetailNew14 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp14);
                    	if(StringUtils.isNotNull(costApportDetailNew14)){
                    		amount= amount.add(new BigDecimal(costApportDetailNew14.getAmount()));
                    	}
                    	CostApportDetail costApportDetailTemp15 = new CostApportDetail();
            	    	costApportDetailTemp15.setApportCode(costApport.getApportCode());
            	    	costApportDetailTemp15.setDeptCode(costApportDetailkss.getDeptCode());
            	    	costApportDetailTemp15.setProjectCode(costProjectNum2.getProjectCode());
            	    	costApportDetailTemp15.setApportType("2");
            	    	costApportDetailTemp15.setCostType("2");
            	    	costApportDetailTemp15.setDeptType("4");
                    	CostApportDetail costApportDetailNew15 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp15);
                    	if(StringUtils.isNotNull(costApportDetailNew15)){
                    		amount= amount.add(new BigDecimal(costApportDetailNew15.getAmount()));
                    	}
                    }
                	if(costApportDetailNew2.size()>0){
                		amount = amount.add(new BigDecimal(costApportDetailNew2.get(0).getAmount()));
                	}
                	if(amount.doubleValue()>0){
                   	 map.put(costProjectNum2.getProjectCode(), amount);
                	}
                }
                newList2.add(map);
    		}
        	if(i==1){
        		costApport.setTableData2(newList2);
        	}else if(i==2){
        		costApport.setTableData3(newList2);
        	}else if(i==3){
        		costApport.setTableData4(newList2);
        	}else if(i==4){
        		costApport.setTableData5(newList2);
        	}
		}
        return costApport;
    }
    
    public void fz(String apportType,CostApportDetail costApportDetailTemp1){
    	if("1".equals(apportType)){
	    	costApportDetailTemp1.setApportType("1");
	    	costApportDetailTemp1.setCostType("2");
        }else if("2".equals(apportType)){
        	costApportDetailTemp1.setApportType("2");
	    	costApportDetailTemp1.setCostType("2");
        }else if("3".equals(apportType)){
        	costApportDetailTemp1.setApportType("3");
	    	costApportDetailTemp1.setCostType("2");
        }else if("4".equals(apportType)){
        	costApportDetailTemp1.setApportType("4");
	    	costApportDetailTemp1.setCostType("2");
        }
    }
    
    public void fz1(String apportType,CostApportDetail costApportDetailTemp1,Long long1){
    	if("1".equals(apportType)){
        	costApportDetailTemp1.setDeptCode(long1);
	    	costApportDetailTemp1.setApportType("1");
	    	costApportDetailTemp1.setCostType("2");
        }else if("2".equals(apportType)){
        	costApportDetailTemp1.setDeptCode(long1);
        	costApportDetailTemp1.setApportType("2");
	    	costApportDetailTemp1.setCostType("2");
        }else if("3".equals(apportType)){
        	costApportDetailTemp1.setDeptCode(long1);
        	costApportDetailTemp1.setApportType("3");
	    	costApportDetailTemp1.setCostType("2");
        }else if("4".equals(apportType)){
        	costApportDetailTemp1.setDeptCode(long1);
        	costApportDetailTemp1.setApportType("4");
	    	costApportDetailTemp1.setCostType("2");
        }
    }
    

    /**
     * 查询分摊计算列表
     * 
     * @param costApport 分摊计算
     * @return 分摊计算
     */
    @Override
    public List<CostApport> selectCostApportList(CostApport costApport) {
        return costApportMapper.selectCostApportList(costApport);
    }

    /**
     * 查询分摊计算列表
     *
     * @param costApport 分摊计算
     * @return 分摊计算
     */
    @Override
    public List<CostApport> selectCostApportListOrderByNowDate(CostApport costApport) {
        return costApportMapper.selectCostApportListOrderByNowDate(costApport);
    }

    /**
     * 新增分摊计算
     * 
     * @param costApport 分摊计算
     * @return 结果
     */
    @Override
    public int insertCostApport(CostApport costApport) {
    	costApport.preInsert();
        return costApportMapper.insertCostApport(costApport);
    }
    
    /**
     * 分摊计算
     * 
     * @param costApport 分摊计算
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ftjs(CostApport costApport){
		zjft(costApport);
    	jsft(costApport);
    }
    
  //直接分摊  复制成本录入到分摊计算明细
    public void zjft(CostApport costApport){
    	//分摊成本数据
    	CostProjectNum costProjectNum = new CostProjectNum();
    	costProjectNum.setCompCode(costApport.getCompCode());
    	costProjectNum.setNowDate(costApport.getNowDate());
    	List<CostProjectNum>List2 = costProjectNumService.selectCostProjectNumList(costProjectNum);
    	BigDecimal ftqje = new BigDecimal(0.0);
    	if(List2.size()>0){
    		for (CostProjectNum costProjectNum2 : List2) {
	    		CostApportDetail costApportDetail = new CostApportDetail();
	    		costApportDetail.setApportCode(costApport.getApportCode());
	    		costApportDetail.setDeptCode(costProjectNum2.getDeptCode());
	    		costApportDetail.setDeptType(costProjectNum2.getDeptType());
	    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
	    		costApportDetail.setCostType("1");
	    		costApportDetail.setAmount(costProjectNum2.getMoney());
	    		costApportDetail.preInsert();
	    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
	    		ftqje = ftqje.add(new BigDecimal(costProjectNum2.getMoney()));
    		}
    	}
    	costApport.setForward(ftqje.doubleValue());
    }
  //计算计入成本分摊
    public void jsft(CostApport costApport){
    	BigDecimal fthje  = new BigDecimal(0.0);
    	SimpleDateFormat sdf =  new  SimpleDateFormat("yyyy-MM" );
    	//分摊成本数据
    	CostProjectNum costProjectNum = new CostProjectNum();
    	costProjectNum.setCompCode(costApport.getCompCode());
    	costProjectNum.setNowDate(costApport.getNowDate());
    	List<CostProjectNum>List2 = costProjectNumService.selectCostProjectNumListGroup(costProjectNum);
    	if(List2.size()>0){
    		for (CostProjectNum costProjectNum2 : List2) {
    			List<String>deptList = new ArrayList<String>();
    			CostProject costProject = new CostProject();
				costProject.setCompCode(costApport.getCompCode());
    			costProject.setProjectCode(costProjectNum2.getProjectCode());
    			List<CostProject>list3 = costProjectService.selectCostProjectList(costProject);
				if(list3.size()>0){
					if(list3.get(0).getIsCostCalculate()=="0"||"0".equals(list3.get(0).getIsCostCalculate())){
						//分摊方案
				    	CostApportionmentProject costApportionmentProject1 = new CostApportionmentProject();
				    	//list3.get(0).getCostScheme()计算计入成本分摊方案
				    	costApportionmentProject1.setProjectCode(list3.get(0).getCostScheme());
				    	costApportionmentProject1.setNowDate(costApport.getNowDate());
				    	List<CostApportionmentProject>costApportionmentProjectlist1 = costApportionmentProjectService.selectCostApportionmentProjectList(costApportionmentProject1);
				    	//获取分摊方案科室设置
				    	CostApportionmentProjectDept costApportionmentProjectDept = new CostApportionmentProjectDept();
				    	costApportionmentProjectDept.setProjectCode(costApportionmentProjectlist1.get(0).getProjectInCode());
				    	List<CostApportionmentProjectDept>costApportionmentProjectDeptList = costApportionmentProjectDeptService.selectCostApportionmentProjectDeptList(costApportionmentProjectDept);
				    	//分摊金额
				    	BigDecimal ftje = new BigDecimal(costProjectNum2.getMoney());
				    	//科室比例
				    	BigDecimal ksbl = new BigDecimal(0.0);
				    	//剩余分摊避开
				    	BigDecimal ftbl = new BigDecimal(0.0);
			    		for (CostApportionmentProjectDept costApportionmentProjectDept2 : costApportionmentProjectDeptList) {
			    			if(StringUtils.isNotNull(costApportionmentProjectDept2.getRatio())){
			    				CostApportDetail costApportDetail = new CostApportDetail();
					    		costApportDetail.setApportCode(costApport.getApportCode());
					    		costApportDetail.setDeptCode(costApportionmentProjectDept2.getDeptId());
					    		costApportDetail.setDeptType(deptTypeById(costApportionmentProjectDept2.getDeptId()));
					    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
					    		costApportDetail.setCostType("2");
					    		costApportDetail.setApportType("1");
					    		costApportDetail.setAmount((ftje.multiply(new BigDecimal(costApportionmentProjectDept2.getRatio()))).doubleValue());
					    		costApportDetail.preInsert();
					    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
			    				ksbl = ksbl.add(new BigDecimal(costApportionmentProjectDept2.getRatio()));
			    				fthje = fthje.add(new BigDecimal(costApportDetail.getAmount()));
			    				deptList.add(costApportionmentProjectDept2.getDeptId()+"");
			    			}
						}
			    		ftbl = new BigDecimal(1).subtract(ksbl);
				    	ftje = ftje.multiply(ftbl);
				    	//获取分摊方案比例设置
				    	CostApportionmentProjectRatio costApportionmentProjectRatio = new CostApportionmentProjectRatio();
				    	costApportionmentProjectRatio.setProjectCode(costApportionmentProjectlist1.get(0).getProjectInCode());
				    	List<CostApportionmentProjectRatio>costApportionmentProjectRatioList = costApportionmentProjectRatioService.selectCostApportionmentProjectRatioList(costApportionmentProjectRatio);
				    	for (CostApportionmentProjectRatio costApportionmentProjectRatio2 : costApportionmentProjectRatioList) {
							if(StringUtils.isNotNull(costApportionmentProjectRatio2.getRatio())){
								//分摊比例金额
						    	BigDecimal ftblje = new BigDecimal(0.0);
						    	//分摊总数
						    	BigDecimal ftzs = new BigDecimal(0.0);
						    	ftblje = ftje.multiply(new BigDecimal(costApportionmentProjectRatio2.getRatio()));
								CostApportionmentDictionaryDetail costApportionmentDictionaryDetail = new CostApportionmentDictionaryDetail();
								costApportionmentDictionaryDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
								costApportionmentDictionaryDetail.setLevelType("4");
								costApportionmentDictionaryDetail.setNy(sdf.format(costApport.getNowDate()));
								List<CostApportionmentDictionaryDetail>costApportionmentDictionaryDetailList = costApportionmentDictionaryDetailService.selectCostApportionmentDictionaryDetailList(costApportionmentDictionaryDetail);
								for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail1 : costApportionmentDictionaryDetailList) {
									if(deptList.contains(costApportionmentDictionaryDetail1.getDeptCode())){
										
									}else{
										ftzs = ftzs.add(new BigDecimal(costApportionmentDictionaryDetail1.getNum()));
									}
								}
								for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail2 : costApportionmentDictionaryDetailList) {
									if(deptList.contains(costApportionmentDictionaryDetail2.getDeptCode())){
									}else{
										//分摊比例
								    	BigDecimal xmftbl = new BigDecimal(0.0);
								    	xmftbl = new BigDecimal(costApportionmentDictionaryDetail2.getNum()).divide(ftzs,4,BigDecimal.ROUND_HALF_UP);
										CostApportDetail costApportDetail = new CostApportDetail();
							    		costApportDetail.setApportCode(costApport.getApportCode());
							    		costApportDetail.setDeptCode(Long.valueOf(costApportionmentDictionaryDetail2.getDeptCode()));
							    		costApportDetail.setDeptType(deptTypeById(Long.valueOf(costApportionmentDictionaryDetail2.getDeptCode())));
							    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
							    		costApportDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
							    		costApportDetail.setCostType("2");
							    		costApportDetail.setApportType("1");
							    		costApportDetail.setAmount(ftblje.multiply(xmftbl).divide(BigDecimal.valueOf(1),2, BigDecimal.ROUND_FLOOR).doubleValue());
							    		costApportDetail.preInsert();
							    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
							    		fthje = fthje.add(new BigDecimal(costApportDetail.getAmount()));
									}
								}
							}
						}
					}
				}
			}
    	}
    	costApport.setBack(fthje.doubleValue());;
    }
    
  //一级分摊
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void yjft(CostApport costApport){
    	BigDecimal fthje  = new BigDecimal(0.0);
    	SimpleDateFormat sdf =  new  SimpleDateFormat("yyyy-MM" );
    	//分摊成本数据
    	CostProjectNum costProjectNum = new CostProjectNum();
    	costProjectNum.setCompCode(costApport.getCompCode());
    	costProjectNum.setDeptType("1");
    	costProjectNum.setNowDate(costApport.getNowDate());
    	List<CostProjectNum>List21 = costProjectNumService.selectCostProjectNumListGroup(costProjectNum);
    	CostProjectNum costProjectNum1 = new CostProjectNum();
    	costProjectNum1.setCompCode(costApport.getCompCode());
    	costProjectNum1.setDeptType("5");
    	costProjectNum1.setNowDate(costApport.getNowDate());
    	List<CostProjectNum>List22 = costProjectNumService.selectCostProjectNumListGroup(costProjectNum1);
    	List<CostProjectNum>List2 = new ArrayList<CostProjectNum>();
    	List2.addAll(List22);
    	List2.addAll(List21);
    	if(List2.size()>0){
    		for (CostProjectNum costProjectNum2 : List2) {
    			List<String>deptList = new ArrayList<String>();
    			CostProject costProject = new CostProject();
				costProject.setCompCode(costApport.getCompCode());
    			costProject.setProjectCode(costProjectNum2.getProjectCode());
    			List<CostProject>list3 = costProjectService.selectCostProjectList(costProject);
				if(list3.size()>0){
					if(list3.get(0).getIsCostCalculate()=="1"||"1".equals(list3.get(0).getIsCostCalculate())){
						//分摊方案
				    	CostApportionmentProject costApportionmentProject1 = new CostApportionmentProject();
				    	//list3.get(0).getCostScheme()计算计入成本分摊方案
				    	//costApportionmentProject1.setProjectCode(list3.get(0).getFirstLevelSql());
				    	costApportionmentProject1.setNowDate(costApport.getNowDate());
				    	List<CostApportionmentProject>costApportionmentProjectlist1 = costApportionmentProjectService.selectCostApportionmentProjectList(costApportionmentProject1);
				    	//获取分摊方案科室设置
				    	CostApportionmentProjectDept costApportionmentProjectDept = new CostApportionmentProjectDept();
				    	costApportionmentProjectDept.setProjectCode(costApportionmentProjectlist1.get(0).getProjectInCode());
				    	List<CostApportionmentProjectDept>costApportionmentProjectDeptList = costApportionmentProjectDeptService.selectCostApportionmentProjectDeptList(costApportionmentProjectDept);
				    	//分摊金额
				    	BigDecimal ftje = new BigDecimal(0.0);
				    	if(StringUtils.isNotNull(costProjectNum2.getMoney())){
					    	ftje = ftje.add(new BigDecimal(costProjectNum2.getMoney()));
				    	}
				    	CostApportDetail costApportDetailTemp111 = new CostApportDetail();
				    	costApportDetailTemp111.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp111.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailTemp111.setDeptType("2");
				    	costApportDetailTemp111.setCostType("1");
				    	CostApportDetail costApportDetailNew111 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp111);
				    	if(StringUtils.isNotNull(costApportDetailNew111)){
				    		fthje = fthje.add(new BigDecimal(costApportDetailNew111.getAmount()));
				    	}
				    	//科室比例
				    	BigDecimal ksbl = new BigDecimal(0.0);
				    	//剩余分摊避开
				    	BigDecimal ftbl = new BigDecimal(0.0);
			    		for (CostApportionmentProjectDept costApportionmentProjectDept2 : costApportionmentProjectDeptList) {
			    			if(StringUtils.isNotNull(costApportionmentProjectDept2.getFirstRatio())){
			    				if(!"0".equals(costApportionmentProjectDept2.getFirstRatio())){
			    					CostApportDetail costApportDetail = new CostApportDetail();
						    		costApportDetail.setApportCode(costApport.getApportCode());
						    		costApportDetail.setDeptCode(costApportionmentProjectDept2.getDeptId());
						    		costApportDetail.setDeptType(deptTypeById(costApportionmentProjectDept2.getDeptId()));
						    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
						    		costApportDetail.setCostType("2");
						    		costApportDetail.setApportType("2");
						    		costApportDetail.setAmount(ftje.multiply(new BigDecimal(costApportionmentProjectDept2.getFirstRatio())).doubleValue());
						    		costApportDetail.preInsert();
						    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
				    				ksbl = ksbl.add(new BigDecimal(costApportionmentProjectDept2.getFirstRatio()));
				    				fthje = fthje.add(new BigDecimal(costApportDetail.getAmount()));
				    				deptList.add(costApportionmentProjectDept2.getDeptId()+"");
			    				}
			    				
			    			}
						}
			    		ftbl = new BigDecimal(1).subtract(ksbl);
				    	ftje = ftje.multiply(ftbl);
				    	//获取分摊方案比例设置
				    	CostApportionmentProjectRatio costApportionmentProjectRatio = new CostApportionmentProjectRatio();
				    	costApportionmentProjectRatio.setProjectCode(costApportionmentProjectlist1.get(0).getProjectInCode());
				    	List<CostApportionmentProjectRatio>costApportionmentProjectRatioList = costApportionmentProjectRatioService.selectCostApportionmentProjectRatioList(costApportionmentProjectRatio);
				    	for (CostApportionmentProjectRatio costApportionmentProjectRatio2 : costApportionmentProjectRatioList) {
							if(StringUtils.isNotNull(costApportionmentProjectRatio2.getFirstRatio())){
								//分摊比例金额
						    	BigDecimal ftblje = new BigDecimal(0.0);
						    	//分摊总数
						    	BigDecimal ftzs = new BigDecimal(0.0);
						    	ftblje = ftje.multiply(new BigDecimal(costApportionmentProjectRatio2.getFirstRatio()));
								CostApportionmentDictionaryDetail costApportionmentDictionaryDetail = new CostApportionmentDictionaryDetail();
								costApportionmentDictionaryDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
								costApportionmentDictionaryDetail.setLevelType("1");
								costApportionmentDictionaryDetail.setNy(sdf.format(costApport.getNowDate()));
								List<CostApportionmentDictionaryDetail>costApportionmentDictionaryDetailList = costApportionmentDictionaryDetailService.selectCostApportionmentDictionaryDetailList(costApportionmentDictionaryDetail);
								for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail1 : costApportionmentDictionaryDetailList) {
									if(deptList.contains(costApportionmentDictionaryDetail1.getDeptCode())){
										
									}else{
										ftzs = ftzs.add(new BigDecimal(costApportionmentDictionaryDetail1.getNum()));
									}
								}
								for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail2 : costApportionmentDictionaryDetailList) {
									if(deptList.contains(costApportionmentDictionaryDetail2.getDeptCode())){
									}else{
										//分摊比例
								    	BigDecimal xmftbl = new BigDecimal(0.0);
								    	xmftbl = new BigDecimal(costApportionmentDictionaryDetail2.getNum()).divide(ftzs,4,BigDecimal.ROUND_HALF_UP);
										CostApportDetail costApportDetail = new CostApportDetail();
							    		costApportDetail.setApportCode(costApport.getApportCode());
							    		costApportDetail.setDeptCode(Long.valueOf(costApportionmentDictionaryDetail2.getDeptCode()));
							    		costApportDetail.setDeptType(deptTypeById(Long.valueOf(costApportionmentDictionaryDetail2.getDeptCode())));
							    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
							    		costApportDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
							    		costApportDetail.setCostType("2");
							    		costApportDetail.setApportType("2");
							    		costApportDetail.setAmount(ftblje.multiply(xmftbl).doubleValue());
							    		costApportDetail.preInsert();
							    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
							    		fthje = fthje.add(new BigDecimal(costApportDetail.getAmount()));
									}
								}
							}
						}
					}else if(list3.get(0).getIsCostCalculate()=="0"||"0".equals(list3.get(0).getIsCostCalculate())){

						//分摊方案
				    	CostApportionmentProject costApportionmentProject1 = new CostApportionmentProject();
				    	//list3.get(0).getCostScheme()计算计入成本分摊方案
				    	//costApportionmentProject1.setProjectCode(list3.get(0).getFirstLevelSql());
				    	costApportionmentProject1.setNowDate(costApport.getNowDate());
				    	List<CostApportionmentProject>costApportionmentProjectlist1 = costApportionmentProjectService.selectCostApportionmentProjectList(costApportionmentProject1);
				    	//获取分摊方案科室设置
				    	CostApportionmentProjectDept costApportionmentProjectDept = new CostApportionmentProjectDept();
				    	costApportionmentProjectDept.setProjectCode(costApportionmentProjectlist1.get(0).getProjectInCode());
				    	List<CostApportionmentProjectDept>costApportionmentProjectDeptList = costApportionmentProjectDeptService.selectCostApportionmentProjectDeptList(costApportionmentProjectDept);
				    	//分摊金额
				    	BigDecimal ftje = new BigDecimal(0.0);
				    	/*if(StringUtils.isNotNull(costProjectNum2.getMoney())){
					    	ftje = ftje.add(new BigDecimal(costProjectNum2.getMoney()));
				    	}*/
				    	CostApportDetail costApportDetailNew2 = new CostApportDetail();
				    	CostApportDetail costApportDetailTemp2 = new CostApportDetail();
				    	costApportDetailTemp2.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp2.setApportType("1");
				    	costApportDetailTemp2.setDeptType("1");
				    	costApportDetailTemp2.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailNew2 = costApportDetailMapper.selectCostApportFtje(costApportDetailTemp2);
				    	if(StringUtils.isNotNull(costApportDetailNew2)){
					    	ftje = ftje.add(new BigDecimal(costApportDetailNew2.getAmount()));
				    	}
				    	CostApportDetail costApportDetailTemp112 = new CostApportDetail();
				    	costApportDetailTemp112.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp112.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailTemp112.setApportType("1");
				    	costApportDetailTemp112.setCostType("2");
				    	costApportDetailTemp112.setDeptType("2");
				    	CostApportDetail costApportDetailNew112 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp112);
				    	if(StringUtils.isNotNull(costApportDetailNew112)){
				    		fthje = fthje.add(new BigDecimal(costApportDetailNew112.getAmount()));
				    	}
				    	//科室比例
				    	BigDecimal ksbl = new BigDecimal(0.0);
				    	//剩余分摊避开
				    	BigDecimal ftbl = new BigDecimal(0.0);
			    		for (CostApportionmentProjectDept costApportionmentProjectDept2 : costApportionmentProjectDeptList) {
			    			if(StringUtils.isNotNull(costApportionmentProjectDept2.getFirstRatio())){
			    				if(!"0".equals(costApportionmentProjectDept2.getFirstRatio())){
			    					CostApportDetail costApportDetail = new CostApportDetail();
						    		costApportDetail.setApportCode(costApport.getApportCode());
						    		costApportDetail.setDeptCode(costApportionmentProjectDept2.getDeptId());
						    		costApportDetail.setDeptType(deptTypeById(costApportionmentProjectDept2.getDeptId()));
						    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
						    		costApportDetail.setCostType("2");
						    		costApportDetail.setApportType("2");
						    		costApportDetail.setAmount(ftje.multiply(new BigDecimal(costApportionmentProjectDept2.getFirstRatio())).doubleValue());
						    		costApportDetail.preInsert();
						    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
				    				ksbl = ksbl.add(new BigDecimal(costApportionmentProjectDept2.getFirstRatio()));
				    				fthje = fthje.add(new BigDecimal(costApportDetail.getAmount()));
				    				deptList.add(costApportionmentProjectDept2.getDeptId()+"");
			    				}
			    				
			    			}
						}
			    		ftbl = new BigDecimal(1).subtract(ksbl);
				    	ftje = ftje.multiply(ftbl);
				    	//获取分摊方案比例设置
				    	CostApportionmentProjectRatio costApportionmentProjectRatio = new CostApportionmentProjectRatio();
				    	costApportionmentProjectRatio.setProjectCode(costApportionmentProjectlist1.get(0).getProjectInCode());
				    	List<CostApportionmentProjectRatio>costApportionmentProjectRatioList = costApportionmentProjectRatioService.selectCostApportionmentProjectRatioList(costApportionmentProjectRatio);
				    	for (CostApportionmentProjectRatio costApportionmentProjectRatio2 : costApportionmentProjectRatioList) {
							if(StringUtils.isNotNull(costApportionmentProjectRatio2.getFirstRatio())){
								//分摊比例金额
						    	BigDecimal ftblje = new BigDecimal(0.0);
						    	//分摊总数
						    	BigDecimal ftzs = new BigDecimal(0.0);
						    	ftblje = ftje.multiply(new BigDecimal(costApportionmentProjectRatio2.getFirstRatio()));
								CostApportionmentDictionaryDetail costApportionmentDictionaryDetail = new CostApportionmentDictionaryDetail();
								costApportionmentDictionaryDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
								costApportionmentDictionaryDetail.setLevelType("1");
								costApportionmentDictionaryDetail.setNy(sdf.format(costApport.getNowDate()));
								List<CostApportionmentDictionaryDetail>costApportionmentDictionaryDetailList = costApportionmentDictionaryDetailService.selectCostApportionmentDictionaryDetailList(costApportionmentDictionaryDetail);
								for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail1 : costApportionmentDictionaryDetailList) {
									if(deptList.contains(costApportionmentDictionaryDetail1.getDeptCode())){
										
									}else{
										ftzs = ftzs.add(new BigDecimal(costApportionmentDictionaryDetail1.getNum()));
									}
								}
								for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail2 : costApportionmentDictionaryDetailList) {
									if(deptList.contains(costApportionmentDictionaryDetail2.getDeptCode())){
									}else{
										//分摊比例
								    	BigDecimal xmftbl = new BigDecimal(0.0);
								    	xmftbl = new BigDecimal(costApportionmentDictionaryDetail2.getNum()).divide(ftzs,4,BigDecimal.ROUND_HALF_UP);
										CostApportDetail costApportDetail = new CostApportDetail();
							    		costApportDetail.setApportCode(costApport.getApportCode());
							    		costApportDetail.setDeptCode(Long.valueOf(costApportionmentDictionaryDetail2.getDeptCode()));
							    		costApportDetail.setDeptType(deptTypeById(Long.valueOf(costApportionmentDictionaryDetail2.getDeptCode())));
							    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
							    		costApportDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
							    		costApportDetail.setCostType("2");
							    		costApportDetail.setApportType("2");
							    		costApportDetail.setAmount(ftblje.multiply(xmftbl).doubleValue());
							    		costApportDetail.preInsert();
							    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
							    		fthje = fthje.add(new BigDecimal(costApportDetail.getAmount()));
									}
								}
							}
						}
					
					}
				}
			}
    	}
    	costApport.setBack(fthje.doubleValue());;
    }

    
  //二级分摊
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ejft(CostApport costApport){
    	BigDecimal fthje  = new BigDecimal(0.0);
    	SimpleDateFormat sdf =  new  SimpleDateFormat("yyyy-MM" );
    	//分摊成本数据
    	CostProjectNum costProjectNum = new CostProjectNum();
    	costProjectNum.setCompCode(costApport.getCompCode());
    	costProjectNum.setNowDate(costApport.getNowDate());
    	List<CostProjectNum>List2 = costProjectNumService.selectCostProjectNumListGroup(costProjectNum);
    	if(List2.size()>0){
    		for (CostProjectNum costProjectNum2 : List2) {
    			List<String>deptList = new ArrayList<String>();
    			CostProject costProject = new CostProject();
				costProject.setCompCode(costApport.getCompCode());
    			costProject.setProjectCode(costProjectNum2.getProjectCode());
    			List<CostProject>list3 = costProjectService.selectCostProjectList(costProject);
				if(list3.size()>0){
					if(list3.get(0).getIsCostCalculate()=="1"||"1".equals(list3.get(0).getIsCostCalculate())){
						//分摊方案
				    	CostApportionmentProject costApportionmentProject1 = new CostApportionmentProject();
				    	//list3.get(0).getCostScheme()计算计入成本分摊方案
				    	//costApportionmentProject1.setProjectCode(list3.get(0).getSecondLevelSql());
				    	costApportionmentProject1.setNowDate(costApport.getNowDate());
				    	List<CostApportionmentProject>costApportionmentProjectlist1 = costApportionmentProjectService.selectCostApportionmentProjectList(costApportionmentProject1);
				    	//获取分摊方案科室设置
				    	CostApportionmentProjectDept costApportionmentProjectDept = new CostApportionmentProjectDept();
				    	costApportionmentProjectDept.setProjectCode(costApportionmentProjectlist1.get(0).getProjectInCode());
				    	List<CostApportionmentProjectDept>costApportionmentProjectDeptList = costApportionmentProjectDeptService.selectCostApportionmentProjectDeptList(costApportionmentProjectDept);
				    	//分摊金额
				    	BigDecimal ftje = new BigDecimal(0.0);
				    	CostApportDetail costApportDetailNew1 = new CostApportDetail();
				    	CostApportDetail costApportDetailTemp1 = new CostApportDetail();
				    	costApportDetailTemp1.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp1.setDeptType("2");
				    	costApportDetailTemp1.setCostType("1");
				    	costApportDetailTemp1.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailNew1 = costApportDetailMapper.selectCostApportFtje(costApportDetailTemp1);
				    	if(StringUtils.isNotNull(costApportDetailNew1)){
					    	ftje = ftje.add(new BigDecimal(costApportDetailNew1.getAmount()));
				    	}
				    	
				    	CostApportDetail costApportDetailNew2 = new CostApportDetail();
				    	CostApportDetail costApportDetailTemp2 = new CostApportDetail();
				    	costApportDetailTemp2.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp2.setCostType("2");
				    	costApportDetailTemp2.setDeptType("2");
				    	costApportDetailTemp2.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailNew2 = costApportDetailMapper.selectCostApportFtje(costApportDetailTemp2);
				    	if(StringUtils.isNotNull(costApportDetailNew2)){
					    	ftje = ftje.add(new BigDecimal(costApportDetailNew2.getAmount()));
				    	}
				    	
				    	CostApportDetail costApportDetailTemp111 = new CostApportDetail();
				    	costApportDetailTemp111.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp111.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailTemp111.setDeptType("3");
				    	costApportDetailTemp111.setCostType("1");
				    	CostApportDetail costApportDetailNew111 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp111);
				    	if(StringUtils.isNotNull(costApportDetailNew111)){
				    		fthje = fthje.add(new BigDecimal(costApportDetailNew111.getAmount()));
				    	}
				    	
				    	CostApportDetail costApportDetailTemp112 = new CostApportDetail();
				    	costApportDetailTemp112.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp112.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailTemp112.setDeptType("3");
				    	costApportDetailTemp112.setCostType("2");
				    	CostApportDetail costApportDetailNew112 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp112);
				    	if(StringUtils.isNotNull(costApportDetailNew112)){
				    		fthje = fthje.add(new BigDecimal(costApportDetailNew112.getAmount()));
				    	}
				    	//科室比例
				    	BigDecimal ksbl = new BigDecimal(0.0);
				    	//剩余分摊避开
				    	BigDecimal ftbl = new BigDecimal(0.0);
			    		for (CostApportionmentProjectDept costApportionmentProjectDept2 : costApportionmentProjectDeptList) {
			    			if(StringUtils.isNotNull(costApportionmentProjectDept2.getSecondRatio())){
			    				if(!"0".equals(costApportionmentProjectDept2.getSecondRatio())){
				    				CostApportDetail costApportDetail = new CostApportDetail();
						    		costApportDetail.setApportCode(costApport.getApportCode());
						    		costApportDetail.setDeptCode(costApportionmentProjectDept2.getDeptId());
						    		costApportDetail.setDeptType(deptTypeById(costApportionmentProjectDept2.getDeptId()));
						    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
						    		costApportDetail.setCostType("2");
						    		costApportDetail.setApportType("3");
						    		costApportDetail.setAmount(ftje.multiply(new BigDecimal(costApportionmentProjectDept2.getSecondRatio())).doubleValue());
						    		costApportDetail.preInsert();
						    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
				    				ksbl = ksbl.add(new BigDecimal(costApportionmentProjectDept2.getSecondRatio()));
				    				fthje = fthje.add(new BigDecimal(costApportDetail.getAmount()));
				    				deptList.add(costApportionmentProjectDept2.getDeptId()+"");
			    				}
			    			}
						}
			    		ftbl = new BigDecimal(1).subtract(ksbl);
				    	ftje = ftje.multiply(ftbl);
				    	//获取分摊方案比例设置
				    	CostApportionmentProjectRatio costApportionmentProjectRatio = new CostApportionmentProjectRatio();
				    	costApportionmentProjectRatio.setProjectCode(costApportionmentProjectlist1.get(0).getProjectInCode());
				    	List<CostApportionmentProjectRatio>costApportionmentProjectRatioList = costApportionmentProjectRatioService.selectCostApportionmentProjectRatioList(costApportionmentProjectRatio);
				    	for (CostApportionmentProjectRatio costApportionmentProjectRatio2 : costApportionmentProjectRatioList) {
							if(StringUtils.isNotNull(costApportionmentProjectRatio2.getSecondRatio())){
								//分摊比例金额
						    	BigDecimal ftblje = new BigDecimal(0.0);
						    	//分摊总数
						    	BigDecimal ftzs = new BigDecimal(0.0);
						    	ftblje = ftje.multiply(new BigDecimal(costApportionmentProjectRatio2.getSecondRatio()));
								CostApportionmentDictionaryDetail costApportionmentDictionaryDetail = new CostApportionmentDictionaryDetail();
								costApportionmentDictionaryDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
								costApportionmentDictionaryDetail.setLevelType("2");
								costApportionmentDictionaryDetail.setNy(sdf.format(costApport.getNowDate()));
								List<CostApportionmentDictionaryDetail>costApportionmentDictionaryDetailList = costApportionmentDictionaryDetailService.selectCostApportionmentDictionaryDetailList(costApportionmentDictionaryDetail);
								for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail1 : costApportionmentDictionaryDetailList) {
									if(deptList.contains(costApportionmentDictionaryDetail1.getDeptCode())){
										
									}else{
										ftzs = ftzs.add(new BigDecimal(costApportionmentDictionaryDetail1.getNum()));
									}
								}
								for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail2 : costApportionmentDictionaryDetailList) {
										if(deptList.contains(costApportionmentDictionaryDetail2.getDeptCode())){
										}else{
											//分摊比例
									    	BigDecimal xmftbl = new BigDecimal(0.0);
									    	xmftbl = new BigDecimal(costApportionmentDictionaryDetail2.getNum()).divide(ftzs,4,BigDecimal.ROUND_HALF_UP);
											CostApportDetail costApportDetail = new CostApportDetail();
								    		costApportDetail.setApportCode(costApport.getApportCode());
								    		costApportDetail.setDeptCode(Long.valueOf(costApportionmentDictionaryDetail2.getDeptCode()));
								    		costApportDetail.setDeptType(deptTypeById(Long.valueOf(costApportionmentDictionaryDetail2.getDeptCode())));
								    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
								    		costApportDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
								    		costApportDetail.setCostType("2");
								    		costApportDetail.setApportType("3");
								    		costApportDetail.setAmount(ftblje.multiply(xmftbl).doubleValue());
								    		costApportDetail.preInsert();
								    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
								    		fthje = fthje.add(new BigDecimal(costApportDetail.getAmount()));
										}
								}
							}
						}
					}else if(list3.get(0).getIsCostCalculate()=="0"||"0".equals(list3.get(0).getIsCostCalculate())){

						//分摊方案
				    	CostApportionmentProject costApportionmentProject1 = new CostApportionmentProject();
				    	//list3.get(0).getCostScheme()计算计入成本分摊方案
				    	//costApportionmentProject1.setProjectCode(list3.get(0).getSecondLevelSql());
				    	costApportionmentProject1.setNowDate(costApport.getNowDate());
				    	List<CostApportionmentProject>costApportionmentProjectlist1 = costApportionmentProjectService.selectCostApportionmentProjectList(costApportionmentProject1);
				    	//获取分摊方案科室设置
				    	CostApportionmentProjectDept costApportionmentProjectDept = new CostApportionmentProjectDept();
				    	costApportionmentProjectDept.setProjectCode(costApportionmentProjectlist1.get(0).getProjectInCode());
				    	List<CostApportionmentProjectDept>costApportionmentProjectDeptList = costApportionmentProjectDeptService.selectCostApportionmentProjectDeptList(costApportionmentProjectDept);
				    	//分摊金额
				    	BigDecimal ftje = new BigDecimal(0.0);
				    	CostApportDetail costApportDetailNew1 = new CostApportDetail();
				    	CostApportDetail costApportDetailTemp1 = new CostApportDetail();
				    	costApportDetailTemp1.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp1.setDeptType("2");
				    	costApportDetailTemp1.setCostType("2");
				    	costApportDetailTemp1.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailNew1 = costApportDetailMapper.selectCostApportFtje(costApportDetailTemp1);
				    	if(StringUtils.isNotNull(costApportDetailNew1)){
					    	ftje = ftje.add(new BigDecimal(costApportDetailNew1.getAmount()));
				    	}
				    	/*CostApportDetail costApportDetailNew2 = new CostApportDetail();
				    	CostApportDetail costApportDetailTemp2 = new CostApportDetail();
				    	costApportDetailTemp2.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp2.setApportType("2");
				    	costApportDetailTemp2.setDeptType("2");
				    	costApportDetailTemp2.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailNew2 = costApportDetailMapper.selectCostApportFtje(costApportDetailTemp2);
				    	if(StringUtils.isNotNull(costApportDetailNew2)){
					    	ftje = ftje.add(new BigDecimal(costApportDetailNew2.getAmount()));
				    	}*/
				    	CostApportDetail costApportDetailTemp112 = new CostApportDetail();
				    	costApportDetailTemp112.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp112.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailTemp112.setCostType("2");
				    	costApportDetailTemp112.setDeptType("3");
				    	CostApportDetail costApportDetailNew112 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp112);
				    	if(StringUtils.isNotNull(costApportDetailNew112)){
				    		fthje = fthje.add(new BigDecimal(costApportDetailNew112.getAmount()));
				    	}
				    	
				    	//科室比例
				    	BigDecimal ksbl = new BigDecimal(0.0);
				    	//剩余分摊避开
				    	BigDecimal ftbl = new BigDecimal(0.0);
			    		for (CostApportionmentProjectDept costApportionmentProjectDept2 : costApportionmentProjectDeptList) {
			    			if(StringUtils.isNotNull(costApportionmentProjectDept2.getSecondRatio())){
			    				if(!"0".equals(costApportionmentProjectDept2.getSecondRatio())){
				    				CostApportDetail costApportDetail = new CostApportDetail();
						    		costApportDetail.setApportCode(costApport.getApportCode());
						    		costApportDetail.setDeptCode(costApportionmentProjectDept2.getDeptId());
						    		costApportDetail.setDeptType(deptTypeById(costApportionmentProjectDept2.getDeptId()));
						    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
						    		costApportDetail.setCostType("2");
						    		costApportDetail.setApportType("3");
						    		costApportDetail.setAmount(ftje.multiply(new BigDecimal(costApportionmentProjectDept2.getSecondRatio())).doubleValue());
						    		costApportDetail.preInsert();
						    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
				    				ksbl = ksbl.add(new BigDecimal(costApportionmentProjectDept2.getSecondRatio()));
				    				fthje = fthje.add(new BigDecimal(costApportDetail.getAmount()));
				    				deptList.add(costApportionmentProjectDept2.getDeptId()+"");
			    				}
			    			}
						}
			    		ftbl = new BigDecimal(1).subtract(ksbl);
				    	ftje = ftje.multiply(ftbl);
				    	//获取分摊方案比例设置
				    	CostApportionmentProjectRatio costApportionmentProjectRatio = new CostApportionmentProjectRatio();
				    	costApportionmentProjectRatio.setProjectCode(costApportionmentProjectlist1.get(0).getProjectInCode());
				    	List<CostApportionmentProjectRatio>costApportionmentProjectRatioList = costApportionmentProjectRatioService.selectCostApportionmentProjectRatioList(costApportionmentProjectRatio);
				    	for (CostApportionmentProjectRatio costApportionmentProjectRatio2 : costApportionmentProjectRatioList) {
							if(StringUtils.isNotNull(costApportionmentProjectRatio2.getSecondRatio())){
								//分摊比例金额
						    	BigDecimal ftblje = new BigDecimal(0.0);
						    	//分摊总数
						    	BigDecimal ftzs = new BigDecimal(0.0);
						    	ftblje = ftje.multiply(new BigDecimal(costApportionmentProjectRatio2.getSecondRatio()));
								CostApportionmentDictionaryDetail costApportionmentDictionaryDetail = new CostApportionmentDictionaryDetail();
								costApportionmentDictionaryDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
								costApportionmentDictionaryDetail.setLevelType("2");
								costApportionmentDictionaryDetail.setNy(sdf.format(costApport.getNowDate()));
								List<CostApportionmentDictionaryDetail>costApportionmentDictionaryDetailList = costApportionmentDictionaryDetailService.selectCostApportionmentDictionaryDetailList(costApportionmentDictionaryDetail);
								for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail1 : costApportionmentDictionaryDetailList) {
									if(deptList.contains(costApportionmentDictionaryDetail1.getDeptCode())){
										
									}else{
										ftzs = ftzs.add(new BigDecimal(costApportionmentDictionaryDetail1.getNum()));
									}
								}
								for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail2 : costApportionmentDictionaryDetailList) {
									if(deptList.contains(costApportionmentDictionaryDetail2.getDeptCode())){
									}else{
										//分摊比例
								    	BigDecimal xmftbl = new BigDecimal(0.0);
								    	xmftbl = new BigDecimal(costApportionmentDictionaryDetail2.getNum()).divide(ftzs,4,BigDecimal.ROUND_HALF_UP);
										CostApportDetail costApportDetail = new CostApportDetail();
							    		costApportDetail.setApportCode(costApport.getApportCode());
							    		costApportDetail.setDeptCode(Long.valueOf(costApportionmentDictionaryDetail2.getDeptCode()));
							    		costApportDetail.setDeptType(deptTypeById(Long.valueOf(costApportionmentDictionaryDetail2.getDeptCode())));
							    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
							    		costApportDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
							    		costApportDetail.setCostType("2");
							    		costApportDetail.setApportType("3");
							    		costApportDetail.setAmount(ftblje.multiply(xmftbl).doubleValue());
							    		costApportDetail.preInsert();
							    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
							    		fthje = fthje.add(new BigDecimal(costApportDetail.getAmount()));
									}
								}
							}
						}
					
					}
				}
			}
    	}
    	costApport.setBack(fthje.doubleValue());;
    }
    
  //三级分摊
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sjft(CostApport costApport){
    	BigDecimal fthje  = new BigDecimal(0.0);
    	SimpleDateFormat sdf =  new  SimpleDateFormat("yyyy-MM" );
    	//分摊成本数据
    	CostProjectNum costProjectNum = new CostProjectNum();
    	costProjectNum.setCompCode(costApport.getCompCode());
    	costProjectNum.setNowDate(costApport.getNowDate());
    	List<CostProjectNum>List2 = costProjectNumService.selectCostProjectNumListGroup(costProjectNum);
    	if(List2.size()>0){
    		for (CostProjectNum costProjectNum2 : List2) {
    			List<String>deptList = new ArrayList<String>();
    			CostProject costProject = new CostProject();
				costProject.setCompCode(costApport.getCompCode());
    			costProject.setProjectCode(costProjectNum2.getProjectCode());
    			List<CostProject>list3 = costProjectService.selectCostProjectList(costProject);
				if(list3.size()>0){
					if(list3.get(0).getIsCostCalculate()=="1"||"1".equals(list3.get(0).getIsCostCalculate())){
						//分摊方案
				    	CostApportionmentProject costApportionmentProject1 = new CostApportionmentProject();
				    	//list3.get(0).getCostScheme()计算计入成本分摊方案
				    	//costApportionmentProject1.setProjectCode(list3.get(0).getThirdLevelSql());
				    	costApportionmentProject1.setNowDate(costApport.getNowDate());
				    	List<CostApportionmentProject>costApportionmentProjectlist1 = costApportionmentProjectService.selectCostApportionmentProjectList(costApportionmentProject1);
				    	//获取分摊方案科室设置
				    	CostApportionmentProjectDept costApportionmentProjectDept = new CostApportionmentProjectDept();
				    	costApportionmentProjectDept.setProjectCode(costApportionmentProjectlist1.get(0).getProjectInCode());
				    	List<CostApportionmentProjectDept>costApportionmentProjectDeptList = costApportionmentProjectDeptService.selectCostApportionmentProjectDeptList(costApportionmentProjectDept);
				    	//分摊金额
				    	BigDecimal ftje = new BigDecimal(0.0);
				    	CostApportDetail costApportDetailNew1 = new CostApportDetail();
				    	CostApportDetail costApportDetailTemp1 = new CostApportDetail();
				    	costApportDetailTemp1.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp1.setDeptType("3");
				    	costApportDetailTemp1.setCostType("1");
				    	costApportDetailTemp1.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailNew1 = costApportDetailMapper.selectCostApportFtje(costApportDetailTemp1);
				    	if(StringUtils.isNotNull(costApportDetailNew1)){
					    	ftje = ftje.add(new BigDecimal(costApportDetailNew1.getAmount()));
				    	}
				    	
				    	CostApportDetail costApportDetailNew2 = new CostApportDetail();
				    	CostApportDetail costApportDetailTemp2 = new CostApportDetail();
				    	costApportDetailTemp2.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp2.setDeptType("3");
				    	costApportDetailTemp2.setCostType("2");
				    	costApportDetailTemp2.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailNew2 = costApportDetailMapper.selectCostApportFtje(costApportDetailTemp2);
				    	if(StringUtils.isNotNull(costApportDetailNew2)){
					    	ftje = ftje.add(new BigDecimal(costApportDetailNew2.getAmount()));
				    	}
				    	CostApportDetail costApportDetailTemp111 = new CostApportDetail();
				    	costApportDetailTemp111.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp111.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailTemp111.setDeptType("4");
				    	costApportDetailTemp111.setCostType("1");
				    	CostApportDetail costApportDetailNew111 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp111);
				    	if(StringUtils.isNotNull(costApportDetailNew111)){
				    		fthje = fthje.add(new BigDecimal(costApportDetailNew111.getAmount()));
				    	}
				    	
				    	CostApportDetail costApportDetailTemp112 = new CostApportDetail();
				    	costApportDetailTemp112.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp112.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailTemp112.setDeptType("4");
				    	costApportDetailTemp112.setCostType("2");
				    	CostApportDetail costApportDetailNew112 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp112);
				    	if(StringUtils.isNotNull(costApportDetailNew112)){
				    		fthje = fthje.add(new BigDecimal(costApportDetailNew112.getAmount()));
				    	}
				    	//科室比例
				    	BigDecimal ksbl = new BigDecimal(0.0);
				    	//剩余分摊避开
				    	BigDecimal ftbl = new BigDecimal(0.0);
			    		for (CostApportionmentProjectDept costApportionmentProjectDept2 : costApportionmentProjectDeptList) {
			    			if(StringUtils.isNotNull(costApportionmentProjectDept2.getThirdRatio())){
			    				if(!"0".equals(costApportionmentProjectDept2.getSecondRatio())){
				    				CostApportDetail costApportDetail = new CostApportDetail();
						    		costApportDetail.setApportCode(costApport.getApportCode());
						    		costApportDetail.setDeptCode(costApportionmentProjectDept2.getDeptId());
						    		costApportDetail.setDeptType(deptTypeById(costApportionmentProjectDept2.getDeptId()));
						    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
						    		costApportDetail.setCostType("2");
						    		costApportDetail.setApportType("4");
						    		costApportDetail.setAmount(ftje.multiply(new BigDecimal(costApportionmentProjectDept2.getThirdRatio())).doubleValue());
						    		costApportDetail.preInsert();
						    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
				    				ksbl = ksbl.add(new BigDecimal(costApportionmentProjectDept2.getThirdRatio()));
				    				fthje = fthje.add(new BigDecimal(costApportDetail.getAmount()));
				    				deptList.add(costApportionmentProjectDept2.getDeptId()+"");
			    				}
			    			}
						}
			    		ftbl = new BigDecimal(1).subtract(ksbl);
				    	ftje = ftje.multiply(ftbl);
				    	//获取分摊方案比例设置
				    	CostApportionmentProjectRatio costApportionmentProjectRatio = new CostApportionmentProjectRatio();
				    	costApportionmentProjectRatio.setProjectCode(costApportionmentProjectlist1.get(0).getProjectInCode());
				    	List<CostApportionmentProjectRatio>costApportionmentProjectRatioList = costApportionmentProjectRatioService.selectCostApportionmentProjectRatioList(costApportionmentProjectRatio);
				    	for (CostApportionmentProjectRatio costApportionmentProjectRatio2 : costApportionmentProjectRatioList) {
							if(StringUtils.isNotNull(costApportionmentProjectRatio2.getThirdRatio())){
								//分摊比例金额
						    	BigDecimal ftblje = new BigDecimal(0.0);
						    	//分摊总数
						    	BigDecimal ftzs = new BigDecimal(0.0);
						    	ftblje = ftje.multiply(new BigDecimal(costApportionmentProjectRatio2.getThirdRatio()));
								CostApportionmentDictionaryDetail costApportionmentDictionaryDetail = new CostApportionmentDictionaryDetail();
								costApportionmentDictionaryDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
								costApportionmentDictionaryDetail.setLevelType("3");
								costApportionmentDictionaryDetail.setNy(sdf.format(costApport.getNowDate()));
								List<CostApportionmentDictionaryDetail>costApportionmentDictionaryDetailList = costApportionmentDictionaryDetailService.selectCostApportionmentDictionaryDetailList(costApportionmentDictionaryDetail);
								for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail1 : costApportionmentDictionaryDetailList) {
									if(deptList.contains(costApportionmentDictionaryDetail1.getDeptCode())){
										
									}else{
										ftzs = ftzs.add(new BigDecimal(costApportionmentDictionaryDetail1.getNum()));
									}
								}

								for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail2 : costApportionmentDictionaryDetailList) {
									if(deptList.contains(costApportionmentDictionaryDetail2.getDeptCode())){
									}else{
										//分摊比例
								    	BigDecimal xmftbl = new BigDecimal(0.0);
								    	xmftbl = new BigDecimal(costApportionmentDictionaryDetail2.getNum()).divide(ftzs,4,BigDecimal.ROUND_HALF_UP);
										CostApportDetail costApportDetail = new CostApportDetail();
							    		costApportDetail.setApportCode(costApport.getApportCode());
							    		costApportDetail.setDeptCode(Long.valueOf(costApportionmentDictionaryDetail2.getDeptCode()));
							    		costApportDetail.setDeptType(deptTypeById(Long.valueOf(costApportionmentDictionaryDetail2.getDeptCode())));
							    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
							    		costApportDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
							    		costApportDetail.setCostType("2");
							    		costApportDetail.setApportType("4");
							    		costApportDetail.setAmount(ftblje.multiply(xmftbl).doubleValue());
							    		costApportDetail.preInsert();
							    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
							    		fthje = fthje.add(new BigDecimal(costApportDetail.getAmount()));
									}
								}
							}
						}
					}else if(list3.get(0).getIsCostCalculate()=="0"||"0".equals(list3.get(0).getIsCostCalculate())){

						//分摊方案
				    	CostApportionmentProject costApportionmentProject1 = new CostApportionmentProject();
				    	//list3.get(0).getCostScheme()计算计入成本分摊方案
				    	//costApportionmentProject1.setProjectCode(list3.get(0).getThirdLevelSql());
				    	costApportionmentProject1.setNowDate(costApport.getNowDate());
				    	List<CostApportionmentProject>costApportionmentProjectlist1 = costApportionmentProjectService.selectCostApportionmentProjectList(costApportionmentProject1);
				    	//获取分摊方案科室设置
				    	CostApportionmentProjectDept costApportionmentProjectDept = new CostApportionmentProjectDept();
				    	costApportionmentProjectDept.setProjectCode(costApportionmentProjectlist1.get(0).getProjectInCode());
				    	List<CostApportionmentProjectDept>costApportionmentProjectDeptList = costApportionmentProjectDeptService.selectCostApportionmentProjectDeptList(costApportionmentProjectDept);
				    	//分摊金额
				    	BigDecimal ftje = new BigDecimal(0.0);
				    	CostApportDetail costApportDetailNew1 = new CostApportDetail();
				    	CostApportDetail costApportDetailTemp1 = new CostApportDetail();
				    	costApportDetailTemp1.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp1.setDeptType("3");
				    	costApportDetailTemp1.setCostType("2");
				    	costApportDetailTemp1.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailNew1 = costApportDetailMapper.selectCostApportFtje(costApportDetailTemp1);
				    	if(StringUtils.isNotNull(costApportDetailNew1)){
					    	ftje = ftje.add(new BigDecimal(costApportDetailNew1.getAmount()));
				    	}
				    	
				    	/*CostApportDetail costApportDetailNew2 = new CostApportDetail();
				    	CostApportDetail costApportDetailTemp2 = new CostApportDetail();
				    	costApportDetailTemp2.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp2.setApportType("3");
				    	costApportDetailTemp2.setDeptType("3");
				    	costApportDetailTemp2.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailNew2 = costApportDetailMapper.selectCostApportFtje(costApportDetailTemp2);
				    	if(StringUtils.isNotNull(costApportDetailNew2)){
					    	ftje = ftje.add(new BigDecimal(costApportDetailNew2.getAmount()));
				    	}*/
				    	CostApportDetail costApportDetailTemp112 = new CostApportDetail();
				    	costApportDetailTemp112.setApportCode(costApport.getApportCode());
				    	costApportDetailTemp112.setProjectCode(costProjectNum2.getProjectCode());
				    	costApportDetailTemp112.setCostType("2");
				    	costApportDetailTemp112.setDeptType("4");
				    	CostApportDetail costApportDetailNew112 = costApportDetailMapper.selectCostApportZjcb(costApportDetailTemp112);
				    	if(StringUtils.isNotNull(costApportDetailNew112)){
				    		fthje = fthje.add(new BigDecimal(costApportDetailNew112.getAmount()));
				    	}
				    	//科室比例
				    	BigDecimal ksbl = new BigDecimal(0.0);
				    	//剩余分摊避开
				    	BigDecimal ftbl = new BigDecimal(0.0);
			    		for (CostApportionmentProjectDept costApportionmentProjectDept2 : costApportionmentProjectDeptList) {
			    			if(StringUtils.isNotNull(costApportionmentProjectDept2.getThirdRatio())){
			    				if(!"0".equals(costApportionmentProjectDept2.getSecondRatio())){
				    				CostApportDetail costApportDetail = new CostApportDetail();
						    		costApportDetail.setApportCode(costApport.getApportCode());
						    		costApportDetail.setDeptCode(costApportionmentProjectDept2.getDeptId());
						    		costApportDetail.setDeptType(deptTypeById(costApportionmentProjectDept2.getDeptId()));
						    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
						    		costApportDetail.setCostType("2");
						    		costApportDetail.setApportType("4");
						    		costApportDetail.setAmount(ftje.multiply(new BigDecimal(costApportionmentProjectDept2.getThirdRatio())).doubleValue());
						    		costApportDetail.preInsert();
						    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
				    				ksbl = ksbl.add(new BigDecimal(costApportionmentProjectDept2.getThirdRatio()));
				    				fthje = fthje.add(new BigDecimal(costApportDetail.getAmount()));
				    				deptList.add(costApportionmentProjectDept2.getDeptId()+"");
			    				}
			    			}
						}
			    		ftbl = new BigDecimal(1).subtract(ksbl);
				    	ftje = ftje.multiply(ftbl);
				    	//获取分摊方案比例设置
				    	CostApportionmentProjectRatio costApportionmentProjectRatio = new CostApportionmentProjectRatio();
				    	costApportionmentProjectRatio.setProjectCode(costApportionmentProjectlist1.get(0).getProjectInCode());
				    	List<CostApportionmentProjectRatio>costApportionmentProjectRatioList = costApportionmentProjectRatioService.selectCostApportionmentProjectRatioList(costApportionmentProjectRatio);
				    	for (CostApportionmentProjectRatio costApportionmentProjectRatio2 : costApportionmentProjectRatioList) {
							if(StringUtils.isNotNull(costApportionmentProjectRatio2.getThirdRatio())){
								//分摊比例金额
						    	BigDecimal ftblje = new BigDecimal(0.0);
						    	//分摊总数
						    	BigDecimal ftzs = new BigDecimal(0.0);
						    	ftblje = ftje.multiply(new BigDecimal(costApportionmentProjectRatio2.getThirdRatio()));
								CostApportionmentDictionaryDetail costApportionmentDictionaryDetail = new CostApportionmentDictionaryDetail();
								costApportionmentDictionaryDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
								costApportionmentDictionaryDetail.setLevelType("3");
								costApportionmentDictionaryDetail.setNy(sdf.format(costApport.getNowDate()));
								List<CostApportionmentDictionaryDetail>costApportionmentDictionaryDetailList = costApportionmentDictionaryDetailService.selectCostApportionmentDictionaryDetailList(costApportionmentDictionaryDetail);
								for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail1 : costApportionmentDictionaryDetailList) {
									if(deptList.contains(costApportionmentDictionaryDetail1.getDeptCode())){
										
									}else{
										ftzs = ftzs.add(new BigDecimal(costApportionmentDictionaryDetail1.getNum()));
									}
								}
								for (CostApportionmentDictionaryDetail costApportionmentDictionaryDetail2 : costApportionmentDictionaryDetailList) {
									if(deptList.contains(costApportionmentDictionaryDetail2.getDeptCode())){
									}else{
										//分摊比例
								    	BigDecimal xmftbl = new BigDecimal(0.0);
								    	xmftbl = new BigDecimal(costApportionmentDictionaryDetail2.getNum()).divide(ftzs,4,BigDecimal.ROUND_HALF_UP);
										CostApportDetail costApportDetail = new CostApportDetail();
							    		costApportDetail.setApportCode(costApport.getApportCode());
							    		costApportDetail.setDeptCode(Long.valueOf(costApportionmentDictionaryDetail2.getDeptCode()));
							    		costApportDetail.setDeptType(deptTypeById(Long.valueOf(costApportionmentDictionaryDetail2.getDeptCode())));
							    		costApportDetail.setProjectCode(costProjectNum2.getProjectCode());
							    		costApportDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
							    		costApportDetail.setCostType("2");
							    		costApportDetail.setApportType("4");
							    		costApportDetail.setAmount(ftblje.multiply(xmftbl).doubleValue());
							    		costApportDetail.preInsert();
							    		costApportDetailMapper.insertCostApportDetail(costApportDetail);
							    		fthje = fthje.add(new BigDecimal(costApportDetail.getAmount()));
									}
								}
							}
						}
					
					}
				}
			}
    	}
    	costApport.setBack(fthje.doubleValue());
    }
    
    /**
     * 修改分摊计算
     * 
     * @param costApport 分摊计算
     * @return 结果
     */
    @Override
    public int updateCostApport(CostApport costApport) {
    	costApport.preUpdate();
        return costApportMapper.updateCostApport(costApport);
    }

    /**
     * 批量删除分摊计算
     * 
     * @param ids 需要删除的分摊计算ID
     * @return 结果
     */
    @Override
    public int deleteCostApportByIds(Long[] ids) {
        return costApportMapper.deleteCostApportByIds(ids);
    }

    /**
     * 删除分摊计算信息
     * 
     * @param id 分摊计算ID
     * @return 结果
     */
    @Override
    public int deleteCostApportById(Long id) {
        return costApportMapper.deleteCostApportById(id);
    }
    
    /**
     * 验证
     * @param costApport
     * @return
     */
    @Override
    public String verification(CostApport costApport){
    	SimpleDateFormat sdf =  new  SimpleDateFormat("yyyy-MM" );
    	//分摊成本数据
    	CostProjectNum costProjectNum = new CostProjectNum();
    	costProjectNum.setCompCode(costApport.getCompCode());
    	costProjectNum.setNowDate(costApport.getNowDate());
    	List<CostProjectNum>List2 = costProjectNumService.selectCostProjectNumListGroup(costProjectNum);
    	if(List2.size()>0){
    		for (CostProjectNum costProjectNum2 : List2) {
    			if(StringUtils.isNull(costProjectNum2.getProjectCode())){
    				return "当前机构录入"+sdf.format(costApport.getNowDate())+"的成本项目编码为空！！";
    			}
    			CostProject costProject = new CostProject();
				costProject.setCompCode(costApport.getCompCode());
    			costProject.setProjectCode(costProjectNum2.getProjectCode());
    			List<CostProject>list3 = costProjectService.selectCostProjectList(costProject);
				if(list3.size()>0){
					if(list3.get(0).getIsCostCalculate()=="0"||"0".equals(list3.get(0).getIsCostCalculate())){
						//分摊方案
				    	CostApportionmentProject costApportionmentProject1 = new CostApportionmentProject();
				    	costApportionmentProject1.setProjectCode(list3.get(0).getCostScheme());
				    	costApportionmentProject1.setNowDate(costApport.getNowDate());
						costApportionmentProject1.setProjectType("2");
				    	List<CostApportionmentProject>costApportionmentProjectlist1 = costApportionmentProjectService.selectCostApportionmentProjectList(costApportionmentProject1);
				    	if(costApportionmentProjectlist1.size()==0){
				    		return "当前机构未生成成本项目："+list3.get(0).getProjectName()+"计算计入成本分摊方案对应"+sdf.format(costApport.getNowDate())+"的方案数据！";
				    	}else{
				    		for (CostApportionmentProject costApportionmentProject : costApportionmentProjectlist1) {
				    			CostApportionmentProjectRatio costApportionmentProjectRatio = new CostApportionmentProjectRatio();
						    	costApportionmentProjectRatio.setProjectCode(costApportionmentProject.getProjectInCode());
						    	List<CostApportionmentProjectRatio>costApportionmentProjectRatioList = costApportionmentProjectRatioService.selectCostApportionmentProjectRatioList(costApportionmentProjectRatio);
						    	if(costApportionmentProjectRatioList.size()==0){
						    		return "当前机构未生成成本方案："+costApportionmentProject.getProjectName()+"计算计入成本分摊方案对应"+sdf.format(costApport.getNowDate())+"的比例数据！";
						    	}
						    	for (CostApportionmentProjectRatio costApportionmentProjectRatio2 : costApportionmentProjectRatioList) {
									if(StringUtils.isNotNull(costApportionmentProjectRatio2.getRatio())){
										CostApportionmentDictionaryDetail costApportionmentDictionaryDetail = new CostApportionmentDictionaryDetail();
										costApportionmentDictionaryDetail.setRatioCode(costApportionmentProjectRatio2.getRatioCode());
										costApportionmentDictionaryDetail.setLevelType("4");
										costApportionmentDictionaryDetail.setNy(sdf.format(costApport.getNowDate()));
										List<CostApportionmentDictionaryDetail>costApportionmentDictionaryDetailList = costApportionmentDictionaryDetailService.selectCostApportionmentDictionaryDetailList(costApportionmentDictionaryDetail);
										if(costApportionmentDictionaryDetailList.size()==0){
											return "当前机构未生成分摊比例字典："+costApportionmentProjectRatio2.getRatioCode()+"计算计入成本分摊方案对应"+sdf.format(costApport.getNowDate())+"的比例明细数据！";
										}
									}
								}
						    }
				    	}



					}
				}else{
					return "当前机构录入"+sdf.format(costApport.getNowDate())+"的成本项目编码"+costProjectNum2.getProjectCode()+"未找到匹配数据！！";
				}
			}
    	}else{
    		return "当前机构未录入"+sdf.format(costApport.getNowDate())+"的成本数据！";
    	}
    	return "";
    }


	/**
     * 验证
     * @param costApport
     * @return
     */
//    @Override
//    public String verification1(CostApport costApport){
//    	//校验科室快照
//    	SimpleDateFormat sdf =  new  SimpleDateFormat("yyyy-MM" );
//    	CostDeptSnapshot costDeptSnapshot = new CostDeptSnapshot();
//    	costDeptSnapshot.setCompCode(costApport.getCompCode());
//    	costDeptSnapshot.setNowDate(costApport.getNowDate());
//    	List<CostDeptSnapshot>list1 = costDeptSnapshotService.selectCostDeptSnapshotList(costDeptSnapshot);
//    	if(list1.size()==0){
//    		return "当前机构未生成"+sdf.format(costApport.getNowDate())+"的科室快照数据！";
//    	}
//    	return "";
//    }
    
    /**
     * 验证
     * @param costApport
     * @return
     */
    @Override
    public String verification2(CostApport costApport){
    	SimpleDateFormat sdf =  new  SimpleDateFormat("yyyy-MM" );
    	CostApportionmentProject costApportionmentProject = new CostApportionmentProject();
    	costApportionmentProject.setCompCode(costApport.getCompCode());
    	costApportionmentProject.setNowDate(costApport.getNowDate());
    	List<CostApportionmentProject>list1 = costApportionmentProjectService.selectCostApportionmentProjectList(costApportionmentProject);
    	if(list1.size()==0){
    		return "当前机构未生成"+sdf.format(costApport.getNowDate())+"的分摊方案据！";
    	}
    	return "";
    }
    
    /**
     * 验证
     * @param costApport
     * @return
     */
    @Override
    public String verification3(CostApport costApport){
    	SimpleDateFormat sdf =  new  SimpleDateFormat("yyyy-MM" );
    	CostApportionmentDictionaryDetail costApportionmentDictionaryDetail = new CostApportionmentDictionaryDetail();
		costApportionmentDictionaryDetail.setNy(sdf.format(costApport.getNowDate()));
		List<CostApportionmentDictionaryDetail>list1 = costApportionmentDictionaryDetailService.selectCostApportionmentDictionaryDetailList(costApportionmentDictionaryDetail);
		if(list1.size()==0){
    		return "当前机构未生成"+sdf.format(costApport.getNowDate())+"的分摊比例据！";
    	}
		return "";
    }
    
    public String deptTypeById(Long id){
    	 CostApportDetail costApportDetail = costApportDetailMapper.selectDeptRoomById(id);
		if(StringUtils.isNotNull(costApportDetail)){
			return costApportDetail.getDeptType();
		}
		return "";
    }
    
    public String deptNameById(Long id){
    	CostApportDetail costApportDetail = costApportDetailMapper.selectDeptNameById(id);
    	if(StringUtils.isNull(costApportDetail)){
    		return "";
    	}
		return costApportDetail.getDeptName();
    }
    
    //四舍五入
    public static Double format(double value){
        BigDecimal bd = new BigDecimal(value);//创建一个bd对象，将要转换的值value传入构造函数
        bd = bd.setScale(2, RoundingMode.HALF_UP);//调用setScale方法进行数据格式化，保留两位小数，采用四舍五入规则
        return bd.doubleValue(); //返回bd对象的值（转化为string形式）
    }
}
