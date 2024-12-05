package com.zjry.pms.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.core.redis.RedisCache;
import com.zjry.pms.post.domain.HrPostManage;
import com.zjry.pms.post.domain.HrPostManageVo;
import com.zjry.pms.post.mapper.HrPostManageMapper;
import com.zjry.pms.post.service.IHrPostManageService;
import com.zjry.pms.common.Constants;
import com.zjry.pms.encode.service.IHrEncodeRuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 岗位管理Service业务层处理
 * 
 * @author mk
 * @date 2021-07-23
 */
@Service
public class HrPostManageServiceImpl extends ServiceImpl<HrPostManageMapper, HrPostManage> implements IHrPostManageService {
    @Autowired
    private HrPostManageMapper hrPostManageMapper;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;
    @Resource
    private RedisCache redisCache;

    /**
     * 查询岗位管理
     * 
     * @param id 岗位管理ID
     * @return 岗位管理
     */
    @Override
    public HrPostManage selectHrPostManageById(Long id) {
        return hrPostManageMapper.selectHrPostManageById(id);
    }

    /**
     * 查询岗位管理列表
     * 
     * @param hrPostManage 岗位管理
     * @return 岗位管理
     */
    @Override
    public List<HrPostManage> selectHrPostManageList(HrPostManage hrPostManage) {
        return hrPostManageMapper.selectHrPostManageList(hrPostManage);
    }

    /**
     * 新增岗位管理
     * 
     * @param hrPostManage 岗位管理
     * @return 结果
     */
    @Override
    public int insertHrPostManage(HrPostManage hrPostManage) {
        hrPostManage.preInsert();
        hrPostManage.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        hrPostManage.setPostCode(hrEncodeRuleService.generateEncode("hr_post_manage","POST_CODE"));
        //删除缓存
        redisCache.deleteObject(Constants.POST_REDIS_KEY);
        return hrPostManageMapper.insertHrPostManage(hrPostManage);
    }

    /**
     * 修改岗位管理
     * 
     * @param hrPostManage 岗位管理
     * @return 结果
     */
    @Override
    public int updateHrPostManage(HrPostManage hrPostManage) {
        hrPostManage.preUpdate();
        //删除缓存
        redisCache.deleteObject(Constants.POST_REDIS_KEY);
        return hrPostManageMapper.updateHrPostManage(hrPostManage);
    }

    /**
     * 批量删除岗位管理
     * 
     * @param ids 需要删除的岗位管理ID
     * @return 结果
     */
    @Override
    public int deleteHrPostManageByIds(Long[] ids) {
        return hrPostManageMapper.deleteHrPostManageByIds(ids);
    }

    /**
     * 删除岗位管理信息
     * 
     * @param id 岗位管理ID
     * @return 结果
     */
    @Override
    public int deleteHrPostManageById(Long id) {
        return hrPostManageMapper.deleteHrPostManageById(id);
    }

    /**
     * 查询所有岗位数据
     * @return 结果
     */
    @Override
    public List<HrPostManageVo> queryAllPostInfo() {
        List<HrPostManageVo> hrPostManageVos = redisCache.getCacheList(Constants.POST_REDIS_KEY);
        if (hrPostManageVos.size()==0){
            hrPostManageVos = hrPostManageMapper.queryAllPostInfo();
            if (hrPostManageVos.size()==0){
                return null;
            }
            redisCache.setCacheList(Constants.POST_REDIS_KEY, hrPostManageVos);
        }
        return hrPostManageVos;
    }
}
