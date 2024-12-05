package com.zjry.formDesign.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class ResponseBody {
	
    public Integer code = 200;
    public String msg = "成功";
    public Object data;
    
    public ResponseBody() {
    	
    }
    
    public ResponseBody(String result) {
    	JSONObject obj = JSONObject.parseObject(result);
    	this.code = obj.getInteger("code");
    	this.msg = obj.getString("msg");
    	this.data = obj.getJSONObject("data");
    }
    
    /***
     * object 源实体对象
     * cl   目标实体class
     * */
    public void setDataChange(Object object, Class cl) {
        this.setData(JSON.parseObject(JSON.toJSONString(object), cl));
    }

    public static ResponseBody response(Integer code ,String msg){
    	ResponseBody responseEntity = new ResponseBody();
        responseEntity.setCode(code);
        responseEntity.setMsg(msg);
        return responseEntity;
    }

    public static ResponseBody ok() {
        return new ResponseBody();
    }

    public static ResponseBody ok(Object data) {
    	ResponseBody responseBody = new ResponseBody();
    	responseBody.setData(data);
        return responseBody;
    }
    
}
