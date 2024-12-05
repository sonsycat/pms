package com.zjry.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.system.domain.SysFlowCode;

/**
 * 流水号Service接口
 *
 * @author zjry
 * @date 2021-06-22
 */
public interface ISysFlowCodeService extends IService<SysFlowCode> {

    /**
     * TODO
     * @param code 流水号编码
     * @param prefix 前缀
     * @param len 流水号长度
     * @return 流水号
     */
    public String getEntityDateFormatFlowCode(String code, String prefix, long len);

    /**
     * TODO
     * @param code 流水号编码
     * @param prefix 前缀
     * @param len 流水号长度
     * @return 流水号
     */
    public String getEntityFlowCode(String code, String prefix, long len);

    /**
     * TODO
     * @param code1 流水号编码
     * @param code2 流水号编码
     * @param len 流水号长度
     * @return 流水号
     */
    public String getEntityFlowCodeSc(String code1, String code2, long len);

    /**
     * TODO
     * @param code 流水号编码
     * @param prefix 前缀
     * @param len 流水号长度
     * @return 流水号
     */
    public String getEntityFlowCodeNoCode(String code, String prefix, long len);
}
