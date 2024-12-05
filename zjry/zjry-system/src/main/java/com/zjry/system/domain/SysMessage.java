package com.zjry.system.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 消息管理对象 sys_message
 *
 * @author yangtao
 * @date 2021-11-03
 */
@ApiModel(value = "SysMessage", description = "消息管理对象 sys_message")
public class SysMessage extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ApiModelProperty(value = "主键")
    private Long id;

    /** 消息标题 */
    @Excel(name = "消息标题")
    @ApiModelProperty(value = "消息标题")
    private String title;

    /** 消息内容 */
    @Excel(name = "消息内容")
    @ApiModelProperty(value = "消息内容")
    private String content;

    /** 消息访问路径 */
    @ApiModelProperty(value = "消息访问路径")
    private String msgUrl;

    /** 接收用户id */
    @ApiModelProperty(value = "接收用户id")
    private Long receiveUserId;
    private String receiveUserName;

    /** 发送用户id */
    @ApiModelProperty(value = "发送用户id")
    private Long sendUserId;
    private String sendUserName;
    private Long busiId;

    /** 发送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发送时间")
    private Date sendTime;

    /** 是否已读：1-已读,0-未读 */
    @Excel(name = "是否已读：1-已读,0-未读")
    @ApiModelProperty(value = "是否已读：1-已读,0-未读")
    private String isRead;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private String delFlag;

    private Integer notReadMsgNum;
    private String msgType;
    
    public Integer getNotReadMsgNum() {
		return notReadMsgNum;
	}

	public void setNotReadMsgNum(Integer notReadMsgNum) {
		this.notReadMsgNum = notReadMsgNum;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
    public void setMsgUrl(String msgUrl) {
        this.msgUrl = msgUrl;
    }

    public String getMsgUrl() {
        return msgUrl;
    }
    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public Long getReceiveUserId() {
        return receiveUserId;
    }
    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Long getSendUserId() {
        return sendUserId;
    }
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getSendTime() {
        return sendTime;
    }
    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getIsRead() {
        return isRead;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }
    
    public String getReceiveUserName() {
		return receiveUserName;
	}

	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public Long getBusiId() {
		return busiId;
	}

	public void setBusiId(Long busiId) {
		this.busiId = busiId;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("msgUrl", getMsgUrl())
            .append("receiveUserId", getReceiveUserId())
            .append("sendUserId", getSendUserId())
            .append("sendTime", getSendTime())
            .append("isRead", getIsRead())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
	}
}
