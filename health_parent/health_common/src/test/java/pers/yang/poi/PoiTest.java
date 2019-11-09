package pers.yang.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;

/**
 * @author yf
 * @date 2019/11/1
 */
public class PoiTest {

    @Test
    public void test01() throws Exception {
        // 创建工作薄对象
        XSSFWorkbook sheets = new XSSFWorkbook("C:\\Users\\my\\Desktop\\a.xlsx");
        // 创建工作表对象
        XSSFSheet sheet = sheets.getSheetAt(0);
        // 获取表最后一行
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            // 获取每一行
            XSSFRow row = sheet.getRow(i);
            // 获取最后一列
            short lastCellNum = row.getLastCellNum();
            for (short j = 0; j < lastCellNum; j++) {
                // 获取每一列
                XSSFCell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }
        }

         sheets.close();
    }

    @Test
    public void test02() throws Exception {
        // 创建工作簿对象
        XSSFWorkbook sheets = new XSSFWorkbook("C:\\Users\\my\\Desktop\\a.xlsx");
        // 创建工作表对象
        XSSFSheet sheet = sheets.getSheet("sheet1");
        for (Row row : sheet) {
            // 获取每一行
            for (Cell cell : row) {
                // 获取每一列的数据
                String value = cell.getStringCellValue();
                System.out.println(value);
            }
        }
        sheets.close();
    }
    /**
     * excel写入数据
     */
    @Test
    public void test03() throws Exception {
        //在内存中创建一个Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建工作表，指定工作表名称
        XSSFSheet sheet = workbook.createSheet("sheet1");
        for (int i = 0; i < 2; i++) {
            XSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue("小明");
            row.createCell(1).setCellValue("18");
        }
        workbook.write(new FileOutputStream("C:\\Users\\my\\Desktop\\b.xlsx"));
        workbook.close();
    }
}
