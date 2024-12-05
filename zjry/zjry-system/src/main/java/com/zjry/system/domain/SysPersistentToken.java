package com.zjry.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zjry.common.core.domain.BaseMybatisPlusEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: mingc
 * @DateTime: 2021/7/15
 */
@Data
@ApiModel(value = "SysPersistentToken", description = "自定义查询对象 sys_persistent_token")
public class SysPersistentToken implements Serializable {

    @TableId(type = IdType.INPUT)
    private String series;

    @NotNull
    @TableField("token_value")
    private String tokenValue;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("token_date")
    private LocalDateTime tokenDate;

    /**
     * an IPV6 address max length is 39 characters
     */
    @Size(max = 39)
    @TableField("ip_address")
    private String ipAddress;

    @TableField("user_agent")
    private String userAgent;

    @JsonIgnore
    @TableField("user_id")
    private String userId;

    private String username;

    /**
     * 登录地址
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

}
