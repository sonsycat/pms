/**
 * 
 */
package com.zjry.system.domain;

import com.zjry.common.core.domain.BaseEntity;

/**
 * @author yangtao
 * 第三方接入端
 */
public class SysClient extends BaseEntity {

	private String clientId;
	private String clientSecret;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
}
