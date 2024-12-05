package com.zjry.pms.encode.service.impl;

import com.zjry.pms.common.service.impl.SysFlowcodeServiceImpl;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.encode.domain.HrEncodeRuleTree;
import com.zjry.pms.encode.mapper.HrEncodeRuleTreeMapper;
import com.zjry.pms.encode.service.IHrEncodeRuleTreeService;
import com.zjry.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 编码规则树形Service业务层处理
 * 
 * @author syw
 * @date 2021-06-25
 */
@Service
public class HrEncodeRuleTreeServiceImpl implements IHrEncodeRuleTreeService 
{
    @Resource
    private HrEncodeRuleTreeMapper hrEncodeRuleTreeMapper;
    @Autowired
    private SysFlowcodeServiceImpl sysFlowcodeService;

    /**
     * 查询编码规则树形
     * 
     * @param id 编码规则树形ID
     * @return 编码规则树形
     */
    @Override
    public HrEncodeRuleTree selectHrEncodeRuleTreeById(Long id)
    {
        return hrEncodeRuleTreeMapper.selectHrEncodeRuleTreeById(id);
    }

    /**
     * 查询编码规则树形列表
     * 
     * @param hrEncodeRuleTree 编码规则树形
     * @return 编码规则树形
     */
    @Override
    public List<HrEncodeRuleTree> selectHrEncodeRuleTreeList(HrEncodeRuleTree hrEncodeRuleTree)
    {
        return hrEncodeRuleTreeMapper.selectHrEncodeRuleTreeList(hrEncodeRuleTree);
    }

    /**
     * 新增编码规则树形
     * 
     * @param hrEncodeRuleTree 编码规则树形
     * @return 结果
     */
    @Override
    public int insertHrEncodeRuleTree(HrEncodeRuleTree hrEncodeRuleTree)
    {
        hrEncodeRuleTree.setCreateTime(DateUtils.getNowDate());
        return hrEncodeRuleTreeMapper.insertHrEncodeRuleTree(hrEncodeRuleTree);
    }

    /**
     * 修改编码规则树形
     * 
     * @param hrEncodeRuleTree 编码规则树形
     * @return 结果
     */
    @Override
    public int updateHrEncodeRuleTree(HrEncodeRuleTree hrEncodeRuleTree)
    {
        hrEncodeRuleTree.setUpdateTime(DateUtils.getNowDate());
        return hrEncodeRuleTreeMapper.updateHrEncodeRuleTree(hrEncodeRuleTree);
    }

    /**
     * 批量删除编码规则树形
     * 
     * @param ids 需要删除的编码规则树形ID
     * @return 结果
     */
    @Override
    public int deleteHrEncodeRuleTreeByIds(Long[] ids)
    {
        return hrEncodeRuleTreeMapper.deleteHrEncodeRuleTreeByIds(ids);
    }

    /**
     * 删除编码规则树形信息
     * 
     * @param id 编码规则树形ID
     * @return 结果
     */
    @Override
    public int deleteHrEncodeRuleTreeById(Long id)
    {
        return hrEncodeRuleTreeMapper.deleteHrEncodeRuleTreeById(id);
    }

    /**
     *按规则生成树编码
     * @param databaseName 数据库表名
     * @param  fieldName 字段名
     * @param parentCode 父编码
     * @return String 按数据库规则生成的流水号
     *  @throws Exception
     * */
    @Override
    public String generateTreeEncode(String databaseName, String fieldName,String parentCode)throws Exception{
        //查询编码规则
        HrEncodeRuleTree hrEncodeRuleTree = new HrEncodeRuleTree();
        hrEncodeRuleTree.setRuleDb(databaseName);
        hrEncodeRuleTree.setRuleColumn(fieldName);
        List<HrEncodeRuleTree> list = hrEncodeRuleTreeMapper.selectHrEncodeRuleTreeList(hrEncodeRuleTree);
        //查询到规则
        if(list.size()>0){
            HrEncodeRuleTree hrTree = list.get(0);
            if(hrTree!=null && StringUtils.isNotEmpty(hrTree.getRuleContent())){
                //4-2-2-2
                String[] codeArray = hrTree.getRuleContent().split("-");
                //总长度
                List<String> codeList = Arrays.asList(codeArray);
                Integer allLength = codeList.stream().mapToInt(item -> Integer.parseInt(item)).sum();
                //没有父编码
                if(StringUtils.isEmpty(parentCode)){
                    //父编码用表名生成
                    return sysFlowcodeService.getEntityFlowcodeNoCode(databaseName, Integer.parseInt(codeArray[0]));
                }else{
                    //判断是否超出规定层级
                    if(parentCode.length() >= allLength){
                        throw new Exception("生成编码已超出规定层级");
                    }else{
                        //返回子数据层级
                        int codeLeave = 0;
                        for (int i=0;i<codeArray.length;i++){
                            codeLeave += Integer.parseInt(codeArray[i]);
                            if(parentCode.length() == codeLeave){
                             String code =  sysFlowcodeService.getEntityFlowcodeNoCode(databaseName+parentCode , Integer.parseInt(codeArray[i+1]));
                                return  parentCode+code;

                            }
                        }
                    }
                }
            }else{
                throw new Exception("未查询到相关编码");
            }
        }else{
            throw new Exception("未查询到相关编码");
        }
        return null;
    }
}
