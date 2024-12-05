package com.zjry.pms.project.controller;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.StringUtils;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.apportionment.domain.CostApportionmentProject;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectService;
import com.zjry.pms.common.Constants;
import com.zjry.pms.encode.domain.HrEncodeRuleTree;
import com.zjry.pms.encode.service.IHrEncodeRuleTreeService;
import com.zjry.pms.project.domain.CostProject;
import com.zjry.pms.project.domain.CostProjectDept;
import com.zjry.pms.project.service.ICostProjectDeptService;
import com.zjry.pms.project.service.ICostProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 成本项目管理Controller
 *
 * @author mk
 * @date 2022-05-06
 */
@RestController
@RequestMapping("/project/project")
@Api(tags = {"【成本项目管理】"})
public class CostProjectController extends BaseController {
    @Autowired
    private ICostProjectService costProjectService;
    @Autowired
    private IHrEncodeRuleTreeService hrEncodeRuleTreeService;
    @Autowired
    private ICostProjectDeptService costProjectDeptService;
    @Autowired
    private ICostApportionmentProjectService costApportionmentProjectService;

    /**
     * 收入项目管理科室设置
     */
    @PreAuthorize("@ss.hasPermi('project:project:edit')")
    @Log(title = "收入项目管理科室设置", businessType = BusinessType.UPDATE)
    @PutMapping("/dept")
    @ApiOperation("收入项目管理科室设置")
    public AjaxResult dept(@RequestBody CostProject costProject) {
        try {
            //删除当前科室设置
            costProjectDeptService.deleteCostProjectDeptByProjectCode(costProject.getProjectCode());

            if (!ObjectUtils.isEmpty(costProject.getDeptList()) && costProject.getDeptList().size() != 0) {
                CostProjectDept costProjectDept = new CostProjectDept();
                costProjectDept.setCompCode(costProject.getCompCode());
                costProjectDept.setProjectCode(costProject.getProjectCode());
                for (String deptCode : costProject.getDeptList()) {
                    costProjectDept.setDeptCode(Long.parseLong(deptCode));
                    costProjectDeptService.insertCostProjectDept(costProjectDept);
                }
            }

            return toAjax(costProjectService.updateCostProject(costProject));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 查询成本项目管理列表
     */
    @PreAuthorize("@ss.hasPermi('project:project:list')")
    @GetMapping("/list")
    @ApiOperation("查询成本项目管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(CostProject costProject) {
        startPage();
        List<CostProject> list = costProjectService.selectCostProjectList(costProject);
        return getDataTable(list);
    }

    /**
     * 导出成本项目管理列表
     */
    @PreAuthorize("@ss.hasPermi('project:project:export')")
    @Log(title = "成本项目管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出成本项目管理列表Excel")
    public AjaxResult export(HttpServletResponse response, CostProject costProject) {

        /*// 获取服务器本地的文件位置
        File file = new File("E:\\DEMO\\zjry-cost\\src\\main\\resources\\static\\成本项目管理数据.xlsx");

        if (file.exists()) {
            BufferedInputStream bufferedInputStream = null;
            BufferedOutputStream bufferedOutputStream = null;
            try {

                // 清除buffer缓存
                response.reset();
                // 指定下载的文件名
                response.setHeader("Content-Disposition",
                        "attachment;filename="+"成本项目管理数据.xlsx"+"");
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache");



                FileInputStream inputStream = new FileInputStream(file);
                bufferedInputStream = new BufferedInputStream(inputStream); //缓冲流加速读
                OutputStream outputStream = response.getOutputStream();
                bufferedOutputStream = new BufferedOutputStream(outputStream);  //缓冲流加速写
                int n;
                while ((n = bufferedInputStream.read()) != -1) {
                    bufferedOutputStream.write(n);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedOutputStream.close();
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new RuntimeException("文件在本地服务器不存在");
        }*/
        List<CostProject> list = new ArrayList<CostProject>();
        ExcelUtil<CostProject> util = new ExcelUtil<CostProject>(CostProject.class);
        return util.exportExcel(list, "成本项目管理数据");
    }

    /**
     * 获取成本项目管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:project:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取成本项目管理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(costProjectService.selectCostProjectById(id));
    }

    @PreAuthorize("@ss.hasPermi('project:project:query')")
    @GetMapping(value = "/checkCode")
    @ApiOperation("检查成本项目管理编码")
    public AjaxResult checkCode(CostProject costProject) {
        HrEncodeRuleTree hrEncodeRuleTree = new HrEncodeRuleTree();
        hrEncodeRuleTree.setRuleDb("cost_project");
        hrEncodeRuleTree.setRuleColumn("PROJECT_CODE");
        List<HrEncodeRuleTree> listHrER = hrEncodeRuleTreeService.selectHrEncodeRuleTreeList(hrEncodeRuleTree);
        String parentCode = "";
        if (listHrER.size() > 0) {
            HrEncodeRuleTree hrTree = listHrER.get(0);
            if (hrTree != null && StringUtils.isNotEmpty(hrTree.getRuleContent())) {
                //4-2-2-2
                String[] codeArray = hrTree.getRuleContent().split("-");
                //总长度
                List<String> codeList = Arrays.asList(codeArray);
                Integer allLength = codeList.stream().mapToInt(item -> Integer.parseInt(item)).sum();
                //先判断code总长度有没有超长
                if (allLength < costProject.getProjectCode().length()) {
                    return error("编码长度超出规则长度!");
                }
                boolean codeCheck = false;
                for (int i = 0; i < codeArray.length; i++) {
                    if (codeCheck == false) {
                        int codeLengt = 0;
                        for (int z = 0; z <= i; z++) {
                            codeLengt += Integer.parseInt(codeArray[z]);
                        }
                        if (costProject.getProjectCode().length() == codeLengt) {
                            codeCheck = true;
                            //如果不是第一级,在这一步把parentcode找出来
                            if (costProject.getProjectCode().length() > Integer.parseInt(codeArray[0])) {
                                parentCode =  costProject.getProjectCode().substring(0,codeLengt-Integer.parseInt(codeArray[i]));
                            }
                        }
                    }
                }
                if (codeCheck == false) {
                    return error("编码不符合规则");
                }
                CostProject cp = new CostProject();
                cp.setProjectCode(costProject.getProjectCode());
                List<CostProject> list = costProjectService.selectCostProjectList(cp);
                if (list.size() > 0) {
                    return error("该编码已经存在");
                }
                if (StringUtils.isNotEmpty(parentCode)){
                    CostProject cproject = new CostProject();
                    cproject.setProjectCode(parentCode);
                    List<CostProject> listp = costProjectService.selectCostProjectList(cproject);
                    if (listp == null || listp.size()==0) {
                        return error("找不到对应父级编码请检查");
                    }
                }
            }
        } else {
            return error("请先设置成本项目管理编码规则");
        }
        if (StringUtils.isNotEmpty(parentCode)){
            return success(parentCode);
        }else{
            return success("0");
        }

    }


    // 导入资产盘点明细
    @PostMapping("/importData")
    @ApiOperation("导入资产盘点明细")
    @ResponseBody
    public AjaxResult importData(MultipartFile file,Long compCode) {
        //验证标识
        Boolean isValidatePass = true;
        ExcelUtil<CostProject> util = new ExcelUtil<CostProject>(CostProject.class);
        List<CostProject> costProjectfies = null;
        try {
            costProjectfies = util.importExcel(file.getInputStream());
            costProjectfies.removeAll(Collections.singleton(null)); //移除所有的null元素
            if (costProjectfies.size() == 0) {
                return error("导入失败:excle格式转换失败");
            } else {
                for (CostProject cProject : costProjectfies) {
                    cProject.setReason(null);
                    StringBuilder sb = new StringBuilder();
                    //项目编码判空
                    if (StringUtils.isEmpty(cProject.getProjectCode())) {
                        sb.append("项目编码为空;");
                        isValidatePass = false;
                    }
                    //项目名称判空
                    if (StringUtils.isEmpty(cProject.getProjectName())) {
                        sb.append("项目名称为空;");
                        isValidatePass = false;
                    }
                    //上级项目判空
                    if (StringUtils.isEmpty(cProject.getParentProject())) {
                        sb.append("上级项目为空;");
                        isValidatePass = false;
                    }
                    //项目级别判空
                    if (StringUtils.isEmpty(cProject.getProjectLevel())) {
                        sb.append("项目级别为空;");
                        isValidatePass = false;
                    }
                    //项目类型判空
                    if (StringUtils.isEmpty(cProject.getProjectType())) {
                        sb.append("项目类型为空;");
                        isValidatePass = false;
                    }
                    if (StringUtils.isEmpty(cProject.getCostProperty())) {
                        sb.append("成本属性为空;");
                        isValidatePass = false;
                    }
                    if (StringUtils.isEmpty(cProject.getIsControl())) {
                        sb.append("是否可控为空;");
                        isValidatePass = false;
                    }
                    if (StringUtils.isEmpty(cProject.getIsAccount())) {
                        sb.append("是否核算为空;");
                        isValidatePass = false;
                    }
                    if (StringUtils.isEmpty(cProject.getIsCostCapital())) {
                        sb.append("是否资本性成本为空;");
                        isValidatePass = false;
                    }
                    if (StringUtils.isEmpty(cProject.getIsCostHealth())) {
                        sb.append("是否医疗成本为空;");
                        isValidatePass = false;
                    }
                    if (StringUtils.isEmpty(cProject.getIsCostCalculate())) {
                        sb.append("是否计算计入成本为空;");
                        isValidatePass = false;
                    }
                    if (StringUtils.isEmpty(cProject.getIsLast())) {
                        sb.append("是否末级为空;");
                        isValidatePass = false;
                    }

                    if(cProject.getIsCostCalculate().equals(Constants.IS_CHANGE_YES)){
                        if(StringUtils.isEmpty(cProject.getCostScheme())){
                            sb.append("计算计入成本分摊方案为空;");
                            isValidatePass = false;
                        }
                    }else{
                        cProject.setCostScheme(null);
                    }
                    if (StringUtils.isNotEmpty(sb.toString())) {
                        cProject.setReason(sb.toString());
                    }
                }
                //初次 验证 判空
                if (!isValidatePass) {
                    return util.exportExcel(costProjectfies, "错误的成本项目管理数据");
                }
                //二次 list数据本身验重
                List<CostProject> sbmcList = costProjectfies.stream().collect(
                        Collectors.collectingAndThen( Collectors.toCollection(() -> new TreeSet<CostProject>(
                                Comparator.comparing(CostProject::getProjectCode))), ArrayList::new));
                if(costProjectfies.size()>sbmcList.size()){
                    return error("导入失败:有重复数据,请核对");
                }
                //三次 校验编码规则
                HrEncodeRuleTree hrEncodeRuleTree = new HrEncodeRuleTree();
                hrEncodeRuleTree.setRuleDb("cost_project");
                hrEncodeRuleTree.setRuleColumn("PROJECT_CODE");
                List<HrEncodeRuleTree> listHrER = hrEncodeRuleTreeService.selectHrEncodeRuleTreeList(hrEncodeRuleTree);
                if(listHrER.size()>0){
                    HrEncodeRuleTree hrTree = listHrER.get(0);
                    if(hrTree!=null && StringUtils.isNotEmpty(hrTree.getRuleContent())) {
                        StringBuilder stringBuilder = new StringBuilder();
                        //4-2-2-2
                        String[] codeArray = hrTree.getRuleContent().split("-");
                        //总长度
                        List<String> codeList = Arrays.asList(codeArray);
                        Integer allLength = codeList.stream().mapToInt(item -> Integer.parseInt(item)).sum();
                        //先判断code总长度有没有超长
                        for(CostProject cp : costProjectfies){
                            if(allLength < cp.getProjectCode().length()){
                                stringBuilder.append("编码长度超出规则长度!");
                                isValidatePass = false;
                            }
                            int cpl = 0;
                            for(int i=0;i<Integer.parseInt(cp.getProjectLevel());i++ ){
                                cpl += Integer.parseInt(codeArray[i]);
                            }
                            if(cpl != cp.getProjectCode().length()){
                                stringBuilder.append("编码长度与项目等级不符!");
                                isValidatePass = false;
                            }else {
                                //匹配父编码和子编码的关系
                                boolean codeCheck = false;
                                for (int i = 0; i < codeArray.length; i++) {
                                    if (codeCheck == false) {
                                        int codeLengt = 0;
                                        for (int z = 0; z < i; z++) {
                                            codeLengt += Integer.parseInt(codeArray[z]);
                                        }
                                        if (cp.getProjectCode().length() == codeLengt || cp.getProjectCode().equals("0")) {
                                            codeCheck = true;
                                            //如果不是第一级,在这一步把parentcode找出来
                                            if (cp.getProjectCode().length() > Integer.parseInt(codeArray[0])) {
                                                String parentCode = cp.getProjectCode().substring(0, codeLengt - Integer.parseInt(codeArray[i]));
                                                //父对比,如级编码果不合适改成长度校验
                                                if (!cp.getParentProject().equals(parentCode)) {
                                                    stringBuilder.append("父级编码与子集编码关联关系错误!");
                                                    isValidatePass = false;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (codeCheck == false) {
                                    stringBuilder.append("编码不符合规则!");
                                    isValidatePass = false;
                                }
                            }
                            if(StringUtils.isNotEmpty(stringBuilder.toString())){
                                cp.setReason(stringBuilder.toString());
                            }
                        }
                    }
                }else{
                    return error("操作失败:未查询到编码规则") ;
                }
                if (!isValidatePass) {
                    return util.exportExcel(costProjectfies, "错误的成本项目管理数据");
                }
                //四次 与数据库对比
                for (CostProject cps : costProjectfies) {
                    CostProject cp = new CostProject();
                    cp.setProjectCode(cps.getProjectCode());
                    List<CostProject> list = costProjectService.selectCostProjectList(cp);
                    if (list.size() > 0) {
                        cps.setReason("该条数据与数据库数据重复");
                        isValidatePass = false;
                    }
                }

                for (CostProject cps : costProjectfies) {
                    CostProject cp = new CostProject();
                    cp.setCompCode(compCode);
                    cp.setProjectName(cps.getProjectName());
                    List<CostProject> list = costProjectService.selectCostProjectList(cp);
                    if (list.size() > 0) {
                        cps.setReason("该条数据与数据库数据重复");
                        isValidatePass = false;
                    }
                }

                for (CostProject cps : costProjectfies) {
                    if(cps.getIsCostCalculate().equals(Constants.IS_CHANGE_YES)){
                        CostApportionmentProject cap = new CostApportionmentProject();
                        cap.setProjectCode(cps.getCostScheme());
                        List<CostApportionmentProject> listcap = costApportionmentProjectService.selectCostApportionmentProjectList(cap);
                        if (listcap == null || listcap.size() == 0) {
                            cps.setReason("该计算计入成本分摊方案编码错误");
                            isValidatePass = false;
                        }
                    }

                }



                if (!isValidatePass) {
                    return util.exportExcel(costProjectfies, "错误的成本项目管理数据");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return error("导入失败:excle格式转换失败");
        }
        try {
            costProjectService.importData(costProjectfies,compCode);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success();
    }





    /**
     * 新增成本项目管理
     */
    @PreAuthorize("@ss.hasPermi('project:project:add')")
    @Log(title = "成本项目管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增成本项目管理")
    public AjaxResult add(@RequestBody CostProject costProject) {
        CostProject cp = new CostProject();
        cp.setCompCode(costProject.getCompCode());
        cp.setPName(costProject.getProjectName());
        List<CostProject> list = costProjectService.selectCostProjectList(cp);
        if (list.size() > 0) {
            return error("该名称已经存在");
        }
        try {
            costProjectService.insertCostProject(costProject);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success();
    }

    /**
     * 修改成本项目管理
     */
    @PreAuthorize("@ss.hasPermi('project:project:edit')")
    @Log(title = "成本项目管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改成本项目管理")
    public AjaxResult edit(@RequestBody CostProject costProject) {
        CostProject cp = new CostProject();
        cp.setId(costProject.getId());
        cp.setCompCode(costProject.getCompCode());
        cp.setPName(costProject.getProjectName());
        List<CostProject> list = costProjectService.selectCostProjectList(cp);
        if (list.size() > 0) {
            return error("该名称已经存在");
        }
        return toAjax(costProjectService.updateCostProject(costProject));
    }

    /**
     * 删除成本项目管理
     */
    @PreAuthorize("@ss.hasPermi('project:project:remove')")
    @Log(title = "成本项目管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除成本项目管理")
    public AjaxResult remove(@PathVariable Long[] ids) {
        try {
            String msg = costProjectService.deleteCostProjectByIds(ids);
            if(StringUtils.isNotEmpty(msg)){
                return error(msg);
            }
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success();
    }



}
