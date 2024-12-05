package com.zjry.pms.basic.controller;


import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.entity.SysUser;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.apport.domain.CostApport;
import com.zjry.pms.apport.service.ICostApportService;
import com.zjry.pms.apportionment.domain.CostApportionmentProject;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectService;
import com.zjry.pms.basic.domain.*;
import com.zjry.pms.basic.domain.basicData.BasicdataDeptRoom;
import com.zjry.pms.basic.domain.basicData.BasicdataUser;
import com.zjry.pms.basic.service.IAssetsBasicAccountPeriodService;
import com.zjry.pms.basic.service.IAssetsBasicAccountService;
import com.zjry.pms.basic.service.IPmsHisIncomeService;
import com.zjry.pms.basic.service.ISpdSysUserService;
import com.zjry.pms.basic.utils.BasicDataUserUtils;
import com.zjry.pms.project.domain.CostProject;
import com.zjry.pms.project.service.ICostProjectService;
import com.zjry.pms.revenue.domain.PmsRevenue;
import com.zjry.pms.revenue.service.IPmsRevenueService;
import com.zjry.pms.second.domain.PmsSecondDistribution;
import com.zjry.pms.second.service.IPmsSecondDistributionService;
import com.zjry.system.domain.SysDeptRoom;
import com.zjry.system.service.ISysDeptRoomService;
import com.zjry.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author mk
 * @title: AssetsBasicPublicController
 * @projectName zjry-asset
 * @date 2021/8/1109:11
 */
@RestController
@RequestMapping("/basic/public")
@Api(tags={"【前台字典公共方法接口】"})
public class AssetsBasicPublicController  extends BaseController {
    @Autowired
    private ISpdSysUserService spdSysUserService;
    @Autowired
    private ISysDeptRoomService sysDeptRoomService;
    @Autowired
    private IAssetsBasicAccountPeriodService assetsBasicAccountPeriodService;
    @Autowired
    private IAssetsBasicAccountService assetsBasicAccountService;
    @Autowired
    private ICostProjectService costProjectService;
    @Autowired
    private ICostApportionmentProjectService costApportionmentProjectService;
    @Autowired
    private ICostApportService costApportService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IPmsRevenueService pmsRevenueService;
    @Autowired
    private IPmsHisIncomeService pmsHisIncomeService;
    @Autowired
    private IPmsSecondDistributionService pmsSecondDistributionService;



    /**
     * 根据用户返回账套信息
     */
    @GetMapping("/accountInfo")
    @ApiOperation("根据用户返回账套信息")
    public SysUserInfo accountInfo() {
        return assetsBasicAccountService.getAccountInfo();

    }


    /**
     * 根据用户返回账套信息
     */
    @GetMapping("/queryTypeByDept")
    @ApiOperation("查询科室字典")
    public AjaxResult queryTypeByDept(Long comeCode ,Long deptCode) {
       String data = assetsBasicAccountService.queryTypeByDept( comeCode , deptCode);
        return AjaxResult.success(data);
    }

    /**
     * 根据用户返回账套信息
     */
    @GetMapping("/projectft")
    @ApiOperation("查询科室字典")
    public AjaxResult projectft(CostApportionmentProject costApportionmentProject) {
        List<CostApportionmentProject> list =  costApportionmentProjectService.selectForProject(costApportionmentProject);
        List<AssetsBasicPublicVo>  data = new ArrayList<AssetsBasicPublicVo>();
        if(list != null && list.size()>0){
            list.stream().forEach(item->{
                AssetsBasicPublicVo abpv = new AssetsBasicPublicVo();
                abpv.setDictValue(item.getProjectCode());
                abpv.setDictLabel(item.getProjectName());
                data.add(abpv);
            });
        }
        return AjaxResult.success(data);
    }


    /**
     * 根据用户返回账套信息
     */
    @GetMapping("/deptInfo")
    @ApiOperation("查询科室字典")
    public AjaxResult deptInfo() {
        BasicdataUser user = BasicDataUserUtils.getUserWithRelations();
        List<BasicdataDeptRoom> list = user.getDeptRoomList();
        List<AssetsBasicPublicVo>  data = new ArrayList<AssetsBasicPublicVo>();
        if(list != null && list.size()>0){
            list.stream().forEach(item->{
                AssetsBasicPublicVo abpv = new AssetsBasicPublicVo();
                abpv.setDictValue(item.getDeptRoomId().toString());
                abpv.setDictLabel(item.getDeptRoomName());
                data.add(abpv);
            });
        }
        return AjaxResult.success(data);
    }

    /**
     * 根据用户返回账套信息
     */
    @GetMapping("/deptInfoByType")
    @ApiOperation("根据科室类型查询科室")
    public AjaxResult deptInfoByType(String type,Long compCode) {
        List<CostProject> list = costProjectService.deptInfoByType(type,compCode);
        List<AssetsBasicPublicVo>  data = new ArrayList<AssetsBasicPublicVo>();
        if(list != null && list.size()>0){
            list.stream().forEach(item->{
                AssetsBasicPublicVo abpv = new AssetsBasicPublicVo();
                abpv.setDictValue(item.getDeptCode().toString());
                abpv.setDictLabel(item.getDeptName());
                data.add(abpv);
            });
        }
        return AjaxResult.success(data);
    }

    /**
     * 根据用户返回账套信息
     */
    @GetMapping("/deptInfoForPms")
    @ApiOperation("根据科室类型查询科室")
    public AjaxResult deptInfoForPms(Long compCode) {
        List<CostProject> list = costProjectService.deptInfoByType(null,compCode);
        List<AssetsBasicPublicVo>  data = new ArrayList<AssetsBasicPublicVo>();
        if(list != null && list.size()>0){
            list.stream().forEach(item->{
                AssetsBasicPublicVo abpv = new AssetsBasicPublicVo();
                abpv.setDictValue(item.getDeptCode().toString());
                abpv.setDictLabel(item.getDeptName());
                data.add(abpv);
            });
        }
        return AjaxResult.success(data);
    }


    /**
     * 根据单位查询科室
     */
    @GetMapping("/getDeptForPms")
    @ApiOperation("根据单位查询科室")
    public AjaxResult getDeptForPms(String compCode,String clinicType,String deptName) {
        List<CostProject> list = costProjectService.getDeptForPms(compCode,clinicType,deptName);
        List<AssetsBasicPublicVo>  data = new ArrayList<>();
        if(list != null && list.size()>0){
            list.stream().forEach(item->{
                AssetsBasicPublicVo abpv = new AssetsBasicPublicVo();
                abpv.setDictValue(item.getDeptCode().toString());
                abpv.setDictLabel(item.getDeptName());
                data.add(abpv);
            });
        }
        return AjaxResult.success(data);
    }



    /**
     * 根据单位查询科室
     */
    @GetMapping("/getDeptList")
    @ApiOperation("根据单位查询科室")
    public AjaxResult getDeptList(String compCode,String isCostCalculate) {
        if (StringUtils.isEmpty(isCostCalculate)) {
            isCostCalculate = null;
        }
        List<CostProject> list = costProjectService.getDeptList(compCode,isCostCalculate);
        List<AssetsBasicPublicVo>  data = new ArrayList<>();
        if(list != null && list.size()>0){
            list.stream().forEach(item->{
                AssetsBasicPublicVo abpv = new AssetsBasicPublicVo();
                abpv.setDictValue(item.getDeptCode().toString());
                if (StringUtils.isEmpty(item.getType())) {
                    abpv.setDictLabel(item.getDeptName());
                } else {
                    abpv.setDictLabel(item.getType() + " - " + item.getDeptName());
                }
                data.add(abpv);
            });
        }
        return AjaxResult.success(data);
    }

    /**
     * 查询收入核算方案字典
     */
    @GetMapping("/getRevenue")
    @ApiOperation("查询收入核算方案字典")
    public AjaxResult getRevenue(Long compCode) {
        PmsRevenue pmsRevenue = new PmsRevenue();
        pmsRevenue.setCompCode(compCode);
        List<PmsRevenue> list = pmsRevenueService.selectPmsRevenueList(pmsRevenue);
        List<AssetsBasicPublicVo>  data = new ArrayList<>();
        if(list != null && list.size()>0){
            list.stream().forEach(item->{
                AssetsBasicPublicVo abpv = new AssetsBasicPublicVo();
                abpv.setDictValue(item.getRevenueCode());
                abpv.setDictLabel(item.getRevenueName());
                data.add(abpv);
            });
        }
        return AjaxResult.success(data);
    }

    /**
     * 查询成本项目字典
     */
    @GetMapping("/getProject")
    @ApiOperation("查询成本项目字典")
    public AjaxResult getProject(Long compCode) {
        CostProject costProject = new CostProject();
        costProject.setCompCode(compCode);
        costProject.setIsLast("0");
        List<CostProject> list = costProjectService.selectCostProjectList(costProject);
        List<AssetsBasicPublicVo>  data = new ArrayList<>();
        if(list != null && list.size()>0){
            list.stream().forEach(item->{
                AssetsBasicPublicVo abpv = new AssetsBasicPublicVo();
                abpv.setDictValue(item.getProjectCode());
                abpv.setDictLabel(item.getProjectName());
                data.add(abpv);
            });
        }
        return AjaxResult.success(data);
    }

    /**
     * 查询his收入类别字典
     */
    @GetMapping("/getHisIncome")
    @ApiOperation("查询his收入类别字典")
    public AjaxResult getHisIncome(Long compCode) {
        PmsHisIncome pmsHisIncome = new PmsHisIncome();
        pmsHisIncome.setCompCode(compCode);
        List<PmsHisIncome> list = pmsHisIncomeService.selectPmsHisIncomeList(pmsHisIncome);
        List<AssetsBasicPublicVo>  data = new ArrayList<>();
        if(list != null && list.size()>0){
            list.stream().forEach(item->{
                AssetsBasicPublicVo abpv = new AssetsBasicPublicVo();
                abpv.setDictValue(item.getRevenueCode());
                abpv.setDictLabel(item.getRevenueName());
                data.add(abpv);
            });
        }
        return AjaxResult.success(data);
    }

    /**
     * 查询员工信息
     */
    @GetMapping("/empInfo")
    @ApiOperation("查询用户")
    public TableDataInfo empInfo() {
        startPage();
        List<EmpInfoVo> data = spdSysUserService.empInfo();
        return getDataTable(data);
    }

    /**
     * 查询员工信息
     */
    @GetMapping("/queryEmpInfo")
    @ApiOperation("查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo queryEmpInfo(String compCode,String deptCode,String postCode,String searchName) {
        startPage();
        List<EmpInfoVo> data = spdSysUserService.queryEmpInfo(compCode,deptCode,postCode,searchName);
        return getDataTable(data);
    }


    /**
     * 查询用户
     */
    @GetMapping("/userInfo")
    @ApiOperation("查询用户")
    public AjaxResult userInfo() {
        List<AssetsBasicPublicVo> data = spdSysUserService.queryAllUserInfo();
        return AjaxResult.success(data);
    }


    /**
     * 根据单位查询科室信息
     */
    @GetMapping("/deptInfoByCompCode")
    @ApiOperation("根据单位查询科室信息")
    public AjaxResult deptInfoByCompCode(Long compCode) {
        SysDeptRoom sysDeptRoom = new SysDeptRoom();
        sysDeptRoom.setDeptId(compCode);
        List<SysDeptRoom> deptRoomList = sysDeptRoomService.selectSysDeptRoomList(sysDeptRoom);
        List<AssetsBasicPublicVo> assetsBasicPublicVos = new ArrayList<AssetsBasicPublicVo>();
        if (deptRoomList.size()>0){
            deptRoomList.stream().forEach(item->{
                AssetsBasicPublicVo abpVo = new AssetsBasicPublicVo();
                abpVo.setDictLabel(item.getDeptRoomName());
                abpVo.setDictValue(item.getDeptRoomId().toString());
                assetsBasicPublicVos.add(abpVo);
            });
        }
        return AjaxResult.success(assetsBasicPublicVos);
    }


    /**
     * 根据账套查账期名称
     */
    @GetMapping("/getPeriodName")
    @ApiOperation("根据账套查账期名称")
    public AjaxResult getPeriodName(String accountCode) {
        AssetsBasicAccountPeriod assetsBasicAccountPeriod = new AssetsBasicAccountPeriod();
        assetsBasicAccountPeriod.setAccountCode(accountCode);
        List<AssetsBasicAccountPeriod> assetsBasicAccountPeriods = assetsBasicAccountPeriodService.selectAssetsBasicAccountPeriodList(assetsBasicAccountPeriod);
        return AjaxResult.success(assetsBasicAccountPeriods);
    }

    /**
     * 查询方案字典
     */
    @GetMapping("/getProject1")
    @ApiOperation("查询方案字典")
    public AjaxResult getProject1(Long compCode) {
        CostApportionmentProject costApportionmentProject = new CostApportionmentProject();
        costApportionmentProject.setCompCode(compCode);
        costApportionmentProject.setProjectType("1");
        List<CostApportionmentProject> list = costApportionmentProjectService.selectCostApportionmentProjectList(costApportionmentProject);
        List<AssetsBasicPublicVo>  data = new ArrayList<AssetsBasicPublicVo>();
        if(list != null && list.size()>0){
            list.stream().forEach(item->{
                AssetsBasicPublicVo abpv = new AssetsBasicPublicVo();
                abpv.setDictValue(item.getProjectCode());
                abpv.setDictLabel(item.getProjectName());
                data.add(abpv);
            });
        }
        return AjaxResult.success(data);
    }

    /**
     * 查询方案字典
     */
    @GetMapping("/getProject2")
    @ApiOperation("查询方案字典")
    public AjaxResult getProject2(Long compCode) {
        CostApportionmentProject costApportionmentProject = new CostApportionmentProject();
        costApportionmentProject.setCompCode(compCode);
        costApportionmentProject.setProjectType("2");
        List<CostApportionmentProject> list = costApportionmentProjectService.selectCostApportionmentProjectList(costApportionmentProject);
        List<AssetsBasicPublicVo>  data = new ArrayList<AssetsBasicPublicVo>();
        if(list != null && list.size()>0){
            list.stream().forEach(item->{
                AssetsBasicPublicVo abpv = new AssetsBasicPublicVo();
                abpv.setDictValue(item.getProjectCode());
                abpv.setDictLabel(item.getProjectName());
                data.add(abpv);
            });
        }
        return AjaxResult.success(data);
    }

    /**
     * 根据单位查询分摊方案
     */
    @GetMapping("/getCostApport")
    @ApiOperation("根据单位查询分摊方案")
    public AjaxResult getCostApport(Long compCode) {
        CostApport costApport = new CostApport();
        costApport.setCompCode(compCode);
        List<CostApport> costApports = costApportService.selectCostApportListOrderByNowDate(costApport);
        Calendar cal=Calendar.getInstance();
        if (costApports.size() > 0) {
            cal.setTime(costApports.get(0).getNowDate());
            cal.add(Calendar.MONTH,1);
        } else {
            cal.set(Calendar.DAY_OF_MONTH,1);
            cal.set(Calendar.HOUR_OF_DAY,0);
            cal.set(Calendar.MINUTE,0);
            cal.set(Calendar.SECOND,0);
            cal.set(Calendar.MILLISECOND,0);
        }
        return AjaxResult.success(cal.getTime());
    }

    @PostMapping("/insertUser")
    @ApiOperation("新增用户")
    public AjaxResult insertUser(@Validated @RequestBody SysUser user) {
        if ("1".equals(this.userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && "1".equals(this.userService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && "1".equals(this.userService.checkEmailUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        } else {
            user.setCreateBy(SecurityUtils.getUsername());
            user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
            return this.toAjax(this.spdSysUserService.insertUser(user));

        }
    }
    @PostMapping("/insertUserNew")
    @ApiOperation("新增用户")
    public AjaxResult insertUserNew(@Validated @RequestBody SysUser user) {
        if ("1".equals(this.userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && "1".equals(this.userService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && "1".equals(this.userService.checkEmailUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        } else {
            if(null == user.getDeptId()){
                user.setDeptId(user.getDept().getDeptId());
            }
            user.setCreateBy(SecurityUtils.getUsername());
            user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
            return this.toAjax(this.spdSysUserService.insertUser(user));

        }
    }
    @PutMapping("/updateUser")
    @ApiOperation("修改用户")
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        this.userService.checkUserAllowed(user);
        if (StringUtils.isNotEmpty(user.getPhonenumber()) && "1".equals(this.userService.checkPhoneUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && "1".equals(this.userService.checkEmailUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        } else {
            user.setUpdateBy(SecurityUtils.getUsername());
            return this.toAjax(this.spdSysUserService.updateUser(user));
        }
    }

    /**
     * 查询二次分配指标集字典
     */
    @GetMapping("/getDistribution")
    @ApiOperation("查询二次分配指标集字典")
    public AjaxResult getDistribution(PmsSecondDistribution pmsSecondDistribution) {
        List<PmsSecondDistribution> list =  pmsSecondDistributionService.selectPmsSecondDistributionList(pmsSecondDistribution);
        List<AssetsBasicPublicVo>  data = new ArrayList<AssetsBasicPublicVo>();
        if(list != null && list.size()>0){
            list.stream().forEach(item->{
                AssetsBasicPublicVo abpv = new AssetsBasicPublicVo();
                abpv.setDictValue(item.getDisCode());
                abpv.setDictLabel(item.getDisName());
                data.add(abpv);
            });
        }
        return AjaxResult.success(data);
    }


}
