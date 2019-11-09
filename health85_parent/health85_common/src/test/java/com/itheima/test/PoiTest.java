package com.itheima.test;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PoiTest {
    @Test
    public void demo1() throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook("d:\\hello.xlsx");
        //通过工作簿获取工作簿
        XSSFSheet sheet = workbook.getSheetAt(0);
        //遍历所有的行
        for (Row row : sheet) {
            //遍历所有的单元格
            for (Cell cell : row) {
                System.out.println(cell.getStringCellValue());
            }
        }
        workbook.close();
    }

    @Test
    public void demo2() throws IOException {

        //加载excel,获取工作簿实例
        XSSFWorkbook workbook = new XSSFWorkbook("d:\\hello.xlsx");
        //获取工作表
        XSSFSheet sheet = workbook.getSheetAt(0);

        //获取最后一行的行号
        int lastRowNum = sheet.getLastRowNum();
        //遍历所有的行
        for (int i = 0; i <= lastRowNum; i++) {
            //获取sheet中的每一行
            XSSFRow row = sheet.getRow(i);
            //获取行中最后一个单元格的索引号
            short lastCellNum = row.getLastCellNum();
            //遍历行中的所有的单元格
            for (short j = 0; j < lastCellNum; j++) {
                XSSFCell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }

        }

        workbook.close();

    }

    @Test
    public void demo3() throws Exception {
        //创建工作簿实例
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建工作表
        XSSFSheet sheet = workbook.createSheet("itcast");
        //创建行
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("序号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("年龄");

        for (int i = 1; i < 10; i++) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue("admin"+i);
            row.createCell(2).setCellValue(18+i);
        }

        FileOutputStream outputStream = new FileOutputStream("d:\\itheima.xlsx");
        workbook.write(outputStream);
        outputStream.flush();//缓冲区
        outputStream.close();
        workbook.close();
    }

}
