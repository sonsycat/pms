package com.zjry.pms.basic.mapper.basicData;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.basicData.AccountSet;

import java.util.List;


/**
 * 账套信息Mapper接口
 * 
 * @author 王超
 * @date 2021-07-15
 */
public interface AccountSetMapper extends BaseMapper<AccountSet> {
    /**
     * 查询账套信息
     * 
     * @param id 账套信息ID
     * @return 账套信息
     */
    public AccountSet selectAccountSetById(Long id);
    /**
     * 通过账套id获取账套
     * @param ids
     * @return
     */
    public List<AccountSet> selectAccountSetByIds(Long[] ids);
}
