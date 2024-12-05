package com.zjry.pms.table.domain;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class PmsTableCostRes extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String orderedByDoctor;
    @ApiModelProperty(value = "科室名称")
    @Excel(name = "科室名称")
    private String deptName;
    @ApiModelProperty(value = "合计")
    @Excel(name = "合计")
    private String total;
    @ApiModelProperty(value = "人力成本")
    @Excel(name = "人力成本")
    private String rlcb;
    @ApiModelProperty(value = "卫生材料")
    @Excel(name = "卫生材料")
    private String wscl;
    @ApiModelProperty(value = "手术室材料")
    @Excel(name = "手术室材料")
    private String ssscl;
    @ApiModelProperty(value = "消毒材料费")
    @Excel(name = "消毒材料费")
    private String xdclf;
    @ApiModelProperty(value = "低值易耗")
    @Excel(name = "低值易耗")
    private String dzyh;
    @ApiModelProperty(value = "西药费")
    @Excel(name = "西药费")
    private String xyf;
    @ApiModelProperty(value = "中成药")
    @Excel(name = "中成药")
    private String zcf;
    @ApiModelProperty(value = "中草药")
    @Excel(name = "中草药")
    private String zyf;
    @ApiModelProperty(value = "折旧费")
    @Excel(name = "折旧费")
    private String zjf;
    @ApiModelProperty(value = "房屋折旧费")
    @Excel(name = "房屋折旧费")
    private String fwzjf;
    @ApiModelProperty(value = "资产处置费用")
    @Excel(name = "资产处置费用")
    private String zcczf;
    @ApiModelProperty(value = "医疗风险基金")
    @Excel(name = "医疗风险基金")
    private String ylfxjj;
    @ApiModelProperty(value = "医疗设备贷款利息")
    @Excel(name = "医疗设备贷款利息")
    private String ylsbdk;
    @ApiModelProperty(value = "维修(护)费")
    @Excel(name = "维修(护)费")
    private String wxhf;
    @ApiModelProperty(value = "医疗设备考察费")
    @Excel(name = "医疗设备考察费")
    private String ylsbkcf;
    @ApiModelProperty(value = "差旅费")
    @Excel(name = "差旅费")
    private String clf;
    @ApiModelProperty(value = "被服洗涤费")
    @Excel(name = "被服洗涤费")
    private String bfxdf;
    @ApiModelProperty(value = "水费")
    @Excel(name = "水费")
    private String sf;
    @ApiModelProperty(value = "电梯使用费")
    @Excel(name = "电梯使用费")
    private String dtsyf;
    @ApiModelProperty(value = "电费")
    @Excel(name = "电费")
    private String df;
    @ApiModelProperty(value = "设备修理费")
    @Excel(name = "设备修理费")
    private String sbxlf;
    @ApiModelProperty(value = "邮电费")
    @Excel(name = "邮电费")
    private String ydf;
    @ApiModelProperty(value = "医杂费")
    @Excel(name = "医杂费")
    private String yzf;
    @ApiModelProperty(value = "印刷费")
    @Excel(name = "印刷费")
    private String ysf;
    @ApiModelProperty(value = "保洁费")
    @Excel(name = "保洁费")
    private String bjf;
    @ApiModelProperty(value = "医用垃圾处理费")
    @Excel(name = "医用垃圾处理费")
    private String yyljclf;
    @ApiModelProperty(value = "科研课题费")
    @Excel(name = "科研课题费")
    private String kykqf;
    @ApiModelProperty(value = "氧气费")
    @Excel(name = "氧气费")
    private String yqf;
    @ApiModelProperty(value = "管理费")
    @Excel(name = "管理费")
    private String glf;
    @ApiModelProperty(value = "其它支出")
    @Excel(name = "其它支出")
    private String qtzc;
    @ApiModelProperty(value = "取暖费")
    @Excel(name = "取暖费")
    private String qnf;
    @ApiModelProperty(value = "污水处理费")
    @Excel(name = "污水处理费")
    private String wsclf;
    @ApiModelProperty(value = "医疗设备修理费")
    @Excel(name = "医疗设备修理费")
    private String ylsbxlf;
    @ApiModelProperty(value = "仪器配件费")
    @Excel(name = "仪器配件费")
    private String yqpjf;
    @ApiModelProperty(value = "器械费")
    @Excel(name = "器械费")
    private String qxf;
    @ApiModelProperty(value = "办公费")
    @Excel(name = "办公费")
    private String bgf;
    @ApiModelProperty(value = "其他交通费")
    @Excel(name = "其他交通费")
    private String qtjtf;
    @ApiModelProperty(value = "培训费")
    @Excel(name = "培训费")
    private String pxf;
    @ApiModelProperty(value = "会议接待费")
    @Excel(name = "会议接待费")
    private String hyjdf;
    @ApiModelProperty(value = "劳务费")
    @Excel(name = "劳务费")
    private String lwf;
    @ApiModelProperty(value = "福利费")
    @Excel(name = "福利费")
    private String flf;
    @ApiModelProperty(value = "其他材料")
    @Excel(name = "其他材料")
    private String qtcl;
    @ApiModelProperty(value = "工会经费")
    @Excel(name = "工会经费")
    private String ghjf;
    @ApiModelProperty(value = "维修费")
    @Excel(name = "维修费")
    private String wxf;
    @ApiModelProperty(value = "银行手续费")
    @Excel(name = "银行手续费")
    private String yhsxf;
    @ApiModelProperty(value = "租赁费")
    @Excel(name = "租赁费")
    private String zlf;
    @ApiModelProperty(value = "其他一般商品服务支出")
    @Excel(name = "其他一般商品服务支出")
    private String qtspfw;
    @ApiModelProperty(value = "会务费")
    @Excel(name = "会务费")
    private String hwf;
    @ApiModelProperty(value = "宣传品")
    @Excel(name = "宣传品")
    private String xcp;
    @ApiModelProperty(value = "物业管理费")
    @Excel(name = "物业管理费")
    private String wyglf;
    @ApiModelProperty(value = "利息费用")
    @Excel(name = "利息费用")
    private String lxf;
    @ApiModelProperty(value = "会议费")
    @Excel(name = "会议费")
    private String hyf;
    @ApiModelProperty(value = "委托业务费")
    @Excel(name = "委托业务费")
    private String wtywf;
    @ApiModelProperty(value = "其他材料费用")
    @Excel(name = "其他材料费用")
    private String qtclf;
    @ApiModelProperty(value = "汽油费")
    @Excel(name = "汽油费")
    private String qyf;
    @ApiModelProperty(value = "无形资产摊销")
    @Excel(name = "无形资产摊销")
    private String wxzctx;
    @ApiModelProperty(value = "被服购置费")
    @Excel(name = "被服购置费")
    private String bfgzf;
    @ApiModelProperty(value = "电脑设备、耗材购置费")
    @Excel(name = "电脑设备、耗材购置费")
    private String dnhcf;
    @ApiModelProperty(value = "医用表格费")
    @Excel(name = "医用表格费")
    private String yybgf;
    @ApiModelProperty(value = "总务材料")
    @Excel(name = "总务材料")
    private String zwcl;
    @ApiModelProperty(value = "血费")
    @Excel(name = "血费")
    private String xf;







}
