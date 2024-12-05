package com.zjry.pms.basic.domain.basicData;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 科室对象 sys_dept_room
 *
 * @author cyq
 * @date 2021-07-16
 */
@Data
public class BasicdataDeptRoomVo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 科室id */
    @ApiModelProperty(value = "${comment}")
    private Long deptRoomId;

    /** 父科室id */
    @ApiModelProperty(value = "${comment}")
    private Long parentId;

    /** 科室名称 */
    @Excel(name = "科室名称")
    @ApiModelProperty(value = "科室名称")
    private String deptRoomName;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptRoomCode;

    /** 机构ID */
    @ApiModelProperty(value = "科室编码")
    private Long deptId;

    /** 机构名称 */
    @Excel(name = "机构名称")
    @ApiModelProperty(value = "机构名称")
    private String deptName;

    /** 科室类型 */
    @Excel(name = "科室类型")
    @ApiModelProperty(value = "科室类型")
    private String type;

    /** 科室排序 */
    @ApiModelProperty(value = "科室类型")
    private Long sort;

    /** 科室级别 */
    @ApiModelProperty(value = "科室类型")
    private String grade;

    /** 负责人 */
    @ApiModelProperty(value = "科室类型")
    private String dutyOfficer;

    /** 联系电话 */
    @Excel(name = "联系电话")
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /** 科室地址 */
    @ApiModelProperty(value = "联系电话")
    private String depAddress;

    /** 版本 */
    @Excel(name = "版本")
    @ApiModelProperty(value = "版本")
    private String version;

    /** 部门状态（0正常 1停用） */
    @ApiModelProperty(value = "版本")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @ApiModelProperty(value = "版本")
    private String delFlag;

    /** 拼音码 */
    @ApiModelProperty(value = "版本")
    private String pym;

    /** 账套名称 */
    @Excel(name = "账套名称")
    @ApiModelProperty(value = "账套名称")
    private String accountSetName;

    /** 所属账套 */
    @Excel(name = "所属账套")
    @ApiModelProperty(value = "所属账套")
    private Long accountSetId;

    /** 临床科室类别（1-门诊；2-急诊） */
    @Excel(name = "临床科室类别", readConverterExp = "1=-门诊；2-急诊")
    @ApiModelProperty(value = "临床科室类别")
    private String clinicType;

    /** 是否核算（0-否；1-是） */
    @Excel(name = "是否核算", readConverterExp = "0=-否；1-是")
    @ApiModelProperty(value = "是否核算")
    private String isAccounting;

    /** 是否独立核算（0-否；1-是） */
    @Excel(name = "是否独立核算", readConverterExp = "0=-否；1-是")
    @ApiModelProperty(value = "是否独立核算")
    private String isIndependentAccounting;

    /** 内线电话 */
    @Excel(name = "内线电话")
    @ApiModelProperty(value = "内线电话")
    private String inlinePhone;

    /** 是否服务（0-否；1-是） */
    @Excel(name = "是否服务", readConverterExp = "0=-否；1-是")
    @ApiModelProperty(value = "是否服务")
    private String isService;

    /** 是否末级（0-否；1-是） */
    @Excel(name = "是否末级", readConverterExp = "0=-否；1-是")
    @ApiModelProperty(value = "是否末级")
    private String isLeaf;

    /** 是否采购（0-否；1-是） */
    @Excel(name = "是否采购", readConverterExp = "0=-否；1-是")
    @ApiModelProperty(value = "是否采购")
    private String isPurchase;

    /** 描述 */
    @Excel(name = "描述")
    @ApiModelProperty(value = "描述")
    private String description;
    private String parentName;
    private List<BasicdataDeptRoomVo> children = new ArrayList();
    private boolean hasChildren;
    /** 标志，0单位，1科室 */
    private String tag;
    /** id */
    private Long tagId;

}
