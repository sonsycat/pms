package com.zjry.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjry.common.annotation.DataScope;
import com.zjry.common.constant.UserConstants;
import com.zjry.common.core.domain.TreeSelect;
import com.zjry.common.core.domain.entity.SysDept;
import com.zjry.common.core.domain.entity.SysRole;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.core.text.Convert;
import com.zjry.common.exception.CustomException;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.system.mapper.SysDeptMapper;
import com.zjry.system.mapper.SysRoleMapper;
import com.zjry.system.service.ISysDeptService;

/**
 * 部门管理 服务实现
 * 
 * @author zjry
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {
	@Autowired
	private SysDeptMapper deptMapper;

	@Autowired
	private SysRoleMapper roleMapper;
	/**
	 * 定义变量，用于流计算
	 */
	List<SysDept> returnList = null;

	/**
	 * 查询部门管理数据
	 * 
	 * @param dept 部门信息
	 * @return 部门信息集合
	 */
	@Override
	@DataScope(deptAlias = "d")
	public List<SysDept> selectDeptList(SysDept dept) {
		//父节点为空，设置当前节点
//		if(dept.getParentId()==null) {
//			LoginUser loginUser = SecurityUtils.getLoginUser();
//			if(loginUser.getCurrDeptId() != null) {
//				dept.setDeptId(loginUser.getCurrDeptId());
//			} else {
//				dept.setDeptId(loginUser.getUser().getDeptId());
//			}
//		}
		List<SysDept> selectDeptList = deptMapper.selectDeptList(dept);
		return selectDeptList;
	}
	
	/**
	 * 查询前三级
	 */
	@Override
	@DataScope(deptAlias = "d")
	public List<SysDept> selectDeptListLevel(SysDept dept) {
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if(loginUser.getCurrDeptId() != null) {
			dept.setDeptId(loginUser.getCurrDeptId());
		} else {
			dept.setDeptId(loginUser.getUser().getDeptId());
		}
		List<SysDept> firstList = new ArrayList<>();
		SysDept rootDept = deptMapper.selectDeptById(dept.getDeptId());
		List<SysDept> secondList = new ArrayList<>();
		SysDept dept1 = new SysDept();
		dept1.setParentId(rootDept.getDeptId());
		secondList = deptMapper.selectDeptList(dept1);
		for(SysDept sysDept : secondList) {
			if(sysDept.isHasChildren()) {
				SysDept dept2 = new SysDept();
				dept2.setParentId(sysDept.getDeptId());
				List<SysDept> deptList2 = deptMapper.selectDeptList(dept2);
				sysDept.setChildren(deptList2);
			}
		}
		rootDept.setChildren(secondList);
		firstList.add(rootDept);
		return firstList;
	}
	
	@Override
	@DataScope(deptAlias = "d")
	public List<SysDept> selectDeptListByName(SysDept dept) {
		SysDept sysDept = new SysDept();
		List<SysDept> selectDeptList = new ArrayList<>();
		if(StringUtils.isNotEmpty(dept.getDeptName())) {
			sysDept.setDeptName(dept.getDeptName());
			selectDeptList = deptMapper.selectDeptList(sysDept);
		} else {
			selectDeptList = this.selectDeptListLevel(sysDept);
		}
		return selectDeptList;
	}

	/**
	 * 构建前端所需要树结构
	 * 
	 * @param depts 部门列表
	 * @return 树结构列表
	 */
	@Override
	public List<SysDept> buildDeptTree(List<SysDept> depts) {
		returnList = new ArrayList<SysDept>();
		List<Long> rootDeptParentId = deptMapper.selectRootDeptParentId();
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
	 * 构建前端所需要下拉树结构
	 * 
	 * @param depts 部门列表
	 * @return 下拉树结构列表
	 */
	@Override
	public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts) {
		List<SysDept> deptTrees = buildDeptTree(depts);
		return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	/**
	 * 根据角色ID查询部门树信息
	 * 
	 * @param roleId 角色ID
	 * @return 选中部门列表
	 */
	@Override
	public List<Long> selectDeptListByRoleId(Long roleId) {
		SysRole role = roleMapper.selectRoleById(roleId);
		return deptMapper.selectDeptListByRoleId(roleId, role.isDeptCheckStrictly());
	}

	/**
	 * 根据部门ID查询信息
	 * 
	 * @param deptId 部门ID
	 * @return 部门信息
	 */
	@Override
	public SysDept selectDeptById(Long deptId) {
		return deptMapper.selectDeptById(deptId);
	}

	/**
	 * 根据ID查询所有子部门（正常状态）
	 * 
	 * @param deptId 部门ID
	 * @return 子部门数
	 */
	@Override
	public int selectNormalChildrenDeptById(Long deptId) {
		return deptMapper.selectNormalChildrenDeptById(deptId);
	}

	/**
	 * 是否存在子节点
	 * 
	 * @param deptId 部门ID
	 * @return 结果
	 */
	@Override
	public boolean hasChildByDeptId(Long deptId) {
		int result = deptMapper.hasChildByDeptId(deptId);
		return result > 0 ? true : false;
	}

	/**
	 * 查询部门是否存在用户
	 * 
	 * @param deptId 部门ID
	 * @return 结果 true 存在 false 不存在
	 */
	@Override
	public boolean checkDeptExistUser(Long deptId) {
		int result = deptMapper.checkDeptExistUser(deptId);
		return result > 0 ? true : false;
	}

	/**
	 * 校验部门名称是否唯一
	 * 
	 * @param dept 部门信息
	 * @return 结果
	 */
	@Override
	public String checkDeptNameUnique(SysDept dept) {
		Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
		SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
		if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 新增保存部门信息
	 * 
	 * @param dept 部门信息
	 * @return 结果
	 */
	@Override
	public int insertDept(SysDept dept) {
		SysDept info = deptMapper.selectDeptById(dept.getParentId());
		// 如果父节点不为正常状态,则不允许新增子节点
		if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
			throw new CustomException("部门停用，不允许新增");
		}
		dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
		return deptMapper.insertDept(dept);
	}

	/**
	 * 修改保存部门信息
	 * 
	 * @param dept 部门信息
	 * @return 结果
	 */
	@Override
	public int updateDept(SysDept dept) {
		SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
		SysDept oldDept = deptMapper.selectDeptById(dept.getDeptId());
		if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
			String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
			String oldAncestors = oldDept.getAncestors();
			dept.setAncestors(newAncestors);
			updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
		}
		int result = deptMapper.updateDept(dept);
		if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
			// 如果该部门是启用状态，则启用该部门的所有上级部门
			updateParentDeptStatusNormal(dept);
		}
		return result;
	}

	/**
	 * 修改该部门的父级部门状态
	 * 
	 * @param dept 当前部门
	 */
	private void updateParentDeptStatusNormal(SysDept dept) {
		String ancestors = dept.getAncestors();
		Long[] deptIds = Convert.toLongArray(ancestors);
		deptMapper.updateDeptStatusNormal(deptIds);
	}

	/**
	 * 修改子元素关系
	 * 
	 * @param deptId       被修改的部门ID
	 * @param newAncestors 新的父ID集合
	 * @param oldAncestors 旧的父ID集合
	 */
	public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
		List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
		for (SysDept child : children) {
			child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
		}
		if (children.size() > 0) {
			deptMapper.updateDeptChildren(children);
		}
	}

	/**
	 * 删除部门管理信息
	 * 
	 * @param deptId 部门ID
	 * @return 结果
	 */
	@Override
	public int deleteDeptById(Long deptId) {
		return deptMapper.deleteDeptById(deptId);
	}

	/**
	 * 递归列表
	 */
	private void recursionFn(List<SysDept> list, SysDept t) {
		// 得到子节点列表
		List<SysDept> childList = list.parallelStream().filter(
				n -> StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue())
				.collect(Collectors.toList());
		t.setChildren(childList);
		childList.parallelStream().forEach(tChild -> recursionFn(list, tChild));
	}

	/**
	 * 得到子节点列表
	 */
	private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
		List<SysDept> tlist = new ArrayList<SysDept>();
		list.stream().filter(
				n -> StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue())
				.collect(Collectors.toList());
		Iterator<SysDept> it = list.iterator();
		while (it.hasNext()) {
			SysDept n = (SysDept) it.next();
			if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue()) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	/**
	 * 判断是否有子节点
	 */
	private boolean hasChild(List<SysDept> list, SysDept t) {
		return getChildList(list, t).size() > 0 ? true : false;
	}
}
