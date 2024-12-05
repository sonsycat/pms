package com.zjry.pms.basic.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mk
 * @title: UserRole
 * @projectName zjry-cost
 * @date 2022/6/1016:06
 */
@Data
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private Long roleId;
    private String delFlag;
    private String userFlag;
}
