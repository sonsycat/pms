package com.zjry.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.system.domain.SysDeptRoom;

/**
 * 科室Service接口
 *
 * @author lf
 * @date 2021-06-18
 */
public interface ISysDeptRoomService extends IService<SysDeptRoom> {

    /**
     * 查询科室列表
     *
     * @param sysDeptRoom 科室
     * @return 科室集合
     */
    public List<SysDeptRoom> selectSysDeptRoomList(SysDeptRoom sysDeptRoom);

    /**
     * 通过机构id查询科室数据
     *
     * @param deptId 机构id
     * @return 结果
     */
    List<SysDeptRoom> getDeptRoomListByDeptId(Long deptId);
}
