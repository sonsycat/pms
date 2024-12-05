package com.zjry.pms.evaluate.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.pms.bonus.domain.PmsBonusCalculation;
import com.zjry.pms.bonus.service.IPmsBonusCalculationService;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.UserUtils;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
import com.zjry.pms.evaluate.domain.PmsEvaluatePersonNotBonus;
import com.zjry.pms.evaluate.service.IPmsEvaluateDataService;
import com.zjry.system.domain.SysDeptRoom;
import com.zjry.system.domain.SysDoctor;
import com.zjry.system.service.ISysDeptRoomService;
import com.zjry.system.service.ISysDoctorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zwf
 * @date 2022年10月20日
 */
@RestController
@RequestMapping("/evaluate/data")
@Api(tags = {"【指标数据值】"})
public class PmsEvaluateDataController extends BaseController {
    @Autowired
    private IPmsEvaluateDataService iPmsEvaluateDataService;
    @Autowired
    private ISysDoctorService iSysDoctorService;
    @Autowired
    private ISysDeptRoomService iSysDeptRoomService;
    @Autowired
    private ISysDeptRoomService sysDeptRoomService;
    @Autowired
    private IPmsBonusCalculationService pmsBonusCalculationService;

    @PreAuthorize("@ss.hasPermi('evaluate:data:list')")
    @GetMapping("/listAccountDeptBonus")
    @ApiOperation("查询指标数据值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult listAccountDeptBonus(PmsEvaluateData pmsEvaluateData){
        pmsEvaluateData.setCompCode(UserUtils.getSysUser().getDeptId());
        return AjaxResult.success(iPmsEvaluateDataService.selectAccountDeptEvaluateDataList(pmsEvaluateData));
    }

    @PreAuthorize("@ss.hasPermi('evaluate:data:list')")
    @GetMapping("/getAccountDeptIndex")
    @ApiOperation("查询核算科室对应指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult getAccountDeptIndex(PmsEvaluateData pmsEvaluateData){
        pmsEvaluateData.setCompCode(UserUtils.getSysUser().getDeptId());
        return AjaxResult.success(iPmsEvaluateDataService.selectAccountDeptIndex(pmsEvaluateData));
    }

    @PreAuthorize("@ss.hasPermi('evaluate:data:list')")
    @GetMapping("/getAccountPersonIndex")
    @ApiOperation("获取科室对应人员奖金指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult getAccountPersonIndex(PmsEvaluateData pmsEvaluateData){
        return AjaxResult.success(iPmsEvaluateDataService.selectAccountPersonIndex(pmsEvaluateData));
    }

    @PreAuthorize("@ss.hasPermi('evaluate:data:list')")
    @GetMapping("/listAccountPersonBonus")
    @ApiOperation("获取科室对应人员奖金指标奖金值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult listAccountPersonBonus(PmsEvaluateData pmsEvaluateData){
        return AjaxResult.success(iPmsEvaluateDataService.selectAccountPersonEvaluateDataList(pmsEvaluateData));
    }

    /**
     * 查询医生列表
     */
    @GetMapping("/doctorList")
    public TableDataInfo doctorList() {
        startPage();
        SysDoctor sysDoctor = new SysDoctor();
        List<SysDoctor> data = iSysDoctorService.selectSysDoctorList(sysDoctor);
        return getDataTable(data);
    }

    /**
     * 查询医生列表
     */
    @GetMapping("/doctorListNotBonus")
    public TableDataInfo doctorListNotBonus(PmsEvaluateData pmsEvaluateData) {
        startPage();
        List<PmsEvaluatePersonNotBonus> data = iPmsEvaluateDataService.selectDoctorListNotBonus(pmsEvaluateData);
        return getDataTable(data);
    }

    /**
     * 查询科室列表
     */
    @GetMapping("/deptList")
    public TableDataInfo deptList() {
        startPage();
        SysDeptRoom sysDeptRoom = new SysDeptRoom();
        List<SysDeptRoom> data = iSysDeptRoomService.selectSysDeptRoomList(sysDeptRoom);
        return getDataTable(data);
    }

    /**
     * 科室奖金详情
     * @param pmsEvaluateData
     * @return
     */
    @PreAuthorize("@ss.hasPermi('evaluate:deptBonus:detail')")
    @GetMapping("/deptBonusDetail")
    @ApiOperation("查询核算科室对应指标")
    public AjaxResult deptBonusDetail(PmsEvaluateData pmsEvaluateData){
        JSONObject result = new JSONObject();

        if (UserUtils.getSysUser().getDeptRoomId() == null){
            return AjaxResult.error("系统当前登录用户未配置科室，无查看权限！");
        }
        String auditState = null;
        String archiveState = Constants.COMMON_NO;
        QueryWrapper<PmsBonusCalculation> qw = new QueryWrapper<>();
        qw.lambda().select(PmsBonusCalculation::getAuditState,PmsBonusCalculation::getArchiveState,PmsBonusCalculation::getName)
                .eq(PmsBonusCalculation::getPeriod, pmsEvaluateData.getActDate());
        List<PmsBonusCalculation> bonusCalculationList = pmsBonusCalculationService.list(qw);
        if(CollectionUtils.isNotEmpty(bonusCalculationList)){
            for(PmsBonusCalculation pc : bonusCalculationList){
                if(Constants.COMMON_YES.equals(pc.getArchiveState())){
                    archiveState = Constants.COMMON_YES;
                    auditState = Constants.CHECK_PASS;
                    break;
                }
                if(Constants.CHECK_PASS.equals(pc.getAuditState())){
                    auditState = Constants.CHECK_PASS;
                    break;
                }else if(Constants.CHECK_SUBMIT.equals(pc.getAuditState())){
                    auditState = Constants.CHECK_SUBMIT;
                }else{
                    auditState = pc.getAuditState();
                }
            }
            if(!Constants.CHECK_PASS.equals(auditState)){
                return AjaxResult.error("数据审核中或未通过审核");
            }
        }else{
            return AjaxResult.error("数据未生成");
        }

        pmsEvaluateData.setUnitCode(String.valueOf(UserUtils.getSysUser().getDeptRoomId()));
        pmsEvaluateData.setDataType(Constants.EVA_CAL_BONUS);
        pmsEvaluateData.setCompCode(UserUtils.getSysUser().getDeptId());

        QueryWrapper<SysDeptRoom> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(SysDeptRoom::getDeptRoomId,SysDeptRoom::getDeptRoomCode,SysDeptRoom::getDeptRoomName)
                .eq(SysDeptRoom::getDeptRoomId, UserUtils.getSysUser().getDeptRoomId());
        SysDeptRoom deptRoom = sysDeptRoomService.getOne(wrapper);
        JSONObject roomJson = new JSONObject();
        roomJson.put("deptRoomId", deptRoom.getDeptRoomId());
        roomJson.put("deptRoomCode", deptRoom.getDeptRoomCode());
        roomJson.put("deptRoomName", deptRoom.getDeptRoomName());
        roomJson.put("archiveState", archiveState);
        roomJson.put("auditState", auditState);

        List<JSONObject> deptRoomList = Lists.newArrayList(roomJson);
        List<PmsEvaluateData> deptBonusIndexList = iPmsEvaluateDataService.selectDeptBonusList(pmsEvaluateData);
        result.put("deptRoomList", deptRoomList);
        result.put("deptBonusIndexList", deptBonusIndexList);
        return AjaxResult.success(result);
    }
}
