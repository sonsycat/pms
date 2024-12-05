package com.zjry.pms.table.service.impl;

import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.exception.CustomException;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.table.domain.*;
import com.zjry.pms.table.mapper.PmsTableMapper;
import com.zjry.pms.table.service.IPmsTableService;
import com.zjry.pms.table.utils.ExcelDeptarUtils;
import com.zjry.pms.table.utils.ExcelDeptbeUtils;
import com.zjry.pms.table.utils.ExcelHsdeptUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PmsTableServiceImpl  implements IPmsTableService {
    private static final Logger log = LoggerFactory.getLogger(PmsTableServiceImpl.class);

    @Autowired
    private PmsTableMapper pmsTableMapper;


    @Override
    public List<PmsTableOpcRes> selectListOpcList(PmsTableOpcQuery pmsTableOpcQuery) {
        if(null != pmsTableOpcQuery){
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            if(null == pmsTableOpcQuery.getStartTime()){
                pmsTableOpcQuery.setStartTime(format1.format(new Date()));
            }else{
                pmsTableOpcQuery.setStartTime(pmsTableOpcQuery.getStartTime());
            }
            if(null == pmsTableOpcQuery.getEndTime()){
                //当前月份加一
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH,1);
                calendar.add(Calendar.MONTH,1);
                String date = format1.format(calendar.getTime());
                pmsTableOpcQuery.setEndTime(date);
            }else{
                pmsTableOpcQuery.setEndTime(pmsTableOpcQuery.getEndTime());
            }
        }else{
            return null;
        }
        return pmsTableMapper.selectListOpcList(pmsTableOpcQuery);
    }

    @Override
    public List<PmsTableBihRes> selectListBihList(PmsTableBihQuery pmsTableBihQuery) {
        if(null != pmsTableBihQuery){
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            if(null == pmsTableBihQuery.getStartTime()){
                pmsTableBihQuery.setStartTime(format1.format(new Date()));
            }else{
                pmsTableBihQuery.setStartTime(pmsTableBihQuery.getStartTime());
            }
            if(null == pmsTableBihQuery.getEndTime()){
                //当前月份加一
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH,1);
                calendar.add(Calendar.MONTH,1);
                String date = format1.format(calendar.getTime());
                pmsTableBihQuery.setEndTime(date);
            }else{
                pmsTableBihQuery.setEndTime(pmsTableBihQuery.getEndTime());
            }
        }else{
            return null;
        }
        return pmsTableMapper.selectListBihList(pmsTableBihQuery);
    }

    @Override
    public List<PmsTableDeptbeRes> listDeptbeList(PmsTableDeptbeQuery pmsTableDeptbeQuery) {
        if(null != pmsTableDeptbeQuery){
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            if(null == pmsTableDeptbeQuery.getStartTime()){
                pmsTableDeptbeQuery.setStartTime(format1.format(new Date()));
            }else{
                pmsTableDeptbeQuery.setStartTime(pmsTableDeptbeQuery.getStartTime());
            }
            if(null == pmsTableDeptbeQuery.getEndTime()){
                //当前月份加一
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH,1);
                calendar.add(Calendar.MONTH,1);
                String date = format1.format(calendar.getTime());
                pmsTableDeptbeQuery.setEndTime(date);
            }else{
                pmsTableDeptbeQuery.setEndTime(pmsTableDeptbeQuery.getEndTime());
            }
        }else{
            return null;
        }
        return pmsTableMapper.listDeptbeList(pmsTableDeptbeQuery);
    }

    @Override
    public AjaxResult exportDetailDeptbe(PmsTableDeptbeQuery pmsTableDeptbeQuery, String name) {
        List<PmsTableDeptbeRes> list = listDeptbeList(pmsTableDeptbeQuery);
        String filename = ExcelDeptbeUtils.encodingFilename(name);
        XSSFWorkbook wb = new XSSFWorkbook();
        FileOutputStream fileOutputStream  = null;
        Workbook workbook = null;
        try {
            fileOutputStream = new FileOutputStream(ExcelDeptbeUtils.getAbsoluteFile(filename) );
            workbook = ExcelDeptbeUtils.buildData(wb,list);
            // 输出
            workbook.write(fileOutputStream);
            // 6.关闭流
            fileOutputStream.close();
            workbook.close();
            wb.close();
            return AjaxResult.success(filename);
        }  catch (Exception e)
        {
            log.error("导出Excel异常{}", e.getMessage());
            throw new CustomException("导出Excel失败，请联系网站管理员！");
        } finally
        {
            if (wb != null)
            {
                try
                {
                    wb.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fileOutputStream != null)
            {
                try
                {
                    fileOutputStream.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (workbook != null)
            {
                try
                {
                    workbook.close();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<PmsTableHsdeptRes> listHsdeptList(PmsTableHsdeptQuery pmsTableHsdeptQuery) {
        if(null != pmsTableHsdeptQuery){
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            if(null == pmsTableHsdeptQuery.getStartTime()){
                pmsTableHsdeptQuery.setStartTime(format1.format(new Date()));
            }else{
                pmsTableHsdeptQuery.setStartTime(pmsTableHsdeptQuery.getStartTime());
            }
            if(null == pmsTableHsdeptQuery.getEndTime()){
                //当前月份加一
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH,1);
                calendar.add(Calendar.MONTH,1);
                String date = format1.format(calendar.getTime());
                pmsTableHsdeptQuery.setEndTime(date);
            }else{
                pmsTableHsdeptQuery.setEndTime(pmsTableHsdeptQuery.getEndTime());
            }
        }else{
            return null;
        }
        List<PmsTableHsdeptRes> list = pmsTableMapper.listHsdeptList(pmsTableHsdeptQuery);
        if(null !=list && list.size()>0){
            //mysql语句不支持排序 后台排序
            list = list.stream()
                    .sorted(Comparator.comparing(PmsTableHsdeptRes::getClassName))
                    .collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public AjaxResult exportDetailHsdept(PmsTableHsdeptQuery pmsTableHsdeptQuery, String name) {
        List<PmsTableHsdeptRes> list = listHsdeptList(pmsTableHsdeptQuery);
        String filename = ExcelHsdeptUtils.encodingFilename(name);
        XSSFWorkbook wb = new XSSFWorkbook();
        FileOutputStream fileOutputStream  = null;
        Workbook workbook = null;
        try {
            fileOutputStream = new FileOutputStream(ExcelHsdeptUtils.getAbsoluteFile(filename) );
            workbook = ExcelHsdeptUtils.buildData(wb,list);
            // 输出
            workbook.write(fileOutputStream);
            // 6.关闭流
            fileOutputStream.close();
            workbook.close();
            wb.close();
            return AjaxResult.success(filename);
        }  catch (Exception e)
        {
            log.error("导出Excel异常{}", e.getMessage());
            throw new CustomException("导出Excel失败，请联系网站管理员！");
        } finally
        {
            if (wb != null)
            {
                try
                {
                    wb.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fileOutputStream != null)
            {
                try
                {
                    fileOutputStream.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (workbook != null)
            {
                try
                {
                    workbook.close();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<PmsTableDeptarRes> listDeptarList(PmsTableDeptarQuery pmsTableDeptarQuery) {
        if(null != pmsTableDeptarQuery){
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            if(null == pmsTableDeptarQuery.getStartTime()){
                pmsTableDeptarQuery.setStartTime(format1.format(new Date()));
            }else{
                pmsTableDeptarQuery.setStartTime(pmsTableDeptarQuery.getStartTime());
            }
            if(null == pmsTableDeptarQuery.getEndTime()){
                //当前月份加一
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH,1);
                calendar.add(Calendar.MONTH,1);
                String date = format1.format(calendar.getTime());
                pmsTableDeptarQuery.setEndTime(date);
            }else{
                pmsTableDeptarQuery.setEndTime(pmsTableDeptarQuery.getEndTime());
            }
        }else{
            return null;
        }
        return pmsTableMapper.listDeptarList(pmsTableDeptarQuery);
    }

    @Override
    public AjaxResult exportDetailDeptar(PmsTableDeptarQuery pmsTableDeptarQuery, String name) {
        List<PmsTableDeptarRes> list = listDeptarList(pmsTableDeptarQuery);
        String filename = ExcelDeptarUtils.encodingFilename(name);
        XSSFWorkbook wb = new XSSFWorkbook();
        FileOutputStream fileOutputStream  = null;
        Workbook workbook = null;
        try {
            fileOutputStream = new FileOutputStream(ExcelDeptarUtils.getAbsoluteFile(filename) );
            workbook = ExcelDeptarUtils.buildData(wb,list);
            // 输出
            workbook.write(fileOutputStream);
            // 6.关闭流
            fileOutputStream.close();
            workbook.close();
            wb.close();
            return AjaxResult.success(filename);
        }  catch (Exception e)
        {
            log.error("导出Excel异常{}", e.getMessage());
            throw new CustomException("导出Excel失败，请联系网站管理员！");
        } finally
        {
            if (wb != null)
            {
                try
                {
                    wb.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fileOutputStream != null)
            {
                try
                {
                    fileOutputStream.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (workbook != null)
            {
                try
                {
                    workbook.close();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<PmsTableIncsumRes> listIncsumList(PmsTableIncsumQuery pmsTableIncsumQuery) {
        if(null != pmsTableIncsumQuery){
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            if(null == pmsTableIncsumQuery.getStartTime()){
                pmsTableIncsumQuery.setStartTime(format1.format(new Date()));
            }else{
                pmsTableIncsumQuery.setStartTime(pmsTableIncsumQuery.getStartTime());
            }
            if(null == pmsTableIncsumQuery.getEndTime()){
                //当前月份加一
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH,1);
                calendar.add(Calendar.MONTH,1);
                String date = format1.format(calendar.getTime());
                pmsTableIncsumQuery.setEndTime(date);
            }else{
                pmsTableIncsumQuery.setEndTime(pmsTableIncsumQuery.getEndTime());
            }
        }else{
            return null;
        }
        List<PmsTableIncsumRes> list = pmsTableMapper.listIncsumList(pmsTableIncsumQuery);
        if(null !=list && list.size()>0){
            //mysql语句不支持排序 后台排序
            list = list.stream()
                    .sorted(Comparator.comparing(PmsTableIncsumRes::getDeptName))
                    .collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public List<PmsTableDocRes> listDocList(PmsTableDocQuery pmsTableDocQuery) {
        if(null != pmsTableDocQuery){
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            if(null == pmsTableDocQuery.getStartTime()){
                pmsTableDocQuery.setStartTime(format1.format(new Date()));
            }else{
                pmsTableDocQuery.setStartTime(pmsTableDocQuery.getStartTime());
            }
            if(null == pmsTableDocQuery.getEndTime()){
                //当前月份加一
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH,1);
                calendar.add(Calendar.MONTH,1);
                String date = format1.format(calendar.getTime());
                pmsTableDocQuery.setEndTime(date);
            }else{
                pmsTableDocQuery.setEndTime(pmsTableDocQuery.getEndTime());
            }
        }else{
            return null;
        }
        List<PmsTableDocRes> list = pmsTableMapper.listDocList(pmsTableDocQuery);
        if(null !=list && list.size()>0){
            //mysql语句不支持排序 后台排序
            list = list.stream()
                    .sorted(Comparator.comparing(PmsTableDocRes::getOrderedByDoctor))
                    .collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public List<PmsTableCostRes> listCostList(PmsTableCostQuery pmsTableCostQuery) {
        if(null != pmsTableCostQuery){
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            if(null == pmsTableCostQuery.getStartTime()){
                pmsTableCostQuery.setStartTime(format1.format(new Date()));
            }else{
                pmsTableCostQuery.setStartTime(pmsTableCostQuery.getStartTime());
            }
            if(null == pmsTableCostQuery.getEndTime()){
                //当前月份加一
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH,1);
                calendar.add(Calendar.MONTH,1);
                String date = format1.format(calendar.getTime());
                pmsTableCostQuery.setEndTime(date);
            }else{
                pmsTableCostQuery.setEndTime(pmsTableCostQuery.getEndTime());
            }
        }else{
            return null;
        }
        List<PmsTableCostRes> list = pmsTableMapper.listCostList(pmsTableCostQuery);
        return list;
    }
}
