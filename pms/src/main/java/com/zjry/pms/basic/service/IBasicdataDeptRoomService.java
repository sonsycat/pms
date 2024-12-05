package com.zjry.pms.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.basic.domain.basicData.BasicdataDeptRoom;
import com.zjry.pms.basic.domain.basicData.BasicdataDeptRoomVo;
import com.zjry.pms.basic.domain.basicData.OrganTree;
import com.zjry.pms.basic.domain.basicData.Tree;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 科室Service接口
 * 
 * @author cyq
 * @date 2021-07-16
 */
public interface IBasicdataDeptRoomService extends IService<BasicdataDeptRoom> {
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
     * 批量删除科室
     *
     * @param deptRoomIds 需要删除的科室ID
     * @return 结果
     */
    public int deleteBasicdataDeptRoomByIds(Long[] deptRoomIds);

    /**
     * 删除科室信息
     *
     * @param deptRoomId 科室ID
     * @return 结果
     */
    public int deleteBasicdataDeptRoomById(Long deptRoomId);

    /**
     * 查询科室树
     *
     * @param basicdataDeptRoom 科室信息
     * @return 科室树
     */
    List<BasicdataDeptRoomVo> selectDeptRoomList(BasicdataDeptRoomVo basicdataDeptRoom);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 科室列表
     * @return 下拉树结构列表
     */
    List<Tree> buildDeptRoomTree(List<BasicdataDeptRoomVo> depts);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param deptRooms 科室列表
     * @return 下拉树结构列表
     */
    List<OrganTree> buildDeptRoomOrganTree(List<BasicdataDeptRoomVo> deptRooms);
    /**
     * 校验科室名称是否唯一
     *
     * @param basicdataDeptRoom 科室信息
     * @return 结果
     */
    String checkDeptRoomNameUnique(BasicdataDeptRoom basicdataDeptRoom);
    /**
     * 存在下级科室判断
     *
     * @param deptRoomId 科室ID
     * @return 结果
     */
    boolean hasChildByDeptRoomId(Long deptRoomId);

    /**
     * 查询是否存在用户
     *
     * @param deptRoomId 科室ID
     * @return 结果
     */
    boolean checkExistUser(Long deptRoomId);
    /**
     * 导入科室
     *
     * @param file 文件信息
     * @return 结果
     */
    String importData(MultipartFile file);

}
