package com.zjry.pms.apportionment.domain;

import java.io.Serializable;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 比例分摊字典明细对象 cost_apportionment_dictionary_detail
 *
 * @author yzh
 * @date 2022-05-12
 */
@Data
@ApiModel(value = "CostApportionmentDictionaryDetail", description = "比例分摊字典明细对象 cost_apportionment_dictionary_detail")
public class CostApportionmentDictionaryDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 比例编码 */
    @Excel(name = "比例编码")
    @ApiModelProperty(value = "比例编码")
    private String ratioCode;

    /** 比例名称 */
    @Excel(name = "比例名称")
    @ApiModelProperty(value = "比例名称")
    private String ratioName;

    /** 分摊或计算类型 */
    @Excel(name = "分摊或计算类型")
    @ApiModelProperty(value = "分摊或计算类型")
    private String levelType;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptCode;

    /** 科室名称 */
    @Excel(name = "科室名称")
    @ApiModelProperty(value = "科室名称")
    private String deptName;

    /** 使用比例 */
    @Excel(name = "使用比例")
    @ApiModelProperty(value = "使用比例")
    private String useRatio;

    /** 年月 */
    @Excel(name = "年月")
    @ApiModelProperty(value = "年月")
    private String ny;

    /** 人数 */
    @Excel(name = "人数")
    @ApiModelProperty(value = "人数")
    private String num;

}
