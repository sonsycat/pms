package com.zjry.pms.table.domain;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class PmsTableIncsumRes extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "科室名称")
    @Excel(name = "科室名称")
    private String deptName;
    @ApiModelProperty(value = "类别")
    @Excel(name = "类别")
    private String deptType;
    @ApiModelProperty(value = "合计")
    @Excel(name = "合计")
    private String total;
    @ApiModelProperty(value = "药品")
    @Excel(name = "药品")
    private String yp;
    @ApiModelProperty(value = "检验")
    @Excel(name = "检验")
    private String jy;
    @ApiModelProperty(value = "检查")
    @Excel(name = "检查")
    private String jc;
    @ApiModelProperty(value = "治疗")
    @Excel(name = "治疗")
    private String zl;
    @ApiModelProperty(value = "放射")
    @Excel(name = "放射")
    private String fs;
    @ApiModelProperty(value = "手术")
    @Excel(name = "手术")
    private String ss;
    @ApiModelProperty(value = "输血")
    @Excel(name = "输血")
    private String sx;
    @ApiModelProperty(value = "护理")
    @Excel(name = "护理")
    private String hl;
    @ApiModelProperty(value = "挂号")
    @Excel(name = "挂号")
    private String gh;
    @ApiModelProperty(value = "床位")
    @Excel(name = "床位")
    private String cw;
    @ApiModelProperty(value = "其它")
    @Excel(name = "其它")
    private String qt;
    @ApiModelProperty(value = "治疗收入")
    @Excel(name = "治疗收入")
    private String zlsr;
    @ApiModelProperty(value = "诊疗费")
    @Excel(name = "诊疗费")
    private String zlf;
    @ApiModelProperty(value = "麻醉")
    @Excel(name = "麻醉")
    private String mz;
    @ApiModelProperty(value = "中心")
    @Excel(name = "中心")
    private String zx;
    @ApiModelProperty(value = "材料")
    @Excel(name = "材料")
    private String cl;






}
