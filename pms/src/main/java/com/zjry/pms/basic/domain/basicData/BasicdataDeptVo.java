package com.zjry.pms.basic.domain.basicData;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 单位对象
 *
 * @author cyq
 * @date 2021-07-15
 */
@Data
public class BasicdataDeptVo extends BaseEntity implements Serializable {

    /** 单位id */
    private Long deptId;

    /** 父单位id */
    @ApiModelProperty(value = "${comment}")
    private Long parentId;

    /** 祖级列表 */
    @ApiModelProperty(value = "${comment}")
    private String ancestors;

    /** 单位名称 */
    @Excel(name = "单位名称")
    @ApiModelProperty(value = "单位名称")
    private String deptName;

    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private String deptCode;

    /** 显示顺序 */
    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    /** 负责人 */
    @Excel(name = "负责人")
    @ApiModelProperty(value = "负责人")
    private String leader;

    /** 联系电话 */
    @Excel(name = "联系电话")
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /** 单位状态（0正常 1停用） */
    @Excel(name = "单位状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value = "单位状态")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @ApiModelProperty(value = "单位状态")
    private String delFlag;

    /** 拼音码 */
    @Excel(name = "拼音码")
    @ApiModelProperty(value = "拼音码")
    private String pym;

    /** 所在省 */
    @ApiModelProperty(value = "所在省")
    private Long province;
    @Excel(name = "所在省")
    private String provinceStr;

    /** 所在市 */
    @ApiModelProperty(value = "所在市")
    private Long city;
    @Excel(name = "所在市")
    private String cityStr;


    /** 详细地址 */
    @Excel(name = "详细地址")
    @ApiModelProperty(value = "详细地址")
    private String address;

    /** 联系人 */
    @Excel(name = "联系人")
    @ApiModelProperty(value = "联系人")
    private String contactPerson;

    /** 税务证号 */
    @Excel(name = "税务证号")
    @ApiModelProperty(value = "税务证号")
    private String taxNumber;

    /** 主管会计 */
    @Excel(name = "主管会计")
    @ApiModelProperty(value = "主管会计")
    private String chiefAccountant;

    /** 是否末级(0-否；1-是) */
    @ApiModelProperty(value = "是否末级(0-否；1-是)")
    private String isLeaf;

    /** 上级单位 */
    private String parentDeptName;
    private List<BasicdataDeptVo> children = new ArrayList();
    private boolean hasChildren;
    /** 标志，0单位，1科室 */
    private String tag;
    /** id */
    private Long tagId;


}
