package com.zjry.pms.table.domain;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class PmsTableDocRes extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderedByDoctor;
    @ApiModelProperty(value = "医生")
    @Excel(name = "医生")
    private String docName;
    @ApiModelProperty(value = "合计")
    @Excel(name = "合计")
    private String total;
    @ApiModelProperty(value = "西药费")
    @Excel(name = "西药费")
    private String xyf;
    @ApiModelProperty(value = "中成药费")
    @Excel(name = "中成药费")
    private String zchyf;
    @ApiModelProperty(value = "中草药费")
    @Excel(name = "中草药费")
    private String zcyf;
    @ApiModelProperty(value = "挂号费")
    @Excel(name = "挂号费")
    private String ghf;
    @ApiModelProperty(value = "诊察费")
    @Excel(name = "诊察费")
    private String zcf;
    @ApiModelProperty(value = "检查费")
    @Excel(name = "检查费")
    private String jcf;
    @ApiModelProperty(value = "化验费")
    @Excel(name = "化验费")
    private String hyf;
    @ApiModelProperty(value = "常规类")
    @Excel(name = "常规类")
    private String cgl;
    @ApiModelProperty(value = "生化类")
    @Excel(name = "生化类")
    private String shl;
    @ApiModelProperty(value = "免疫类")
    @Excel(name = "免疫类")
    private String myl;
    @ApiModelProperty(value = "细菌类")
    @Excel(name = "细菌类")
    private String xjl;
    @ApiModelProperty(value = "电诊费")
    @Excel(name = "电诊费")
    private String dzf;
    @ApiModelProperty(value = "心电")
    @Excel(name = "心电")
    private String xd;
    @ApiModelProperty(value = "动态心电")
    @Excel(name = "动态心电")
    private String dtxd;
    @ApiModelProperty(value = "脑电")
    @Excel(name = "脑电")
    private String nd;
    @ApiModelProperty(value = "动态脑电")
    @Excel(name = "动态脑电")
    private String dtnd;
    @ApiModelProperty(value = "B超")
    @Excel(name = "B超")
    private String bc;
    @ApiModelProperty(value = "彩超")
    @Excel(name = "彩超")
    private String cc;
    @ApiModelProperty(value = "碎石")
    @Excel(name = "碎石")
    private String ss;
    @ApiModelProperty(value = "放射费")
    @Excel(name = "放射费")
    private String fsf;
    @ApiModelProperty(value = "透视费")
    @Excel(name = "透视费")
    private String tsf;
    @ApiModelProperty(value = "造影费")
    @Excel(name = "造影费")
    private String zyf;
    @ApiModelProperty(value = "拍片费")
    @Excel(name = "拍片费")
    private String ppf;
    @ApiModelProperty(value = "病理费")
    @Excel(name = "病理费")
    private String blf;
    @ApiModelProperty(value = "组织类")
    @Excel(name = "组织类")
    private String zzl;
    @ApiModelProperty(value = "宫颈活检")
    @Excel(name = "宫颈活检")
    private String gjhj;
    @ApiModelProperty(value = "细胞类")
    @Excel(name = "细胞类")
    private String zbl;
    @ApiModelProperty(value = "液基费")
    @Excel(name = "液基费")
    private String yjf;
    @ApiModelProperty(value = "CT费")
    @Excel(name = "CT费")
    private String ctf;
    @ApiModelProperty(value = "头部CT")
    @Excel(name = "头部CT")
    private String topbct;
    @ApiModelProperty(value = "体部CT")
    @Excel(name = "体部CT")
    private String tbct;
    @ApiModelProperty(value = "核磁费")
    @Excel(name = "核磁费")
    private String hcf;
    @ApiModelProperty(value = "头部核磁")
    @Excel(name = "头部核磁")
    private String topbhc;
    @ApiModelProperty(value = "体部核磁")
    @Excel(name = "体部核磁")
    private String tbhc;
    @ApiModelProperty(value = "镜检费")
    @Excel(name = "镜检费")
    private String jjf;
    @ApiModelProperty(value = "胃镜")
    @Excel(name = "胃镜")
    private String wj;
    @ApiModelProperty(value = "肠镜")
    @Excel(name = "肠镜")
    private String cj;
    @ApiModelProperty(value = "体检费")
    @Excel(name = "体检费")
    private String tjc;
    @ApiModelProperty(value = "治疗费")
    @Excel(name = "治疗费")
    private String zlf;
    @ApiModelProperty(value = "输氧费")
    @Excel(name = "输氧费")
    private String syf;
    @ApiModelProperty(value = "输血费")
    @Excel(name = "输血费")
    private String sxf;
    @ApiModelProperty(value = "换药处置费")
    @Excel(name = "换药处置费")
    private String hyczf;
    @ApiModelProperty(value = "静点费")
    @Excel(name = "静点费")
    private String jdf;
    @ApiModelProperty(value = "静注费")
    @Excel(name = "静注费")
    private String jzfee;
    @ApiModelProperty(value = "肌注费")
    @Excel(name = "肌注费")
    private String jzf;
    @ApiModelProperty(value = "采血费")
    @Excel(name = "采血费")
    private String cxf;
    @ApiModelProperty(value = "注射费")
    @Excel(name = "注射费")
    private String zsf;
    @ApiModelProperty(value = "透析费")
    @Excel(name = "透析费")
    private String txf;
    @ApiModelProperty(value = "高压氧")
    @Excel(name = "高压氧")
    private String gyf;
    @ApiModelProperty(value = "灌肠费")
    @Excel(name = "灌肠费")
    private String gcf;
    @ApiModelProperty(value = "导尿费")
    @Excel(name = "导尿费")
    private String ndf;
    @ApiModelProperty(value = "穿刺费")
    @Excel(name = "穿刺费")
    private String ccf;
    @ApiModelProperty(value = "理疗费")
    @Excel(name = "理疗费")
    private String llf;
    @ApiModelProperty(value = "针灸费")
    @Excel(name = "针灸费")
    private String zjf;
    @ApiModelProperty(value = "按摩费")
    @Excel(name = "按摩费")
    private String amf;
    @ApiModelProperty(value = "人流费")
    @Excel(name = "人流费")
    private String rlf;
    @ApiModelProperty(value = "上环费")
    @Excel(name = "上环费")
    private String shf;
    @ApiModelProperty(value = "取环费")
    @Excel(name = "取环费")
    private String qhf;
    @ApiModelProperty(value = "处置费")
    @Excel(name = "处置费")
    private String clf;
    @ApiModelProperty(value = "麻醉费")
    @Excel(name = "麻醉费")
    private String mzf;
    @ApiModelProperty(value = "手术费")
    @Excel(name = "手术费")
    private String ssf;
    @ApiModelProperty(value = "接生类")
    @Excel(name = "接生类")
    private String jsf;
    @ApiModelProperty(value = "护理费")
    @Excel(name = "护理费")
    private String hlf;
    @ApiModelProperty(value = "抢救观察")
    @Excel(name = "抢救观察")
    private String qjgc;
    @ApiModelProperty(value = "留观费")
    @Excel(name = "留观费")
    private String lgf;
    @ApiModelProperty(value = "床位费")
    @Excel(name = "床位费")
    private String cwf;
    @ApiModelProperty(value = "取暖费")
    @Excel(name = "取暖费")
    private String qnf;
    @ApiModelProperty(value = "垃圾费")
    @Excel(name = "垃圾费")
    private String ljf;
    @ApiModelProperty(value = "其他费")
    @Excel(name = "其他费")
    private String qtf;
    @ApiModelProperty(value = "卫材费")
    @Excel(name = "卫材费")
    private String wcf;
    @ApiModelProperty(value = "监护费")
    @Excel(name = "监护费")
    private String jhf;
    @ApiModelProperty(value = "会诊费")
    @Excel(name = "会诊费")
    private String hzf;
    @ApiModelProperty(value = "输注器")
    @Excel(name = "输注器")
    private String szq;
    @ApiModelProperty(value = "洗胃费")
    @Excel(name = "洗胃费")
    private String xwf;
    @ApiModelProperty(value = "试敏费")
    @Excel(name = "试敏费")
    private String smf;






}
