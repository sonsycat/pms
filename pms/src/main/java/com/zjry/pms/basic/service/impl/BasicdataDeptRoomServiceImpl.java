package com.zjry.pms.basic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.constant.UserConstants;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.exception.CustomException;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.basic.constant.Constants;
import com.zjry.pms.basic.domain.basicData.*;
import com.zjry.pms.basic.mapper.basicData.BasicdataDeptMapper;
import com.zjry.pms.basic.mapper.basicData.BasicdataDeptRoomMapper;
import com.zjry.pms.basic.service.IBasicdataCodeRuleService;
import com.zjry.pms.basic.service.IBasicdataDeptRoomService;
import com.zjry.pms.basic.utils.ImportExcel;
import com.zjry.pms.basic.utils.Pinyin4jUtil;
import com.zjry.pms.basic.utils.ValidUtil;
import com.zjry.pms.rocketmq.BasicdataProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 科室Service业务层处理
 * 
 * @author cyq
 * @date 2021-07-16
 */
@Service
public class BasicdataDeptRoomServiceImpl extends ServiceImpl<BasicdataDeptRoomMapper, BasicdataDeptRoom> implements IBasicdataDeptRoomService {
    @Autowired
    private BasicdataDeptRoomMapper basicdataDeptRoomMapper;
    @Autowired
    private IBasicdataCodeRuleService iBasicdataCodeRuleService;
    @Autowired
    private BasicdataDeptMapper basicdataDeptMapper;
    @Autowired
    private IBasicdataDeptRoomService basicdataDeptRoomService;
    @Autowired
    private BasicdataProducer producer;
    /**
     * 定义变量，用于流计算
     */
    List<BasicdataDeptRoomVo> returnList = null;

    /**
     * 查询科室
     *
     * @param deptRoomId 科室ID
     * @return 科室
     */
    @Override
    public BasicdataDeptRoom selectBasicdataDeptRoomById(Long deptRoomId) {
        return basicdataDeptRoomMapper.selectBasicdataDeptRoomById(deptRoomId);
    }

    /**
     * 查询科室列表
     * 
     * @param basicdataDeptRoom 科室
     * @return 科室
     */
    @Override
    public List<BasicdataDeptRoom> selectBasicdataDeptRoomList(BasicdataDeptRoom basicdataDeptRoom) {
        //查询登录信息
        LoginUser user = SecurityUtils.getLoginUser();
        if(basicdataDeptRoom.getDeptId()==null){
            basicdataDeptRoom.setDeptId(user.getUser().getDeptId());
        }
        return basicdataDeptRoomMapper.selectBasicdataDeptRoomList(basicdataDeptRoom);
    }

    /**
     * 获取科室下列表 【通知公告】
     * @param basicdataDeptRoom
     * @return
     */
    @Override
    public List<BasicdataDeptRoom> selectBasicdataDeptRoomListForNotice(BasicdataDeptRoom basicdataDeptRoom) {
        return basicdataDeptRoomMapper.selectBasicdataDeptRoomListForNotice(basicdataDeptRoom);
    }

    /**
     * 新增科室
     *
     * @param basicdataDeptRoom 科室
     * @return 结果
     */
//    @Override
//    public int insertBasicdataDeptRoom(BasicdataDeptRoom basicdataDeptRoom) {
//        //获取父级信息
//        BasicdataDeptRoom dept= basicdataDeptRoomMapper.selectBasicdataDeptRoomById(basicdataDeptRoom.getParentId());
//        String pcode="0";
//        if(dept!=null){
//            pcode=dept.getDeptRoomCode();
//        }
//        //获取编码
//        String newFlowcode = iBasicdataCodeRuleService.generateCode("sys_dept_room", "dept_room_code",pcode);
//        //获取最大值
//        int count = basicdataDeptRoomMapper.getSize(basicdataDeptRoom);
//        basicdataDeptRoom.setDeptRoomCode(newFlowcode);
//        basicdataDeptRoom.setSort((long) (1+count));
//        basicdataDeptRoom.setCreateTime(DateUtils.getNowDate());
//        basicdataDeptRoom.setUpdateTime(DateUtils.getNowDate());
//        basicdataDeptRoom.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
//        if(dept!=null){
//            basicdataDeptRoom.setParentName(dept.getDeptRoomName());
//        }
//        int temp = basicdataDeptRoomMapper.insertBasicdataDeptRoom(basicdataDeptRoom);
//        //发送信息
//        int msg = sendMsg(basicdataDeptRoom);
//        if(msg == Constants.DOWNTIME){
//            return msg;
//        }else{
//            return temp;
//        }
//    }

    @Override
    public int insertBasicdataDeptRoom(BasicdataDeptRoom basicdataDeptRoom) {
        //获取最大值
        int count = basicdataDeptRoomMapper.getSize(basicdataDeptRoom);
        basicdataDeptRoom.setSort((long) (1+count));
        basicdataDeptRoom.setCreateTime(DateUtils.getNowDate());
        basicdataDeptRoom.setUpdateTime(DateUtils.getNowDate());
        basicdataDeptRoom.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        int temp = basicdataDeptRoomMapper.insertBasicdataDeptRoom(basicdataDeptRoom);
        //发送信息
        int msg = sendMsg(basicdataDeptRoom);
        if(msg == Constants.DOWNTIME){
            return msg;
        }else{
            return temp;
        }
    }

    /**
     * 修改科室
     *
     * @param basicdataDeptRoom 科室
     * @return 结果
     */
    @Override
    public int updateBasicdataDeptRoom(BasicdataDeptRoom basicdataDeptRoom) {
        //获取父级信息
        BasicdataDeptRoom dept= basicdataDeptRoomMapper.selectBasicdataDeptRoomById(basicdataDeptRoom.getParentId());
        if(dept!=null){
            basicdataDeptRoom.setParentName(dept.getDeptRoomName());
        }
        basicdataDeptRoom.setUpdateTime(DateUtils.getNowDate());
        basicdataDeptRoom.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        int temp = basicdataDeptRoomMapper.updateBasicdataDeptRoom(basicdataDeptRoom);
        //修改子级信息
        BasicdataDeptRoom deptRoom=new BasicdataDeptRoom();
        deptRoom.setParentId(basicdataDeptRoom.getDeptRoomId());
        List<BasicdataDeptRoom> list = basicdataDeptRoomMapper.selectBasicdataDeptRoomList(deptRoom);
        for (BasicdataDeptRoom room : list) {
            room.setUpdateTime(DateUtils.getNowDate());
            room.setParentName(basicdataDeptRoom.getDeptRoomName());
            room.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
            basicdataDeptRoomMapper.updateBasicdataDeptRoom(room);
            //发送信息
            sendMsg(room);
        }
        //发送信息
        int msg = sendMsg(basicdataDeptRoom);
        if(msg == Constants.DOWNTIME){
            return msg;
        }else{
            return temp;
        }
    }

    /**
     * 批量删除科室
     *
     * @param deptRoomIds 需要删除的科室ID
     * @return 结果
     */
    @Override
    public int deleteBasicdataDeptRoomByIds(Long[] deptRoomIds) {
        return basicdataDeptRoomMapper.deleteBasicdataDeptRoomByIds(deptRoomIds);
    }

    /**
     * 删除科室信息
     *
     * @param deptRoomId 科室ID
     * @return 结果
     */
    @Override
    public int deleteBasicdataDeptRoomById(Long deptRoomId) {
        int temp = basicdataDeptRoomMapper.deleteBasicdataDeptRoomById(deptRoomId);
        BasicdataDeptRoom basicdataDeptRoom = new BasicdataDeptRoom();
        basicdataDeptRoom.setDeptRoomId(deptRoomId);
        basicdataDeptRoom.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
        //发送信息
        int msg = sendMsg(basicdataDeptRoom);
        if(msg == Constants.DOWNTIME){
            return msg;
        }else{
            return temp;
        }
    }

    @Override
    public List<BasicdataDeptRoomVo> selectDeptRoomList(BasicdataDeptRoomVo basicdataDeptRoom) {
        return basicdataDeptRoomMapper.selectDeptRoomList(basicdataDeptRoom);
    }
    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 科室列表
     * @return 下拉树结构列表
     */
    @Override
    public List<Tree> buildDeptRoomTree(List<BasicdataDeptRoomVo> depts) {
        List<BasicdataDeptRoomVo> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(Tree::new).collect(Collectors.toList());
    }
    /**
     * 构建前端所需要下拉树结构
     *
     * @param deptRooms 科室列表
     * @return 下拉树结构列表
     */
    @Override
    public List<OrganTree> buildDeptRoomOrganTree(List<BasicdataDeptRoomVo> deptRooms) {
        List<BasicdataDeptRoomVo> deptTrees = buildDeptTree(deptRooms);
        return deptTrees.stream().map(OrganTree::new).collect(Collectors.toList());
    }
    /**
     * 校验科室名称是否唯一
     *
     * @param basicdataDeptRoom 科室信息
     * @return 结果
     */
    @Override
    public String checkDeptRoomNameUnique(BasicdataDeptRoom basicdataDeptRoom) {
        Long deptRoomId = StringUtils.isNull(basicdataDeptRoom.getDeptRoomId()) ? -1L : basicdataDeptRoom.getDeptRoomId();
        BasicdataDeptRoom info = basicdataDeptRoomMapper.checkDeptRoomNameUnique(basicdataDeptRoom);
        if (StringUtils.isNotNull(info) && info.getDeptRoomId().longValue() != deptRoomId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
    /**
     * 存在下级科室判断
     *
     * @param deptRoomId 科室ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptRoomId(Long deptRoomId) {
        int result = basicdataDeptRoomMapper.hasChildBydeptRoomId(deptRoomId);
        return result > 0 ? true : false;
    }
    /**
     * 查询是否存在用户
     *
     * @param deptRoomId 科室ID
     * @return 结果
     */
    @Override
    public boolean checkExistUser(Long deptRoomId) {
        int result = basicdataDeptRoomMapper.checkDeptExistUser(deptRoomId);
        return result > 0;
    }
    /**
     * 导入数据
     *
     * @param file 数据
     * @return 结果
     */
    @Override
    public String importData(MultipartFile file) {
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        try{
            if(StringUtils.isNotEmpty(file.getOriginalFilename())){
                if(!file.getOriginalFilename().contains(Constants.VALID_DEPTROOM)){
                    throw new CustomException("请使用系统提供的科室信息模板！");
                }
            }
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<BasicdataDeptRoom> list = ei.getDataList(BasicdataDeptRoom.class);
            if (StringUtils.isNull(list) || list.size() == 0)
            {
                throw new CustomException("导入数据不能为空！");
            }
            //导入表格重复数据校验
            List<BasicdataDeptRoom> newList =new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                if(i>0){
                    newList.add(list.get(i-1));
                }
                //验证重复数据
                for(int j = 0; j < newList.size(); j++){
                    if (ValidUtil.isNotEmpty(list.get(i).getDeptRoomName()) && list.get(i).getDeptRoomName().trim().equals(newList.get(j).getDeptRoomName().trim())
                            && list.get(i).getDeptCode().trim().equals(newList.get(j).getDeptCode().trim())) {
                        failureMsg.append("<br/>" + "科室名称【"+list.get(i).getDeptRoomName()+"】重复");
                        failureNum ++;
                    }
                }
            }
            //数据校验
            for (BasicdataDeptRoom data : list) {
                if(ValidUtil.isEmpty(data.getDeptRoomName())){
                    failureMsg.append("<br/>" + "科室名称不能为空 ");
                    failureNum ++;
                }
                if(ValidUtil.isEmpty(data.getDeptCode())){
                    failureMsg.append("<br/>"+"科室【"+data.getDeptRoomName()+"】所属单位编码不能为空 ");
                    failureNum ++;
                }
                // 验证是否存在
                if(ValidUtil.isNotEmpty(data.getDeptCode())) {
                    BasicdataDeptVo basicdataDeptVo = new BasicdataDeptVo();
                    basicdataDeptVo.setDeptCode(data.getDeptCode().trim());
                    List<BasicdataDeptVo> dept = basicdataDeptMapper.selectBasicdataDeptList(basicdataDeptVo);
                    if (ValidUtil.isEmpty(dept)) {
                        failureMsg.append("<br/>" + "科室【" + data.getDeptRoomName() + "】所属单位编码不存在 ");
                        failureNum++;
                    } else if(ValidUtil.isNotEmpty(data.getDeptRoomName())){
                        data.setDeptId(dept.get(0).getDeptId());
                        String u = basicdataDeptRoomService.checkDeptRoomNameUnique(data);
                        if (UserConstants.NOT_UNIQUE.equals(u)) {
                            failureNum++;
                            failureMsg.append("<br/>" + "科室【" + data.getDeptRoomName() + "】已经存在  ");
                        }
                    }
                }
                //验证上级科室存在
                BasicdataDeptRoom r=new BasicdataDeptRoom();
                r.setDeptRoomCode(data.getDeptRoomCode());
                List<BasicdataDeptRoom> room = basicdataDeptRoomMapper.selectBasicdataDeptRoomList(r);
                if(ValidUtil.isEmpty(room)){
                    failureMsg.append("<br/>"+"科室【"+data.getDeptRoomName()+"】上级科室编码不存在 ");
                    failureNum ++;
                }

                // 验证是否核算
                if(!"0".equals(data.getIsAccounting())  && !"1".equals(data.getIsAccounting())){
                    failureMsg.append("<br/>"+"科室【"+data.getDeptRoomName()+"】是否核算只能输入0或1 ");
                    failureNum ++;
                }
                if(!"0".equals(data.getIsIndependentAccounting())  && !"1".equals(data.getIsIndependentAccounting())){
                    failureMsg.append("<br/>"+"科室【"+data.getDeptRoomName()+"】是否独立核算只能输入0或1 ");
                    failureNum ++;
                }
                if(!"0".equals(data.getIsPurchase())  && !"1".equals(data.getIsPurchase())){
                    failureMsg.append("<br/>"+"科室【"+data.getDeptRoomName()+"】是否采购只能输入0或1 ");
                    failureNum ++;
                }
                if(!"0".equals(data.getIsService())  && !"1".equals(data.getIsService())){
                    failureMsg.append("<br/>"+"科室【"+data.getDeptRoomName()+"】是否服务只能输入0或1 ");
                    failureNum ++;
                }
                if(!"0".equals(data.getIsLeaf())  && !"1".equals(data.getIsLeaf())){
                    failureMsg.append("<br/>"+"科室【"+data.getDeptRoomName()+"】是否末级只能输入0或1 ");
                    failureNum ++;
                }

            }
            if(failureNum>0){
                return "成功导入0条,失败"+list.size()+"条,失败信息如下：</br>"+failureMsg;
            }
            // 保存数据
            LoginUser user = SecurityUtils.getLoginUser();
            String operName = user.getUsername();
            for (BasicdataDeptRoom data : list) {
                //查询单位信息
                BasicdataDeptVo basicdataDeptVo=new BasicdataDeptVo();
                basicdataDeptVo.setDeptCode(data.getDeptCode());
                List<BasicdataDeptVo> dept = basicdataDeptMapper.selectBasicdataDeptList(basicdataDeptVo);
                data.setDeptId(dept.get(0).getDeptId());
                data.setDeptName(dept.get(0).getDeptName());
                data.setPym(Pinyin4jUtil.getPinYinHeadChar(data.getDeptRoomName()));
                //查询父级科室信息
                if(ValidUtil.isEmpty(data.getDeptRoomCode())){
                    data.setParentId(0L);
                }else {
                    BasicdataDeptRoom r=new BasicdataDeptRoom();
                    r.setDeptRoomCode(data.getDeptRoomCode());
                    List<BasicdataDeptRoom> room = basicdataDeptRoomMapper.selectBasicdataDeptRoomList(r);
                    data.setParentId(room.get(0).getDeptRoomId());
                    data.setParentName(room.get(0).getDeptRoomName());
                }
                data.setCreateBy(operName);
                successNum++;
                this.insertBasicdataDeptRoom(data);
            }
        }catch (Exception e){
            failureNum++;
            String msg = "导入失败,失败信息如下：</br>";
            failureMsg.append(msg + e.getMessage());
            log.error(msg, e);
        }
        if (failureNum > 0)
        {
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条");
        }
        return successMsg.toString();
    }


    /**
     * 前端所需要树结构
     *
     * @param depts 科室列表
     * @return 树结构列表
     */
    private List<BasicdataDeptRoomVo> buildDeptTree(List<BasicdataDeptRoomVo> depts) {
        returnList = new ArrayList<BasicdataDeptRoomVo>();
        List<Long> rootDeptParentId = basicdataDeptRoomMapper.selectRootParentId();
        // 重写循环，效果不明显，后续根据情况修改或还原
        depts.parallelStream().filter(dept -> rootDeptParentId.contains(dept.getParentId())).spliterator()
                .forEachRemaining(dept -> {
                    recursionFn(depts, dept);
                    returnList.add(dept);
                });
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<BasicdataDeptRoomVo> list, BasicdataDeptRoomVo t) {
        // 得到子节点列表
        List<BasicdataDeptRoomVo> childList = list.parallelStream().filter(
                n -> StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptRoomId().longValue())
                .collect(Collectors.toList());
        t.setChildren(childList);
        childList.parallelStream().forEach(tChild -> recursionFn(list, tChild));
    }


    /**
     * 调用生产者
     * @param basicdataDeptRoom
     */
    public int sendMsg(BasicdataDeptRoom basicdataDeptRoom){
        //调用rocketMQ发布消息
        return producer.sendMessageOfBasicData(JSONObject.toJSONString(basicdataDeptRoom),1, Constants.DESTINATION_DEPT_ROOM, basicdataDeptRoom.getDeptRoomId());
    }
}
