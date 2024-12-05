package com.zjry.framework.config.provider.ureport;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.system.domain.SysUreportFile;
import com.zjry.system.mapper.SysUreportFileMapper;

/**
 * @Author: mingc
 * @DateTime: 2021/7/5
 */
@Component
public class DBReportProvider implements ReportProvider {
	private String prefix = "mysql:";
	private boolean disabled;

	@Autowired
	private SysUreportFileMapper sysUreportFileMapper;

	@Override
	public InputStream loadReport(String file) {
		if (file.startsWith(prefix)) {
			file = file.substring(prefix.length());
		}
		SysUreportFile sysUreportFile = getSysUreportFile(file);
		byte[] fileContent = sysUreportFile.getFileContent();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent);
		return inputStream;
	}

	private SysUreportFile getSysUreportFile(String file) {
		LambdaQueryWrapper<SysUreportFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(SysUreportFile::getFileName, file).eq(SysUreportFile::getDelFlag,
				BaseEntity.DEL_FLAG_NORMAL);
		List<SysUreportFile> sysUreportFiles = sysUreportFileMapper.selectList(lambdaQueryWrapper);
		SysUreportFile sysUreportFile = new SysUreportFile();
		if (sysUreportFiles.size() == 1) {
			sysUreportFile = sysUreportFiles.get(0);
		}
		return sysUreportFile;
	}

	@Override
	public void deleteReport(String file) {
		if (file.startsWith(prefix)) {
			file = file.substring(prefix.length());
		}
		SysUreportFile ureportFile = new SysUreportFile();
		ureportFile.setFileName(file);
		UpdateWrapper<SysUreportFile> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("file_name", file).set("del_flag", BaseEntity.DEL_FLAG_DELETE);
		sysUreportFileMapper.update(null, updateWrapper);
	}

	@Override
	public List<ReportFile> getReportFiles() {
		List<ReportFile> list = new ArrayList<>();
		LambdaQueryWrapper<SysUreportFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(SysUreportFile::getDelFlag, BaseEntity.DEL_FLAG_NORMAL);
			List<SysUreportFile> list1 = sysUreportFileMapper.selectList(lambdaQueryWrapper);
			list1.forEach(s -> {
				list.add(new ReportFile(s.getFileName(), s.getUpdateTime()));
			});
		List<ReportFile> finalList = list.stream().sorted((s1, s2) -> s2.getUpdateDate().compareTo(s1.getUpdateDate()))
				.collect(Collectors.toList());
		return finalList;
	}

	@Override
	public void saveReport(String file, String content) {
		if (file.startsWith(prefix)) {
			file = file.substring(prefix.length());
		}
		// content = content.replace("UTF-8", "GB2312");
		SysUreportFile sysUreportFile = getSysUreportFile(file);
		SysUreportFile basicUreportFile = new SysUreportFile();
		basicUreportFile.setFileName(file);
		try {
			basicUreportFile.setFileContent(content.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (sysUreportFile != null&&sysUreportFile.getId()!=null) {
			//basicUreportFile.preUpdate();
			//basicUreportFile.setUpdateTime(new Date());
			basicUreportFile.setId(sysUreportFile.getId());
			sysUreportFileMapper.updateById(basicUreportFile);
		} else {
			//basicUreportFile.preInsert();
			//basicUreportFile.setCreateTime(new Date());
			//basicUreportFile.setUpdateTime(new Date());
			sysUreportFileMapper.insert(basicUreportFile);
		}
	}

	@Override
	public String getName() {
		return "数据库存储";
	}

	@Override
	public boolean disabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

}
