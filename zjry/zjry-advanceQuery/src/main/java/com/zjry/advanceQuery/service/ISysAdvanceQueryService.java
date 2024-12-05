package com.zjry.advanceQuery.service;

import java.util.List;
import java.util.Map;

import com.zjry.advanceQuery.vo.AdvanceQueryVo;

/**
 * 高级查询Service接口
 * @author yangtao
 * @date 2021-10-13
 */
public interface ISysAdvanceQueryService {

    List<Map> list(AdvanceQueryVo advanceQueryVo);

}
