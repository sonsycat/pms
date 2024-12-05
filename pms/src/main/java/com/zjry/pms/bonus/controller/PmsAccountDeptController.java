package com.zjry.pms.bonus.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.UserUtils;
import io.swagger.annotations.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.enums.BusinessType;
import com.zjry.pms.bonus.domain.PmsAccountDept;
import com.zjry.pms.bonus.service.IPmsAccountDeptService;
import com.zjry.common.utils.poi.ExcelUtil;

/**
 * 核算科室设置Controller
 *
 * @author zjry
 * @date 2022-09-29
 */
@RestController
@RequestMapping("/bonus/accountDept")
@Api(tags={"【核算科室设置】"})
public class PmsAccountDeptController extends BaseController {
    @Autowired
    private IPmsAccountDeptService pmsAccountDeptService;

    /**
     * 查询核算科室设置列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:accountDept:list')")
    @GetMapping("/list")
    @ApiOperation("查询核算科室设置列表")
    public List list(PmsAccountDept pmsAccountDept, Integer dataType) {
        dataType = dataType==null?1:dataType;
        List<PmsAccountDept> resultList = new ArrayList<>();
        pmsAccountDept.setCompCode(UserUtils.getSysUser().getDeptId());
        List<PmsAccountDept> selectList = pmsAccountDeptService.selectPmsAccountDeptList(pmsAccountDept);
        if(CollectionUtils.isNotEmpty(selectList)){
            if(dataType.equals(1)){
                Map<String, PmsAccountDept> deptMap = selectList.stream().collect(Collectors.toMap(PmsAccountDept::getDeptRoomCode, Function.identity()));
                List<PmsAccountDept> needAccountList = new ArrayList<>();//核算科室列表
                selectList.forEach(data->{
                    if(StringUtils.isEmpty(data.getIsAccount())){
                        resultList.add(data);
                    }
                    if(deptMap.containsKey(data.getParentCode())){
                        deptMap.get(data.getParentCode()).getAccountDeptList().add(data);
                    }
                    if(Constants.COMMON_YES.equals(data.getIsAccount())){
                        needAccountList.add(data);
                    }
                });

                if(CollectionUtils.isNotEmpty(selectList)){
                    needAccountList.forEach(data->{
                        data.setChildNum(data.getAccountDeptList().size());
                        resultList.add(data);
                        PmsAccountDept dept = new PmsAccountDept();
                        BeanUtils.copyProperties(data, dept);
                        dept.setParentCode(data.getDeptRoomCode());
                        dept.setParentName(data.getDeptRoomName());
                        resultList.add(dept);
                        if(CollectionUtils.isNotEmpty(data.getAccountDeptList())){
                            data.getAccountDeptList().forEach(d->{
                                d.setParentCode(data.getDeptRoomCode());
                                d.setParentName(data.getDeptRoomName());
                                resultList.add(d);
                            });
                        }
                    });
                }
            }else{
                selectList.forEach(data->{
                    if(!Constants.COMMON_NO.equals(data.getIsAccount())){
                        resultList.add(data);
                    }
                });
            }
        }
        return resultList;
    }

    /**
     * 导出核算科室设置列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:accountDept:export')")
    @Log(title = "核算科室设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出核算科室设置列表Excel")
    public AjaxResult export(PmsAccountDept pmsAccountDept) {
        List<PmsAccountDept> list = pmsAccountDeptService.selectPmsAccountDeptList(pmsAccountDept);
        ExcelUtil<PmsAccountDept> util = new ExcelUtil<PmsAccountDept>(PmsAccountDept.class);
        return util.exportExcel(list, "核算科室设置数据");
    }

    /**
     * 获取核算科室设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('bonus:accountDept:query')")
    @GetMapping(value = "/{deptRoomCode}")
    @ApiOperation("获取核算科室设置详细信息")
    public AjaxResult getInfo(@PathVariable("deptRoomCode") String deptRoomCode, String nowDate) {
        QueryWrapper<PmsAccountDept> qw = new QueryWrapper();
        qw.lambda().select(PmsAccountDept::getDeptRoomCode,PmsAccountDept::getDeptRoomName,PmsAccountDept::getParentCode,PmsAccountDept::getIsAccount)
                        .eq(PmsAccountDept::getDeptRoomCode,deptRoomCode).eq(PmsAccountDept::getNowDate,nowDate).last("LIMIT 1");
        PmsAccountDept accountDept = pmsAccountDeptService.getOne(qw);
        if(accountDept != null && IPmsAccountDeptService.ROOT_PARENT_CODE.equals(accountDept.getParentCode())){
            accountDept.setParentCode(deptRoomCode);
        }
        return AjaxResult.success(accountDept);
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
    @PreAuthorize("@ss.hasPermi('bonus:accountDept:edit')")
    @Log(title = "核算科室设置", businessType = BusinessType.UPDATE)
    @PostMapping
    @ApiOperation("修改核算科室设置")
    public AjaxResult edit(@Validated @RequestBody PmsAccountDept pmsAccountDept) {
        return toAjax(pmsAccountDeptService.saveData(pmsAccountDept));
    }

    /**
     * 删除核算科室设置
     */
    @PreAuthorize("@ss.hasPermi('bonus:accountDept:remove')")
    @Log(title = "核算科室设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除核算科室设置")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsAccountDeptService.deletePmsAccountDeptByIds(ids));
    }

    /**
     * 通过年月复制数据
     */
    @PostMapping("/copy")
    @PreAuthorize("@ss.hasPermi('bonus:accountDept:copy')")
    @ApiOperation("新增指标集与科室管理")
    public AjaxResult copy(@RequestBody PmsAccountDept pmsAccountDept) {
        if(pmsAccountDept.getNowDate() == null || pmsAccountDept.getCopyDate() == null){
            return error("参数为空");
        }
        try {
            PmsAccountDept param = new PmsAccountDept();
            param.setNowDate(pmsAccountDept.getCopyDate());
            List<PmsAccountDept> list = pmsAccountDeptService.queryList(param);
            if(CollectionUtils.isNotEmpty(list)){
                return error("当前年月已有数据");
            }

            list = pmsAccountDeptService.queryList(pmsAccountDept);
            if(CollectionUtils.isEmpty(list)){
                return error("需复制年月没有数据");
            }

            pmsAccountDept.preInsert();
            pmsAccountDeptService.copyData(pmsAccountDept);
        } catch (Exception e) {
            logger.error("系统错误", e);
            return error();
        }
        return success();
    }

    @PreAuthorize("@ss.hasPermi('bonus:accountDept:list')")
    @GetMapping("/getAccountDept")
    @ApiOperation("查询核算科室")
    public List<PmsAccountDept> getAccountDept(PmsAccountDept pmsAccountDept, Integer dataType) {
        if (pmsAccountDept.getNowDate() == null){
            pmsAccountDept.setNowDate(new Date());
        }
        return pmsAccountDeptService.selectPmsAccountDeptList(pmsAccountDept);
    }
}
