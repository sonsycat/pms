package com.zjry.pms.foundation.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 分摊计算对象 cost_apport
 *
 * @author yzh
 * @date 2022-05-17
 */
@Data
@ApiModel(value = "CostApport", description = "分摊计算对象 cost_apport")
public class PmsFundationEquivalent extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;
    @ApiModelProperty(value = "科室编码")
    private String equivalentDeptCode;
    @ApiModelProperty(value = "科室名称")
    private String equivalentDeptName;
    @ApiModelProperty(value = "项目编码")
    private String equivalentItemCode;
    @ApiModelProperty(value = "项目名称")
    private String equivalentItemName;
    @ApiModelProperty(value = "类别")
    private String equivalentTypeName;
    @ApiModelProperty(value = "规格")
    private String equivalentUnit;
    @ApiModelProperty(value = "价格")
    private String equivalentPrice;
    @ApiModelProperty(value = "医嘱标准当量")
    private String doctorAdviceNum;
    @ApiModelProperty(value = "医技标准当量")
    private String doctorTechNum;
    @ApiModelProperty(value = "护理标准当量")
    private String doctorNurseNum;
    @ApiModelProperty(value = "分组名称")
    private String groupName;
    private String matchDeptName;

}
