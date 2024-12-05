package com.zjry.pms.post.domain;

import com.zjry.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author mk
 * @title: HrPostManageVo
 * @projectName zjrj-hrm
 * @date 2021/7/2317:04
 */
public class HrPostManageVo {
    private static final long serialVersionUID = 1L;
    /** 岗位编码 */
    @Excel(name = "岗位编码")
    @ApiModelProperty(value = "岗位编码")
    private String postCode;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    @ApiModelProperty(value = "岗位名称")
    private String postName;

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
}
