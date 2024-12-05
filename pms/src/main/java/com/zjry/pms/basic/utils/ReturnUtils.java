package com.zjry.pms.basic.utils;

import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.basic.constant.Constants;

public final class ReturnUtils {

    /**
     * 返回信息
     *
     * @param temp
     * @param msg
     * @return
     */
    public static AjaxResult returnMsg(int temp, String msg) {
        AjaxResult ajax;
        if (temp == Constants.DOWNTIME) {
            ajax = AjaxResult.success(msg + "成功，消息服务器已宕机！");
        } else if (temp > 0) {
            ajax = AjaxResult.success(msg + "成功！");
        } else {
            ajax = AjaxResult.error(msg + "失败！");
        }
        return ajax;
    }
}
