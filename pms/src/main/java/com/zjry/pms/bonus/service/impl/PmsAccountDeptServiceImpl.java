package com.zjry.pms.bonus.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.UserUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.bonus.mapper.PmsAccountDeptMapper;
import com.zjry.pms.bonus.domain.PmsAccountDept;
import com.zjry.pms.bonus.service.IPmsAccountDeptService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 核算科室设置Service业务层处理
 * 
 * @author zjry
 * @date 2022-09-29
 */
@Service
public class PmsAccountDeptServiceImpl extends ServiceImpl<PmsAccountDeptMapper, PmsAccountDept> implements IPmsAccountDeptService {
    @Autowired
    private PmsAccountDeptMapper pmsAccountDeptMapper;

    /**
     * 查询核算科室设置
     * 
     * @param id 核算科室设置ID
     * @return 核算科室设置
     */
    @Override
    public PmsAccountDept selectPmsAccountDeptById(Long id) {
        return pmsAccountDeptMapper.selectPmsAccountDeptById(id);
    }

    /**
     * 查询核算科室设置列表
     * 
     * @param pmsAccountDept 核算科室设置
     * @return 核算科室设置
     */
    @Override
    public List<PmsAccountDept> selectPmsAccountDeptList(PmsAccountDept pmsAccountDept) {
        return pmsAccountDeptMapper.selectPmsAccountDeptList(pmsAccountDept);
    }

    /**
     * 新增核算科室设置
     * 
     * @param pmsAccountDept 核算科室设置
     * @return 结果
     */
    @Override
    public int insertPmsAccountDept(PmsAccountDept pmsAccountDept) {
    	pmsAccountDept.preInsert();
        return pmsAccountDeptMapper.insertPmsAccountDept(pmsAccountDept);
    }

    /**
     * 修改核算科室设置
     * 
     * @param pmsAccountDept 核算科室设置
     * @return 结果
     */
    @Override
    public int updatePmsAccountDept(PmsAccountDept pmsAccountDept) {
    	pmsAccountDept.preUpdate();
        return pmsAccountDeptMapper.updatePmsAccountDept(pmsAccountDept);
    }

    /**
     * 批量删除核算科室设置
     * 
     * @param ids 需要删除的核算科室设置ID
     * @return 结果
     */
    @Override
    public int deletePmsAccountDeptByIds(Long[] ids) {
        return pmsAccountDeptMapper.deletePmsAccountDeptByIds(ids);
    }

    /**
     * 删除核算科室设置信息
     * 
     * @param id 核算科室设置ID
     * @return 结果
     */
    @Override
    public int deletePmsAccountDeptById(Long id) {
        return pmsAccountDeptMapper.deletePmsAccountDeptById(id);
    }

    /**
     * 单表查询列表数据
     * @param pmsAccountDept
     * @return
     */
    @Override
    public List<PmsAccountDept> queryList(PmsAccountDept pmsAccountDept){
        return pmsAccountDeptMapper.queryList(pmsAccountDept);
    }

    /**
     * 复制数据
     * @param pmsAccountDept
     */
    @Override
    public void copyData(PmsAccountDept pmsAccountDept){
        pmsAccountDeptMapper.copyData(pmsAccountDept);
    }

    /**
     * 修改核算科室设置
     * 父级选项只能是核算科室和未设置科室
     * 1.父级code（parentCode）和子集code（deptRoomCode）相同：设置该科室为是核算科室（0）
     * 2.父级为核算科室（0），子集为非核算科室（0）：正常添加修改数据
     * 3.父级未设置是否核算科室（空）：·正常建立子父级关系
     *                           ·若子级已是核算科室，需改为非核算科室并删除原父子级关系
     *                           ·若子级已有父级，需接触原父子级关系
     */
    @Override
    @Transactional
    public int saveData(PmsAccountDept pmsAccountDept){
        String nowDateStr = DateUtils.parseDateToStr(Constants.DATE_MONTH_FORMATTER, pmsAccountDept.getNowDate());
        boolean isAccountFlag = false;
        /* 取消核算科室 */
        if(pmsAccountDept.getDeptRoomCode().equals(pmsAccountDept.getParentCode()) && Constants.COMMON_NO.equals(pmsAccountDept.getIsAccount())){
            pmsAccountDeptMapper.deleteSelfAndSub(nowDateStr, pmsAccountDept.getDeptRoomCode());
            return 1;
        }

        pmsAccountDept.setIsAccount(Constants.COMMON_NO);
        if(pmsAccountDept.getDeptRoomCode().equals(pmsAccountDept.getParentCode())){
            isAccountFlag  = true;
            pmsAccountDept.setParentCode(IPmsAccountDeptService.ROOT_PARENT_CODE);
            pmsAccountDept.setIsAccount(Constants.COMMON_YES);
        }

        QueryWrapper<PmsAccountDept> qw = new QueryWrapper<>();
        qw.lambda().select(PmsAccountDept::getId,PmsAccountDept::getParentCode,PmsAccountDept::getDeptRoomCode,PmsAccountDept::getDeptRoomName,PmsAccountDept::getIsAccount)
                        .eq(PmsAccountDept::getDeptRoomCode,pmsAccountDept.getDeptRoomCode())
                        .eq(PmsAccountDept::getNowDate,nowDateStr);
        PmsAccountDept accountDept = this.getOne(qw);
        List<PmsAccountDept> accountDeptList;
        if(accountDept == null){
            pmsAccountDept.preInsert();
            pmsAccountDept.setCompCode(UserUtils.getSysUser().getDeptId());
            pmsAccountDeptMapper.insertPmsAccountDept(pmsAccountDept);
        }else{
            /* 数据未发生改变 */
            if(accountDept.getParentCode().equals(pmsAccountDept.getParentCode()) || accountDept.getIsAccount().equals(pmsAccountDept.getIsAccount())){
                return 1;
            }
            /* 更新记录 */
            pmsAccountDept.setId(accountDept.getId());
            pmsAccountDept.preUpdate();
            pmsAccountDeptMapper.updatePmsAccountDept(pmsAccountDept);

            if(Constants.COMMON_YES.equals(accountDept.getIsAccount()) && Constants.COMMON_NO.equals(pmsAccountDept.getIsAccount())){
                qw.clear();
                qw.lambda().select(PmsAccountDept::getId,PmsAccountDept::getParentCode,PmsAccountDept::getDeptRoomCode,PmsAccountDept::getDeptRoomName,PmsAccountDept::getIsAccount)
                        .eq(PmsAccountDept::getParentCode,pmsAccountDept.getDeptRoomCode())
                        .eq(PmsAccountDept::getNowDate,nowDateStr);
                accountDeptList = pmsAccountDeptMapper.selectList(qw);
                Long[] ids = new Long[accountDeptList.size()];
                if(CollectionUtils.isNotEmpty(accountDeptList)){
                    for(int i=0;i<accountDeptList.size();i++){
                        ids[i] = accountDeptList.get(i).getId();
                    }
                    pmsAccountDeptMapper.deleteByIds(nowDateStr, ids);
                }
            }
        }
        if(isAccountFlag){
            return 1;
        }

        qw.clear();
        qw.lambda().select(PmsAccountDept::getId,PmsAccountDept::getParentCode,PmsAccountDept::getDeptRoomCode,PmsAccountDept::getDeptRoomName,PmsAccountDept::getIsAccount)
                .eq(PmsAccountDept::getDeptRoomCode,pmsAccountDept.getParentCode())
                .eq(PmsAccountDept::getNowDate,nowDateStr);
        PmsAccountDept accountDeptParent = this.getOne(qw);
        if(accountDeptParent == null){
            accountDeptParent = new PmsAccountDept();
            accountDeptParent.setCompCode(UserUtils.getSysUser().getDeptId());
            accountDeptParent.setParentCode(ROOT_PARENT_CODE);
            accountDeptParent.setDeptRoomCode(pmsAccountDept.getParentCode());
            accountDeptParent.setIsAccount(Constants.COMMON_YES);
            accountDeptParent.setNowDate(pmsAccountDept.getNowDate());
            accountDeptParent.preInsert();
            pmsAccountDeptMapper.insertPmsAccountDept(accountDeptParent);
        }else{
            if(Constants.COMMON_NO.equals(accountDeptParent.getIsAccount())){
                accountDeptParent.setParentCode(ROOT_PARENT_CODE);
                accountDeptParent.setIsAccount(Constants.COMMON_YES);
                accountDeptParent.preUpdate();
                pmsAccountDeptMapper.updatePmsAccountDept(accountDeptParent);
            }
        }
        return 1;
    }
}
