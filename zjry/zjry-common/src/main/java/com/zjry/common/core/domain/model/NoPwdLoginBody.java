package com.zjry.common.core.domain.model;

import java.io.Serializable;

import com.zjry.common.utils.StringUtils;
import com.zjry.common.utils.sign.Base64;

import cn.hutool.Hutool;

/**
 * 用户免密登录对象
 * @author yangtao
 */
public class NoPwdLoginBody implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 接入端标识
     */
    private String clientId;

    /**
     * 接入端秘钥
     */
    private String secret;

    /**
     * 登录用户名
     */
    private String username;

    public NoPwdLoginBody() {
    	
    }
    
    public NoPwdLoginBody(String paramBody) {
    	if(StringUtils.isNotEmpty(paramBody)) {
	    	String body = new String(Base64.decode(paramBody));
	    	if(StringUtils.isNotEmpty(body) && body.indexOf("&") != -1) {
	    		String[] params = body.split("&");
	    		if(params.length == 3) {
	    			this.clientId = params[0].split("=")[1];
	    			this.secret = params[1].split("=")[1];
	    			this.username = params[2].split("=")[1];
	    		}
	    	}
    	}
    }
    
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
