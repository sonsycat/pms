package com.zjry.system.service.impl;

import java.util.List;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.system.mapper.SysDeptRoomMapper;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.system.domain.SysDeptRoom;
import com.zjry.system.service.ISysDeptRoomService;

/**
 * 科室Service业务层处理
 *
 * @author lf
 * @date 2021-06-18
 */
@Service
public class SysDeptRoomServiceImpl extends ServiceImpl<SysDeptRoomMapper, SysDeptRoom> implements ISysDeptRoomService {
    @Autowired
    private SysDeptRoomMapper sysDeptRoomMapper;

    /**
     * 查询科室列表
     *
     * @param sysDeptRoom 科室
     * @return 科室
     */
    @Override
    public List<SysDeptRoom> selectSysDeptRoomList(SysDeptRoom sysDeptRoom) {
        LambdaQueryWrapper<SysDeptRoom> queryWrapper = new LambdaQueryWrapper();
        queryWrapper
                .like(StrUtil.isNotBlank(sysDeptRoom.getDeptRoomName()), SysDeptRoom::getDeptName, sysDeptRoom.getDeptRoomName())
                .eq(StrUtil.isNotBlank(sysDeptRoom.getDeptRoomCode()), SysDeptRoom::getDeptRoomCode, sysDeptRoom.getDeptRoomCode())
                .eq(sysDeptRoom.getDeptId() != null, SysDeptRoom::getDeptId, sysDeptRoom.getDeptId())
                .like(StrUtil.isNotBlank(sysDeptRoom.getDepAddress()), SysDeptRoom::getDepAddress, sysDeptRoom.getDepAddress())
                .eq(StrUtil.isNotBlank(sysDeptRoom.getStatus()), SysDeptRoom::getStatus, sysDeptRoom.getStatus())
                .like(StrUtil.isNotBlank(sysDeptRoom.getRemark()), SysDeptRoom::getRemark, sysDeptRoom.getRemark())
                .eq(StrUtil.isNotBlank(sysDeptRoom.getVersion()), SysDeptRoom::getVersion, sysDeptRoom.getVersion())
                .eq(StrUtil.isNotBlank(sysDeptRoom.getPym()), SysDeptRoom::getPym, sysDeptRoom.getPym());
        return this.list(queryWrapper);
    }

    /**
     * 通过机构id查询科室数据
     *
     * @param deptId 机构id
     * @return 结果
     */
    @Override
    public List<SysDeptRoom> getDeptRoomListByDeptId(Long deptId) {
        LambdaQueryWrapper<SysDeptRoom> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysDeptRoom::getDeptId, deptId);
        return this.list(queryWrapper);
    }
}
