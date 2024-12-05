package com.zjry.pms.project.controller;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.zjry.common.utils.StringUtils;
import com.zjry.pms.apport.domain.CostApport;
import com.zjry.pms.apport.service.ICostApportService;
import com.zjry.pms.common.utils.DateUtils;
import com.zjry.pms.project.domain.*;
import com.zjry.pms.project.service.ICostProjectDeptService;
import com.zjry.pms.project.service.ICostProjectNumSqlService;
import com.zjry.pms.project.service.ICostProjectService;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.enums.BusinessType;
import com.zjry.pms.project.service.ICostProjectNumService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import static java.util.regex.Pattern.*;

/**
 * 成本金额管理Controller
 *
 * @author mk
 * @date 2022-05-18
 */
@RestController
@RequestMapping("/project/num")
@Api(tags = {"【成本金额管理】"})
public class CostProjectNumController extends BaseController {
    @Autowired
    private ICostProjectNumService costProjectNumService;
    @Autowired
    private ICostProjectService costProjectService;
    @Autowired
    private ICostApportService costApportService;



    @Autowired
    private ICostProjectDeptService costProjectDeptService;
    @Autowired
    private ICostProjectNumSqlService costProjectNumSqlService;


    /**
     * 查询成本金额管理列表
     */
    @PreAuthorize("@ss.hasPermi('project:num:list')")
    @GetMapping("/list")
    @ApiOperation("查询成本金额管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(CostProjectNum costProjectNum) {
        startPage();
        List<CostProjectNum> list = costProjectNumService.selectCostProjectNumList(costProjectNum);
        return getDataTable(list);
    }

    /**
     * 新增成本sql
     */
    @Log(title = "成本sql", businessType = BusinessType.INSERT)
    @PostMapping("/inertBySql")
    @ApiOperation("新增成本sql")
    public AjaxResult inertBySql(@RequestBody CostProjectNumSql costProjectNumSql) {
        try {
            String msg = costProjectNumSqlService.inertBySql(costProjectNumSql);
            if (StringUtils.isNotEmpty(msg)) {
                return error(msg);
            }
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success();
    }


    /**
     * 查询成本金额管理列表
     */
    @PreAuthorize("@ss.hasPermi('project:num:list')")
    @GetMapping("/checkInfo")
    @ApiOperation("新增时查询前置接口")
    public Boolean checkInfo(CostProjectNum costProjectNum) {
        CostApport ca = new CostApport();
        ca.setNowDate(costProjectNum.getNowDate());
        ca.setCompCode(costProjectNum.getCompCode());
        List<CostApport> cas = costApportService.selectCostApportList(ca);
        if (cas.size() > 0) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 查询成本金额管理列表
     */
    @PreAuthorize("@ss.hasPermi('project:num:list')")
    @GetMapping("/headList")
    @ApiOperation("表头")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public List headList(CostProjectNum costProjectNum) {
        CostProject cp = new CostProject();
        cp.setProjectLevel(costProjectNum.getProjectLevel());
        cp.setCompCode(costProjectNum.getCompCode());
        List<CostProject> list = costProjectService.selectCostProjectList(cp);
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
        Map map1 = new HashMap<String, Object>();
        map1.put("label", "年月");
        map1.put("prop", "nowDate");
        newList.add(map1);
        Map map4 = new HashMap<String, Object>();
        map4.put("label", "科室");
        map4.put("prop", "deptName");
        newList.add(map4);
        Map map2 = new HashMap<String, Object>();
        map2.put("label", "科室编码");
        map2.put("prop", "deptCode");
        newList.add(map2);
        Map map3 = new HashMap<String, Object>();
        map3.put("label", "合计");
        map3.put("prop", "money");
        newList.add(map3);
        for (CostProject cps : list) {
            Map map = new HashMap<String, Object>();
            map.put("label", cps.getProjectName());
            map.put("prop", cps.getProjectCode());
            newList.add(map);
        }

        return newList;
    }

    /**
     * 查询成本金额管理列表
     */
    @PreAuthorize("@ss.hasPermi('project:num:list')")
    @GetMapping("/queryList")
    @ApiOperation("查询成本金额管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult resultList(CostProjectNum costProjectNum) {
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
        List<Long> deptCost = costProjectNumService.selectCostPNDept(costProjectNum);
        List<CostProjectNum> allCpnList = costProjectNumService.selectCostProjectNumList(costProjectNum);
        //查出该级别下的长度
        CostProject cp = new CostProject();
        cp.setProjectLevel(costProjectNum.getProjectLevel());
        cp.setCompCode(costProjectNum.getCompCode());
        List<CostProject> listC = costProjectService.selectCostProjectList(cp);
        if (listC.size() == 0) {
            return error("未查询到当前级别");
        }
        int codeLen = listC.get(0).getProjectCode().length();
        List<CostProjectNum> allList = new ArrayList<>();
        for (CostProjectNum cpNum : allCpnList) {
            if (cpNum.getProjectCode().length() >= codeLen) {
                allList.add(cpNum);
            }
        }
        if (allList != null && allList.size() > 0) {
            for (Long dept : deptCost) {
                List<CostProjectNum> list = new ArrayList<CostProjectNum>();
                for (CostProjectNum itemCpn : allList) {
                    if (dept.equals(itemCpn.getDeptCode())) {
                        list.add(itemCpn);
                    }
                }
                if (list.size() > 0) {
                    Map map = new HashMap<String, Object>();
                    Double allmoney = list.stream().mapToDouble(item -> item.getMoney()).sum();
                    map.put("nowDate", DateUtils.formatDate(list.get(0).getNowDate(), "yyyy-MM"));
                    map.put("deptName", list.get(0).getDeptName());
                    map.put("deptCode", list.get(0).getDeptCode());
                    map.put("money", allmoney);

                    List<CostProjectNum> checkList = new ArrayList<CostProjectNum>();
                    for (CostProjectNum cpn : list) {
                        CostProjectNum costN = new CostProjectNum();
                        costN.setProjectCode(cpn.getProjectCode().substring(0, codeLen));
                        costN.setMoney(cpn.getMoney());
                        checkList.add(costN);
                    }
                    HashMap<String, CostProjectNum> map1 = new HashMap<>();
                    for (CostProjectNum item : checkList) {// 遍历原集合
                        if (map1.containsKey(item.getProjectCode())) {// 若map中的key包含该型号
                            CostProjectNum itemDTO = map1.get(item.getProjectCode());// 取出该对象，与集合中相同的对象进行数量合并
                            itemDTO.setMoney(item.getMoney() + itemDTO.getMoney());
                            map1.put(item.getProjectCode(), itemDTO);// 将合并数量的该对象重新存入map集合，因key值相同，所以会覆盖掉之前的对象
                        } else {
                            map1.put(item.getProjectCode(), item);
                        }
                    }
                    List<CostProjectNum> listall = new ArrayList<>();
                    for (String model : map1.keySet()) {// 将map中的对象重新存放新的List集合
                        CostProjectNum itemDTO = map1.get(model);
                        listall.add(itemDTO);
                    }
                    for (CostProjectNum item : listall) {
                        map.put(item.getProjectCode(), item.getMoney());
                    }
                    newList.add(map);
                }
            }
        } else {
            return AjaxResult.error("当前日期未查询到对应数据");
        }
        return AjaxResult.success(newList);
    }


    // 导入资产盘点明细
    @PostMapping("/importData")
    @ApiOperation("导入成本金额管理")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, Long compCode) {
        //验证标识
        Boolean isValidatePass = true;
        ExcelUtil<CostProjectNum> util = new ExcelUtil<CostProjectNum>(CostProjectNum.class);
        List<CostProjectNum> costProjectfies = null;
        try {
            costProjectfies = util.importExcel(file.getInputStream());
            costProjectfies.removeAll(Collections.singleton(null)); //移除所有的null元素
            if (costProjectfies.size() == 0) {
                return error("导入失败:excle格式转换失败");
            } else {
                for (CostProjectNum cProject : costProjectfies) {
                    cProject.setReason(null);
                    StringBuilder sb = new StringBuilder();
                    //项目编码判空
                    if (StringUtils.isEmpty(cProject.getProjectCode())) {
                        sb.append("项目编码为空;");
                        isValidatePass = false;
                    }
                    //项目名称判空
                    if (cProject.getDeptCode() == null) {
                        sb.append("科室编码为空;");
                        isValidatePass = false;
                    }
                    //上级项目判空
                    if (cProject.getNowDate() == null) {
                        sb.append("年月为空;");
                        isValidatePass = false;
                    }
                    //项目级别判空
                    if (cProject.getMoney() == null) {
                        sb.append("成本金额为空;");
                        isValidatePass = false;
                    }

                    if (StringUtils.isNotEmpty(sb.toString())) {
                        cProject.setReason(sb.toString());
                    }
                }
                //初次 验证 判空
                if (!isValidatePass) {
                    return util.exportExcel(costProjectfies, "错误的成本录入数据");
                }
//                //二次 list数据本身验重
//                List<CostProjectNum> sbmcList = costProjectfies.stream().collect(
//                        Collectors.collectingAndThen( Collectors.toCollection(() -> new TreeSet<CostProjectNum>(
//                                Comparator.comparing(CostProject::get))), ArrayList::new));
//                if(costProjectfies.size()>sbmcList.size()){
//                    return error("导入失败:有重复数据,请核对");
//
                List<CostProject> list = costProjectService.getDeptList(compCode.toString());
                List<Long> depts = list.stream().map(CostProject::getDeptCode).collect(Collectors.toList());
                //四次 与数据库对比
                for (CostProjectNum cpn : costProjectfies) {
                    if (!depts.contains(cpn.getDeptCode())) {
                        cpn.setReason("科室是不是当前登录用户机构的科室;");
                        isValidatePass = false;
                    }
                }
                if (!isValidatePass) {
                    return util.exportExcel(costProjectfies, "错误的成本录入数据");
                }

                for (CostProjectNum cpn : costProjectfies) {
                    CostProjectDept cp = new CostProjectDept();
                    cp.setProjectCode(cpn.getProjectCode());
                    cp.setDeptCode(cpn.getDeptCode());
                    List<CostProjectDept> listCPD = costProjectDeptService.selectCostProjectDeptList(cp);
                    if (listCPD == null || listCPD.size() == 0) {
                        cpn.setReason("成本项目和科室没有对应关系");
                        isValidatePass = false;
                    }
                }
                if (!isValidatePass) {
                    return util.exportExcel(costProjectfies, "错误的成本录入数据");
                }

                for (CostProjectNum cpn : costProjectfies) {
                    Pattern pattern = compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){2})?$");
                    Matcher match = pattern.matcher(cpn.getMoney().toString());
                    if (!match.matches()) {
                        cpn.setReason("成本值不是两位小数的数字");
                        isValidatePass = false;
                    }
                }
                if (!isValidatePass) {
                    return util.exportExcel(costProjectfies, "错误的成本录入数据");
                }

                for (CostProjectNum cpn : costProjectfies) {
                    CostApport cp = new CostApport();
                    cp.setNowDate(cpn.getNowDate());
                    cp.setCompCode(compCode);
                    List<CostApport> listCPD = costApportService.selectCostApportList(cp);
                    if (listCPD.size() > 0) {
                        cpn.setReason("当前年月已经有分摊数据");
                        isValidatePass = false;
                    }
                }
                if (!isValidatePass) {
                    return util.exportExcel(costProjectfies, "错误的成本录入数据");
                }


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return error("导入失败:excle格式转换失败");
        }
        try {
            costProjectNumService.importData(costProjectfies, compCode);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success();
    }


    /**
     * 导出成本金额管理列表
     */
    @PreAuthorize("@ss.hasPermi('project:num:export')")
    @Log(title = "成本金额管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出成本金额管理列表Excel")
    public AjaxResult export(CostProjectNum costProjectNum) {
        List<CostProjectNum> list = new ArrayList<>();
        ExcelUtil<CostProjectNum> util = new ExcelUtil<CostProjectNum>(CostProjectNum.class);
        return util.exportExcel(list, "成本录入数据");
    }

    /**
     * 获取成本金额管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:num:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取成本金额管理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(costProjectNumService.selectCostProjectNumById(id));
    }


    /**
     * 新增成本金额管理
     */
    @PreAuthorize("@ss.hasPermi('project:num:add')")
    @Log(title = "成本金额管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增成本金额管理")
    public AjaxResult add(@RequestBody CostNumList costNumList) {
        try {
            String msg = costProjectNumService.insertCostProjectNum(costNumList);
            if (StringUtils.isNotEmpty(msg)) {
                return error(msg);
            }
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success();

    }

    /**
     * 修改成本金额管理
     */
    @PreAuthorize("@ss.hasPermi('project:num:edit')")
    @Log(title = "成本金额管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改成本金额管理")
    public AjaxResult edit(@RequestBody CostProjectNum costProjectNum) {
        return toAjax(costProjectNumService.updateCostProjectNum(costProjectNum));
    }


    /**
     * 根据科室和日期删除
     */
    @PreAuthorize("@ss.hasPermi('project:num:remove')")
    @Log(title = "成本金额管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/del")
    @ApiOperation("根据科室和日期删除")
    public AjaxResult remove(Long deptCode, Date nowDate) {
        CostProjectNum cpn = new CostProjectNum();
        cpn.setDeptCode(deptCode);
        cpn.setNowDate(nowDate);
        List<CostProjectNum> listc = costProjectNumService.selectCostProjectNumList(cpn);
        CostApport ca = new CostApport();
        ca.setNowDate(nowDate);
        ca.setCompCode(listc.get(0).getCompCode());
        List<CostApport> cas = costApportService.selectCostApportList(ca);
        if (cas != null && cas.size() > 0) {
            return error("该成本录入已经参加分摊的数据不能删除");
        }
        return toAjax(costProjectNumService.deleteByCodeAndDate(deptCode, nowDate));
    }

    /**
     * 删除成本金额管理
     */
    @PreAuthorize("@ss.hasPermi('project:num:remove')")
    @Log(title = "成本金额管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除成本金额管理")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(costProjectNumService.deleteCostProjectNumByIds(ids));
    }
}
