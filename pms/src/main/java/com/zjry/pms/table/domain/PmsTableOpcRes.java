package com.zjry.pms.table.domain;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class PmsTableOpcRes extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "科室名称")
    @Excel(name = "科室名称")
    private String deptName;
    @ApiModelProperty(value = "合计")
    @Excel(name = "合计")
    private String total;
    @ApiModelProperty(value = "西药费")
    @Excel(name = "西药费")
    private String xyFee;
    @ApiModelProperty(value = "中成药")
    @Excel(name = "中成药")
    private String zyFee;
    @ApiModelProperty(value = "草药费")
    @Excel(name = "草药费")
    private String cyFee;
    @ApiModelProperty(value = "特殊材料费")
    @Excel(name = "特殊材料费")
    private String tsclFee;
    @ApiModelProperty(value = "诊查费")
    @Excel(name = "诊查费")
    private String zcFee;
    @ApiModelProperty(value = "挂号费")
    @Excel(name = "挂号费")
    private String ghFee;
    @ApiModelProperty(value = "体检费")
    @Excel(name = "体检费")
    private String tjFee;
    @ApiModelProperty(value = "救护车费")
    @Excel(name = "救护车费")
    private String jhcFee;
    @ApiModelProperty(value = "取暖费")
    @Excel(name = "取暖费")
    private String qnFee;
    @ApiModelProperty(value = "床位费")
    @Excel(name = "床位费")
    private String cwFee;
    @ApiModelProperty(value = "会诊费")
    @Excel(name = "会诊费")
    private String hzFee;
    @ApiModelProperty(value = "护理费")
    @Excel(name = "护理费")
    private String hlFee;
    @ApiModelProperty(value = "抢救费")
    @Excel(name = "抢救费")
    private String qjFee;
    @ApiModelProperty(value = "注射费")
    @Excel(name = "注射费")
    private String zsFee;
    @ApiModelProperty(value = "治疗费")
    @Excel(name = "治疗费")
    private String zlFee;
    @ApiModelProperty(value = "检查费")
    @Excel(name = "检查费")
    private String jcFee;
    @ApiModelProperty(value = "输血费")
    @Excel(name = "输血费")
    private String sxFee;
    @ApiModelProperty(value = "彩超费")
    @Excel(name = "彩超费")
    private String ccFee;
    @ApiModelProperty(value = "放射费")
    @Excel(name = "放射费")
    private String fsFee;
    @ApiModelProperty(value = "检验费")
    @Excel(name = "检验费")
    private String jyFee;
    @ApiModelProperty(value = "病理费")
    @Excel(name = "病理费")
    private String blFee;
    @ApiModelProperty(value = "脑电图费")
    @Excel(name = "脑电图费")
    private String ndtFee;
    @ApiModelProperty(value = "肌电图费")
    @Excel(name = "肌电图费")
    private String jdtFee;
    @ApiModelProperty(value = "肺功能检查")
    @Excel(name = "肺功能检查")
    private String fgnFee;
    @ApiModelProperty(value = "心电图费")
    @Excel(name = "心电图费")
    private String xdtFee;
    @ApiModelProperty(value = "胃镜费")
    @Excel(name = "胃镜费")
    private String wjFee;
    @ApiModelProperty(value = "麻醉费")
    @Excel(name = "麻醉费")
    private String mzFee;
    @ApiModelProperty(value = "手术费")
    @Excel(name = "手术费")
    private String shFee;
    @ApiModelProperty(value = "其它费")
    @Excel(name = "其它费")
    private String otherFee;
    @ApiModelProperty(value = "CT费")
    @Excel(name = "CT费")
    private String ctFee;
    @ApiModelProperty(value = "康复费")
    @Excel(name = "康复费")
    private String kfFee;
    @ApiModelProperty(value = "急诊科检查费")
    @Excel(name = "急诊科检查费")
    private String jzkjcFee;
    @ApiModelProperty(value = "ICU检查费")
    @Excel(name = "ICU检查费")
    private String icujcFee;
    @ApiModelProperty(value = "内一科检查费")
    @Excel(name = "内一科检查费")
    private String nykjcFee;
    @ApiModelProperty(value = "内二科检查费")
    @Excel(name = "内二科检查费")
    private String nekjcFee;
    @ApiModelProperty(value = "体检科检查费")
    @Excel(name = "体检科检查费")
    private String tjkjcFee;
    @ApiModelProperty(value = "五官科检查费")
    @Excel(name = "五官科检查费")
    private String wgkjcFee;
    @ApiModelProperty(value = "妇产科检查费")
    @Excel(name = "妇产科检查费")
    private String fckjcFee;
    @ApiModelProperty(value = "外二科检查费")
    @Excel(name = "外二科检查费")
    private String wekjcFee;
    @ApiModelProperty(value = "感染科检查费")
    @Excel(name = "感染科检查费")
    private String grkjcFee;
    @ApiModelProperty(value = "氧气费")
    @Excel(name = "氧气费")
    private String yqFee;
    @ApiModelProperty(value = "血材料费")
    @Excel(name = "血材料费")
    private String xclFee;


}
