package com.zjry.pms.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.process.domain.HrProcessModel;
import com.zjry.pms.process.service.IHrProcessModelService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mk
 * @title: BusinessProcessUtils
 * @projectName zjry-spd
 * @date 2021/12/910:24
 */
public class BusinessProcessUtils {
    @Autowired
    private static IHrProcessModelService hrProcessModelService = SpringContextHolder.getBean(IHrProcessModelService.class);

    public static  HashMap getProcessId(String tableName, String businessName ,String type){
        HrProcessModel hrProcessModel = new HrProcessModel();
        HashMap<String,String> msg = new HashMap<String,String>();
        hrProcessModel.setTabelName(tableName);
        if(StringUtils.isNotEmpty(type)){
            hrProcessModel.setType(type);
        }
        List<HrProcessModel> hrProcessModelList = hrProcessModelService.selectHrProcessModelList(hrProcessModel);
        if (hrProcessModelList.size() > 0) {
            //获取流程的访问地址
            String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
            String proclnsId = "";
            Map<String, String> params = new HashMap<String, String>(16);
            params = ProcessUtils.startProcess(hrProcessModelList.get(0).getProcinsId(), "tableName", 0, businessName, hrProcessModelList.get(0).getFormUrl());
            String result = "";
            try {
                //启动流程获取流程的ID，proclnsId
                result = HttpConnectionUtil.post(processUrl + "/api/wf/startProcess", params);
            } catch (Exception e) {
                msg.put("error","请求接口异常！");
                return msg;
            }
            if (StringUtils.isNotEmpty(result)) {
                JSONObject json = JSONObject.parseObject(result);
                Map<String, String> map = (Map<String, String>) json.get("data");
                if (StringUtils.isNotEmpty(map)) {
                    proclnsId = map.get("procInsId");
                    //将流程的示例ID写到业务表中
                }
            }
            if (StringUtils.isEmpty(proclnsId)) {
                msg.put("error","未获取到配置流程ID！");
                return msg;
            } else {
                msg.put("success",proclnsId);
                return msg;
            }
        } else {
            msg.put("error","流程未匹配！");
            return msg;
        }
    }

    public static void setbusinessId(String procedureId, Long id, String code, String businessName )throws Exception {
        //获取流程的访问地
        String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
        Map<String, String> params = new HashMap<String, String>(16);
        params = ProcessUtils.startProcess(procedureId, id, code + businessName);
            //启动流程获取流程的ID，proclnsId
            //启动流程获取流程的ID，proclnsId
            HttpConnectionUtil.post(processUrl + "/api/wf/addBusinessData", params);

    }


}
