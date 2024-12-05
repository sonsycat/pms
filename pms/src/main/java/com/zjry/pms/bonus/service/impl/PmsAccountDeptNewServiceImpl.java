package com.zjry.pms.bonus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.pms.bonus.domain.PmsAccountDeptNew;
import com.zjry.pms.bonus.domain.PmsCopyAccountDeptNew;
import com.zjry.pms.bonus.domain.PmsSaveAccountDeptNew;
import com.zjry.pms.bonus.mapper.PmsAccountDeptNewMapper;
import com.zjry.pms.bonus.service.IPmsAccountDeptNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 核算科室设置Service业务层处理
 * 
 * @author zjry
 * @date 2022-09-29
 */
@Service
public class PmsAccountDeptNewServiceImpl extends ServiceImpl<PmsAccountDeptNewMapper, PmsAccountDeptNew>
        implements IPmsAccountDeptNewService {
    @Autowired
    private PmsAccountDeptNewMapper pmsAccountDeptNewMapper;



    /**
     * 查询核算科室设置列表
     * 
     * @param pmsAccountDept 核算科室设置
     * @return 核算科室设置
     */
    @Override
    public List<PmsAccountDeptNew> selectPmsAccountDeptList(PmsAccountDeptNew pmsAccountDept) {
        return pmsAccountDeptNewMapper.selectPmsAccountDeptList(pmsAccountDept);
    }

    @Override
    @Transactional
    public void saveAccountDeptNew(List<PmsSaveAccountDeptNew> pmsSaveAccountDeptNewList) {
        if(null != pmsSaveAccountDeptNewList && pmsSaveAccountDeptNewList.size()>0){
            //过滤没有选择核算类别的数据
            List<PmsSaveAccountDeptNew> list = pmsSaveAccountDeptNewList.stream().filter(o ->o.getAccountType()!=null)
                    .collect(Collectors.toList());
            if(null != list && list.size()>0){
                Map<String, List<PmsSaveAccountDeptNew>> map = list.stream()
                        .collect(Collectors.groupingBy(item -> {
                            if (item.getId() == null || item.getId().equals("")) {
                                return "insertData";
                            }
                            return "updateData";
                        }));
                if(null!=map.get("insertData") && map.get("insertData").size()>0){
                    //批量新增数据
                    List<PmsSaveAccountDeptNew> dataList= map.get("insertData");
                    pmsAccountDeptNewMapper.insertData(dataList);

                }
                if(null!=map.get("updateData") && map.get("updateData").size()>0){
                    //批量新增数据
                    List<PmsSaveAccountDeptNew> dataList= map.get("updateData");
                    pmsAccountDeptNewMapper.updateData(dataList);

                }
            }

        }
    }

    @Override
    public List<PmsAccountDeptNew> queryList(PmsCopyAccountDeptNew param) {
        return pmsAccountDeptNewMapper.queryList(param);
    }

    @Override
    public void copyData(PmsCopyAccountDeptNew pmsCopyAccountDeptNew,List<PmsAccountDeptNew> copyList) {
        List<PmsSaveAccountDeptNew> dataList = new ArrayList<>();
        for (PmsAccountDeptNew pmsAccountDeptNew : copyList) {
            PmsSaveAccountDeptNew pmsSaveAccountDeptNew = new PmsSaveAccountDeptNew();
            pmsSaveAccountDeptNew.setDeptRoomCode(pmsAccountDeptNew.getDeptRoomCode());
            pmsSaveAccountDeptNew.setDeptRoomName(pmsAccountDeptNew.getDeptRoomName());
            pmsSaveAccountDeptNew.setAccountType(pmsAccountDeptNew.getAccountType());
            pmsSaveAccountDeptNew.setNowDate(pmsCopyAccountDeptNew.getNowDate());
            pmsSaveAccountDeptNew.setCompCode(pmsAccountDeptNew.getCompCode());
            dataList.add(pmsSaveAccountDeptNew);
        }

        pmsAccountDeptNewMapper.insertData(dataList);
    }

}
