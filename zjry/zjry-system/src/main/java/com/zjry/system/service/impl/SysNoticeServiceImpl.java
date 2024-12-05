package com.zjry.system.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjry.common.utils.SecurityUtils;
import com.zjry.system.domain.SysNotice;
import com.zjry.system.mapper.SysNoticeMapper;
import com.zjry.system.service.ISysConfigService;
import com.zjry.system.service.ISysNoticeService;

/**
 * 公告 服务层实现
 * 
 * @author zjry
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService
{
    @Autowired
    private SysNoticeMapper noticeMapper;
    @Autowired
    private SysMessageWS sysMessageWS;
    @Autowired
    private ISysConfigService sysConfigService;

    /**
     * 查询公告信息
     * 
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId)
    {
        return noticeMapper.selectNoticeById(noticeId);
    }

    /**
     * 查询公告列表
     * 
     * @param notice 公告信息
     * @return 公告集合
     */
    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice)
    {
        return noticeMapper.selectNoticeList(notice);
    }

    /**
     * 新增公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertNotice(SysNotice notice)
    {
    	int flag = noticeMapper.insertNotice(notice);
    	String wsEnabled = sysConfigService.selectConfigByKey("websocket.enabled");
    	if(StringUtils.isNotEmpty(wsEnabled) && "1".equals(wsEnabled)) {
    		sysMessageWS.sendMsgContent(notice.getNoticeTitle(), 
    			notice.getNoticeContent(), SecurityUtils.getLoginUser().getUser().getUserId(),
    			new Long[] {1L}, "", notice.getNoticeId());
    	}
    	return flag;
    }

    /**
     * 修改公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int updateNotice(SysNotice notice)
    {
        return noticeMapper.updateNotice(notice);
    }

    /**
     * 删除公告对象
     * 
     * @param noticeId 公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeById(Long noticeId)
    {
        return noticeMapper.deleteNoticeById(noticeId);
    }

    /**
     * 批量删除公告信息
     * 
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(Long[] noticeIds)
    {
        return noticeMapper.deleteNoticeByIds(noticeIds);
    }
}
