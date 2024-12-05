package com.zjry.pms.apport.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 分摊计算对象 cost_apport
 *
 * @author yzh
 * @date 2022-05-17
 */
@Data
@ApiModel(value = "CostApport", description = "分摊计算对象 cost_apport")
public class CostApport extends BaseEntity implements Serializable {
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

    /** 分摊编码 */
    @Excel(name = "分摊编码")
    @ApiModelProperty(value = "分摊编码")
    private String apportCode;

    /** 年月 */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "年月", width = 30, dateFormat = "yyyy-MM")
    @ApiModelProperty(value = "年月")
    private Date nowDate;
    
    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "开始时间 ", width = 30, dateFormat = "yyyy-MM")
    @ApiModelProperty(value = "开始时间 ")
    private Date startDate;
    
    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM")
    @ApiModelProperty(value = "结束时间")
    private Date endDate;

    /** 分摊级别(保存最后的级别) */
    @Excel(name = "分摊级别(保存最后的级别)")
    @ApiModelProperty(value = "分摊级别(保存最后的级别)")
    private String apportType;

    /** 分摊前金额 */
    @Excel(name = "分摊前金额")
    @ApiModelProperty(value = "分摊前金额")
    private Double forward;

    /** 分摊后金额 */
    @Excel(name = "分摊后金额")
    @ApiModelProperty(value = "分摊后金额")
    private Double back;

    /** 分摊时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "分摊时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "分摊时间")
    private Date apportTime;
    
    private List<Map<String, Object>> tableColumns1 = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> tableData1 = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> tableData2 = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> tableData3 = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> tableData4 = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> tableData5 = new ArrayList<Map<String, Object>>();
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setCompCode(Long compCode) {
        this.compCode = compCode;
    }

    public Long getCompCode() {
        return compCode;
    }
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountCode() {
        return accountCode;
    }
    public void setApportCode(String apportCode) {
        this.apportCode = apportCode;
    }

    public String getApportCode() {
        return apportCode;
    }
    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }

    public Date getNowDate() {
        return nowDate;
    }
    public void setApportType(String apportType) {
        this.apportType = apportType;
    }

    public String getApportType() {
        return apportType;
    }
    
    public void setApportTime(Date apportTime) {
        this.apportTime = apportTime;
    }

    public Date getApportTime() {
        return apportTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("apportCode", getApportCode())
            .append("nowDate", getNowDate())
            .append("apportType", getApportType())
            .append("forward", getForward())
            .append("back", getBack())
            .append("apportTime", getApportTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
