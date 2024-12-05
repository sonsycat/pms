package com.zjry.pms.basic.mapper.basicData;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.basicData.BasicdataDeptRoom;
import com.zjry.pms.basic.domain.basicData.BasicdataDeptRoomVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科室Mapper接口
 * 
 * @author cyq
 * @date 2021-07-16
 */
public interface BasicdataDeptRoomMapper extends BaseMapper<BasicdataDeptRoom> {
    /**
     * 查询科室
     * 
     * @param deptRoomId 科室ID
     * @return 科室
     */
    public BasicdataDeptRoom selectBasicdataDeptRoomById(Long deptRoomId);

    /**
     * 查询科室列表
     * 
     * @param basicdataDeptRoom 科室
     * @return 科室集合
     */
    public List<BasicdataDeptRoom> selectBasicdataDeptRoomList(BasicdataDeptRoom basicdataDeptRoom);

    /**
     * 获取科室下列表 【通知公告】
     * @param basicdataDeptRoom
     * @return
     */
    public List<BasicdataDeptRoom> selectBasicdataDeptRoomListForNotice(BasicdataDeptRoom basicdataDeptRoom);

    /**
     * 新增科室
     * 
     * @param basicdataDeptRoom 科室
     * @return 结果
     */
    public int insertBasicdataDeptRoom(BasicdataDeptRoom basicdataDeptRoom);

    /**
     * 修改科室
     * 
     * @param basicdataDeptRoom 科室
     * @return 结果
     */
    public int updateBasicdataDeptRoom(BasicdataDeptRoom basicdataDeptRoom);

    /**
     * 删除科室
     * 
     * @param deptRoomId 科室ID
     * @return 结果
     */
    public int deleteBasicdataDeptRoomById(Long deptRoomId);

    /**
     * 批量删除科室
     * 
     * @param deptRoomIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBasicdataDeptRoomByIds(Long[] deptRoomIds);

    /**
     * 查询科室数量
     *
     * @param basicdataDeptRoom 科室信息
     * @return 科室数量
     */
    int getSize(BasicdataDeptRoom basicdataDeptRoom);

    /**
     * 查询科室树
     *
     * @param basicdataDeptRoom 科室信息
     * @return 科室树
     */
    List<BasicdataDeptRoomVo> selectDeptRoomList(BasicdataDeptRoomVo basicdataDeptRoom);

    /**
     * 查询根节点科室
     * @return
     */
    List<Long> selectRootParentId();
    /**
     * 校验科室名称是否唯一
     *
     * @param basicdataDeptRoom 科室信息
     * @return 结果
     */
    BasicdataDeptRoom checkDeptRoomNameUnique(BasicdataDeptRoom basicdataDeptRoom);

    /**
     * 返回科室列表
     *
     * @param deptId 单位ID
      * @param deptRoomIds 科室IDs
     * @return 结果
     */
    List<BasicdataDeptRoom> selectDeptRoomListByIds(@Param("deptId") Long deptId, @Param("deptRoomIds") List<Long> deptRoomIds);
    /**
     * 存在下级科室判断
     *
     * @param deptRoomId 科室ID
     * @return 结果
     */
    int hasChildBydeptRoomId(Long deptRoomId);
    /**
     * 查询是否存在用户
     *
     * @param deptRoomId 科室ID
     * @return 结果
     */
    int checkDeptExistUser(Long deptRoomId);

}
