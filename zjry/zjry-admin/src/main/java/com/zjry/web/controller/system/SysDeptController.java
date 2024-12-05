package com.zjry.web.controller.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjry.common.annotation.Log;
import com.zjry.common.constant.UserConstants;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.entity.SysDept;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.system.service.ISysDeptService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 部门信息
 *
 * @author zjry
 */
@RestController
@RequestMapping("/system/dept")
@Api(tags = { "【部门信息】" })
public class SysDeptController extends BaseController {
	@Autowired
	private ISysDeptService deptService;

	/**
	 * 获取部门列表
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:list')")
	@GetMapping("/list")
	@ApiOperation("获取部门列表")
	public AjaxResult list(SysDept dept) {
		List<SysDept> depts = deptService.selectDeptList(dept);
		return AjaxResult.success(depts);
	}

	/**
	 * 查询部门列表（排除节点）
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:list')")
	@GetMapping("/list/exclude/{deptId}")
	@ApiOperation("查询部门列表（排除节点）")
	public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
		List<SysDept> depts = deptService.selectDeptList(new SysDept());
		Iterator<SysDept> it = depts.iterator();
		while (it.hasNext()) {
			SysDept d = (SysDept) it.next();
			if (d.getDeptId().intValue() == deptId
					|| ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + "")) {
				it.remove();
			}
		}
		return AjaxResult.success(depts);
	}

	/**
	 * 根据部门编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:query')")
	@GetMapping(value = "/{deptId}")
	@ApiOperation("根据部门编号获取详细信息")
	public AjaxResult getInfo(@PathVariable Long deptId) {
		return AjaxResult.success(deptService.selectDeptById(deptId));
	}

	/**
	 * 获取部门下拉树列表
	 */
	@GetMapping("/treeselect")
	@ApiOperation("获取部门下拉树列表")
	public AjaxResult treeselect(SysDept dept) {
		List<SysDept> depts = new ArrayList<SysDept>();
		depts = deptService.selectDeptList(dept);
		return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
	}
	
	/**
	 * 获取部门下拉树列表-前三级
	 */
	@GetMapping("/treeSelectLevel")
	@ApiOperation("获取部门下拉树列表(前三级)")
	public AjaxResult treeSelectLevel(SysDept dept) {
		List<SysDept> depts = deptService.selectDeptListLevel(dept);
		return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
	}
	
	@GetMapping("/treeselectName")
	@ApiOperation("根据名称获取部门下拉树列表")
	public AjaxResult treeselectName(SysDept dept) {
		List<SysDept> depts = new ArrayList<SysDept>();
		depts = deptService.selectDeptListByName(dept);
		return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
	}

	/**
	 * 加载对应角色部门列表树
	 */
	@GetMapping(value = "/roleDeptTreeselect/{roleId}")
	@ApiOperation("加载对应角色部门列表树")
	public AjaxResult roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
		List<SysDept> depts = deptService.selectDeptList(new SysDept());
		AjaxResult ajax = AjaxResult.success();
		ajax.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
		ajax.put("depts", deptService.buildDeptTreeSelect(depts));
		return ajax;
	}

	/**
	 * 新增部门
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:add')")
	@Log(title = "部门信息", businessType = BusinessType.INSERT)
	@PostMapping
	@ApiOperation("新增部门")
	public AjaxResult add(@Validated @RequestBody SysDept dept) {
		if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
			return AjaxResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
		}
		dept.setCreateBy(SecurityUtils.getUsername());
		return toAjax(deptService.insertDept(dept));
	}

	/**
	 * 修改部门
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:edit')")
	@Log(title = "部门信息", businessType = BusinessType.UPDATE)
	@PutMapping
	@ApiOperation("修改部门")
	public AjaxResult edit(@Validated @RequestBody SysDept dept) {
		if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
			return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
		} else if (dept.getParentId().equals(dept.getDeptId())) {
			return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
		} else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
				&& deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0) {
			return AjaxResult.error("该部门包含未停用的子部门！");
		}
		dept.setUpdateBy(SecurityUtils.getUsername());
		return toAjax(deptService.updateDept(dept));
	}

	/**
	 * 删除部门
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:remove')")
	@Log(title = "部门管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deptId}")
	@ApiOperation("删除部门")
	public AjaxResult remove(@PathVariable Long deptId) {
		if (deptService.hasChildByDeptId(deptId)) {
			return AjaxResult.error("存在下级部门,不允许删除");
		}
		if (deptService.checkDeptExistUser(deptId)) {
			return AjaxResult.error("部门存在用户,不允许删除");
		}
		return toAjax(deptService.deleteDeptById(deptId));
	}
}
