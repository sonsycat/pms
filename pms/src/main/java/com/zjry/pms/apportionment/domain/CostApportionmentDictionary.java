package com.zjry.pms.apportionment.domain;

import java.io.Serializable;
import java.util.List;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 比例分摊字典管理对象 cost_apportionment_dictionary
 *
 * @author yzh
 * @date 2022-05-10
 */
@Data
@ApiModel(value = "CostApportionmentDictionary", description = "比例分摊字典管理对象 cost_apportionment_dictionary")
public class CostApportionmentDictionary extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 单位编码 */
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 比例编码 */
    @Excel(name = "比例编码")
    @ApiModelProperty(value = "比例编码")
    private String ratioCode;

    /** 比例名称 */
    @Excel(name = "比例名称")
    @ApiModelProperty(value = "比例名称")
    private String ratioName;

    /** 比例类型 */
    @Excel(name = "比例类型")
    @ApiModelProperty(value = "比例类型")
    private String ratioType;
    
    /** 比例类型 */
    @Excel(name = "数据连接编码")
    @ApiModelProperty(value = "数据连接编码")
    private String coonectCode;

    /** 计算计入成本分摊SQL */
    @Excel(name = "计算计入成本分摊SQL")
    @ApiModelProperty(value = "计算计入成本分摊SQL")
    private String countSql;

    /** 一级分摊SQL */
    @Excel(name = "一级分摊SQL")
    @ApiModelProperty(value = "一级分摊SQL")
    private String firstLevelSql;

    /** 二级分摊SQL */
    @Excel(name = "二级分摊SQL")
    @ApiModelProperty(value = "二级分摊SQL")
    private String secondLevelSql;

    /** 三级分摊SQL */
    @Excel(name = "三级分摊SQL")
    @ApiModelProperty(value = "三级分摊SQL")
    private String thirdLevelSql;
    
    @ApiModelProperty(value = "一级分摊")
    private List<CostApportionmentDictionaryDetail> costApportionmentDictionaryDetailList1;
    
    @ApiModelProperty(value = "二级分摊")
    private List<CostApportionmentDictionaryDetail> costApportionmentDictionaryDetailList2;
    
    @ApiModelProperty(value = "三级分摊")
    private List<CostApportionmentDictionaryDetail> costApportionmentDictionaryDetailList3;
    
    @ApiModelProperty(value = "计算分摊")
    private List<CostApportionmentDictionaryDetail> costApportionmentDictionaryDetailList4;
    
    @ApiModelProperty(value = "期望报错")
    private String exception;

}
