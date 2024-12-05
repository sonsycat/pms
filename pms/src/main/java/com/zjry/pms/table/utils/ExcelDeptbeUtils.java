package com.zjry.pms.table.utils;

import com.zjry.common.config.ZjRyConfig;
import com.zjry.pms.table.domain.PmsTableDeptbeRes;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExcelDeptbeUtils {
    public static Workbook buildData(Workbook wb, List<PmsTableDeptbeRes> list) throws IOException {
        // 创建sheet页
        Sheet sheetName = wb.createSheet("sheetName");
        sheetName.setColumnWidth(0,5000);
        // 填充表头
        Row row = sheetName.createRow(0);
        row.createCell(0).setCellValue("科室");
        row.getCell(0).setCellStyle( createStyles(wb).get("header"));
        row.createCell(1).setCellValue("总收入");
        row.getCell(1).setCellStyle( createStyles(wb).get("header"));
        row.createCell(2).setCellValue("住院");
        row.getCell(2).setCellStyle( createStyles(wb).get("header"));
        row.createCell(5).setCellValue("门诊");
        row.getCell(5).setCellStyle( createStyles(wb).get("header"));
        Row row2 = sheetName.createRow(1);
        row2.createCell(2).setCellValue("小计");
        row2.getCell(2).setCellStyle( createStyles(wb).get("header"));
        row2.createCell(3).setCellValue("实际收入");
        row2.getCell(3).setCellStyle( createStyles(wb).get("header"));
        row2.createCell(4).setCellValue("计价收入");
        row2.getCell(4).setCellStyle( createStyles(wb).get("header"));
        row2.createCell(5).setCellValue("小计");
        row2.getCell(5).setCellStyle( createStyles(wb).get("header"));
        row2.createCell(6).setCellValue("实际收入");
        row2.getCell(6).setCellStyle( createStyles(wb).get("header"));
        row2.createCell(7).setCellValue("计价收入");
        row2.getCell(7).setCellStyle( createStyles(wb).get("header"));

        // 合并单元格，参数分别是：起始行号，结束行号，起始列号，结束列号
        sheetName.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        sheetName.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
        sheetName.addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
        sheetName.addMergedRegion(new CellRangeAddress(0, 0, 5, 7));
        // 填充内容
        if(null !=list && list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                PmsTableDeptbeRes excelDto = list.get(i);
                row = sheetName.createRow(i + 2);
                row.createCell(0).setCellValue(excelDto.getDeptName());
                row.getCell(0).setCellStyle( createStyles(wb).get("data"));
                row.createCell(1).setCellValue(excelDto.getHj());
                row.getCell(1).setCellStyle( createStyles(wb).get("data"));
                row.createCell(2).setCellValue(excelDto.getZyhj());
                row.getCell(2).setCellStyle( createStyles(wb).get("data"));
                row.createCell(3).setCellValue(excelDto.getZyss());
                row.getCell(3).setCellStyle( createStyles(wb).get("data"));
                row.createCell(4).setCellValue(excelDto.getZyjd());
                row.getCell(4).setCellStyle( createStyles(wb).get("data"));
                row.createCell(5).setCellValue(excelDto.getMzhj());
                row.getCell(5).setCellStyle( createStyles(wb).get("data"));
                row.createCell(6).setCellValue(excelDto.getMzss());
                row.getCell(6).setCellStyle( createStyles(wb).get("data"));
                row.createCell(7).setCellValue(excelDto.getMzjd());
                row.getCell(7).setCellStyle( createStyles(wb).get("data"));

            }
        }
        // sheet页重命名
        wb.setSheetName(0, "sheet");
       return wb;


    }

   
    private static Map<String, CellStyle> createStyles(Workbook wb)
    {
        // 写入各条记录,每条记录对应excel表中的一行
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font totalFont = wb.createFont();
        totalFont.setFontName("Arial");
        totalFont.setFontHeightInPoints((short) 10);
        style.setFont(totalFont);
        styles.put("total", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.LEFT);
        styles.put("data1", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        styles.put("data2", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        styles.put("data3", style);

        return styles;
    }
    public static String encodingFilename(String filename) {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
        return filename;
    }
    public static String getAbsoluteFile(String filename)
    {
        String downloadPath = ZjRyConfig.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }

}
