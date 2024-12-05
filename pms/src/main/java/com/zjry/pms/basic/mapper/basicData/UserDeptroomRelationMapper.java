package com.zjry.pms.basic.mapper.basicData;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.basicData.UserDeptroomRelation;

import java.util.List;

/**
 * 用户科室对应关系Mapper接口
 * 
 * @author 王超
 * @date 2021-07-23
 */
public interface UserDeptroomRelationMapper extends BaseMapper<UserDeptroomRelation> {


    /**
     * 查询用户科室对应关系（roleId and userId）
     * @param userDeptroomRelation
     * @return
     */
    public List<UserDeptroomRelation> selectUserDeptroomRelationByUserIdAndRoleId(UserDeptroomRelation userDeptroomRelation);

}
