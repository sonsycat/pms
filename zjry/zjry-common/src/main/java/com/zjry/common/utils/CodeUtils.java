package com.zjry.common.utils;

import cn.hutool.core.collection.CollectionUtil;

import java.util.List;

/**
 * @Author: mingc
 * @DateTime: 2021/6/18
 */
public class CodeUtils {

    /**
     * 生成业务编码
     *
     * @param prefix    前缀
     * @param length    除去前缀，编码长度
     * @param codeList  已维护编码集合
     * @return
     */
    public static String generateBusiCode(String prefix, int length, List<String> codeList) {
        String resCode;
        if (CollectionUtil.isEmpty(codeList)) {
            resCode = String.format("%0" + length + "d", 1);
            return prefix + resCode;
        } else {
            String maxCode = codeList.stream().max(String::compareTo).get();
            int code = Integer.parseInt(maxCode.substring(prefix.length())) + 1;
            resCode = String.format("%0" + length + "d", code);
            return prefix + resCode;
        }
    }
}
