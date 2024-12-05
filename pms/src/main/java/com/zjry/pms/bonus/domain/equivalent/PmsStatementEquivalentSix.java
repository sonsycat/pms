package com.zjry.pms.bonus.domain.equivalent;

import com.zjry.common.annotation.Excel;
import lombok.Data;

@Data
public class PmsStatementEquivalentSix {
    @Excel(name = "病区代码",sort = 1)
    private String illnessCode;
    @Excel(name = "病区名称",sort = 2)
    private String illnessName;
//    @Excel(name = "医嘱科室")
//    private String yzDeptCode;
//    @Excel(name = "医嘱科室名称")
//    private String yzDeptName;
//    @Excel(name = "人事考勤科室")
//    private String hrDeptName;
//    @Excel(name = "医技考核组")
//    private String dtDeptName;
//    @Excel(name = "科室代码")
//    private String orderedByDept;
//    @Excel(name = "科室名称")
//    private String deptName;
    @Excel(name = "医生工号",sort = 3)
    private String orderedByDoctor;
    @Excel(name = "医生姓名",sort = 4)
    private String userName;
    @Excel(name = "医疗组代码",sort = 5)
    private String zlCode;
    @Excel(name = "核算名称",sort = 8)
    private String className;
    @Excel(name = "医疗组名称",sort = 6)
    private String zlName;
    @Excel(name = "核算代码",sort = 7)
    private String classOnReckoning;
    @Excel(name = "项目代码",sort = 9)
    private String itemCode;
    @Excel(name = "项目名称",sort = 10)
    private String itemName;
    @Excel(name = "物价",sort = 11)
    private String itemPrice;
    @Excel(name = "数量",sort = 12)
    private String number;
    @Excel(name = "标准当量",sort = 13)
    private String bzNum;
    @Excel(name = "绩效当量",sort = 14)
    private String jxNum;
//    @Excel(name = "总当量")
//    private String totalNum;
//    @Excel(name = "状态")
//    private String state;
}
