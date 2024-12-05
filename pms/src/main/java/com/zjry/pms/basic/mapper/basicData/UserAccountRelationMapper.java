package com.zjry.pms.basic.mapper.basicData;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.basicData.UserAccountRelation;

import java.util.List;

/**
 * 人员账套对应关系Mapper接口
 * 
 * @author cyq
 * @date 2021-07-22
 */
public interface UserAccountRelationMapper extends BaseMapper<UserAccountRelation> {
    /**
     * 查询人员账套对应关系列表
     * 
     * @param userAccountRelation 人员账套对应关系
     * @return 人员账套对应关系集合
     */
    public List<UserAccountRelation> selectUserAccountRelationList(UserAccountRelation userAccountRelation);
    /**
     * 修改人员账套对应关系use_flag
     * @param userAccountRelation
     * @return
     */
    public int updateUserAccountRelationUseFlag(UserAccountRelation userAccountRelation);
}
