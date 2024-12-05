package com.zjry.pms.bonus.domain;

import com.zjry.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PmsStatementEquivalent implements Serializable {
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;
    @Excel(name = "病区代码")
    private String illnessCode;
    @Excel(name = "病区名称")
    private String illnessName;
    @Excel(name = "医嘱科室")
    private String yzDeptCode;
    @Excel(name = "医嘱科室名称")
    private String yzDeptName;
    @Excel(name = "人事考勤科室")
    private String hrDeptName;
    @Excel(name = "医技考核组")
    private String dtDeptName;
    @Excel(name = "科室代码")
    private String orderedByDept;
    @Excel(name = "科室名称")
    private String deptName;
    @Excel(name = "医生工号")
    private String orderedByDoctor;
    @Excel(name = "医生姓名")
    private String userName;
    @Excel(name = "医疗组代码")
    private String zlCode;
    @Excel(name = "核算名称")
    private String className;
    @Excel(name = "医疗组名称")
    private String zlName;
    @Excel(name = "核算代码")
    private String classOnReckoning;
    @Excel(name = "项目代码")
    private String itemCode;
    @Excel(name = "项目名称")
    private String itemName;
    @Excel(name = "物价")
    private String itemPrice;
    @Excel(name = "数量")
    private String number;
    @Excel(name = "标准当量")
    private String bzNum;
    @Excel(name = "绩效当量")
    private String jxNum;
    @Excel(name = "总当量")
    private String totalNum;
    @Excel(name = "状态")
    private String state;
    private String equivalentTypeCode;
    private Date dateTime;
    private String  date;
    private String  zyDeptCode;
    private String zyDeptName;
}
