package pers.yang.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yang.constant.MessageConstant;
import pers.yang.entity.Result;
import pers.yang.service.MemberService;
import pers.yang.service.ReportService;
import pers.yang.service.SetmealService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * @author yf
 * @date 2019/11/7
 */
@RestController
@RequestMapping("report")
public class ReportController {

    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    @Reference
    private ReportService reportService;

    @RequestMapping("getMemberReport")
    public Result getMemberReport() {
        try {
            Map map =  memberService.getMemberReport();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    @RequestMapping("getSetmealReport")
    public Result getSetmealReport() {
        try {
            Map map = setmealService.getSetmealReport();
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    @RequestMapping("getBusinessReport")
    public Result getBusinessReport() {
        try {
            Map map = reportService.getBusinessReport();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("exportBusinessReport")
    public void exportBusinessReport(HttpSession session, HttpServletResponse response) {
        try {
            // 获取模板绝对路径
            String templatePath = session.getServletContext().getRealPath("template").concat(File.separator).concat("report_template.xlsx");
            // 获取工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(templatePath);

            // 填充数据
            writeExcelDate(workbook);


            //设置响应的文件格式
            response.setContentType("application/vnd.ms-excel");
            //设置下载方式
            response.setHeader("content-Disposition","attachment;filename=report.xlsx");
            // 获取输出流
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 填充表格数据
     * @param workbook
     */
    private void writeExcelDate(XSSFWorkbook workbook) throws Exception {
        XSSFSheet sheet = workbook.getSheetAt(0);
        Map result = reportService.getBusinessReport();
        String reportDate = (String) result.get("reportDate");
        Long todayNewMember = (Long) result.get("todayNewMember");
        Long totalMember = (Long) result.get("totalMember");
        Long thisWeekNewMember = (Long) result.get("thisWeekNewMember");
        Long thisMonthNewMember = (Long) result.get("thisMonthNewMember");
        Long todayOrderNumber = (Long) result.get("todayOrderNumber");
        Long thisWeekOrderNumber = (Long) result.get("thisWeekOrderNumber");
        Long thisMonthOrderNumber = (Long) result.get("thisMonthOrderNumber");
        Long todayVisitsNumber = (Long) result.get("todayVisitsNumber");
        Long thisWeekVisitsNumber = (Long) result.get("thisWeekVisitsNumber");
        Long thisMonthVisitsNumber = (Long) result.get("thisMonthVisitsNumber");
        List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

        // 填充今天日期
        sheet.getRow(2).getCell(5).setCellValue(reportDate);
        // 填充今日新增会员数
        sheet.getRow(4).getCell(5).setCellValue(todayNewMember);
        // 填充总会员数
        sheet.getRow(4).getCell(7).setCellValue(totalMember);
        // 填充本周新增会员数
        sheet.getRow(5).getCell(5).setCellValue(thisWeekNewMember);
        // 填充本月新增会员数
        sheet.getRow(5).getCell(7).setCellValue(thisMonthNewMember);
        // 填充今日预约数
        sheet.getRow(7).getCell(5).setCellValue(todayOrderNumber);
        // 填充本周预约数
        sheet.getRow(8).getCell(5).setCellValue(thisWeekOrderNumber);
        // 填充本月预约数
        sheet.getRow(9).getCell(5).setCellValue(thisMonthOrderNumber);
        // 填充今日到诊数
        sheet.getRow(7).getCell(7).setCellValue(todayVisitsNumber);
        // 填充本周到诊数
        sheet.getRow(8).getCell(7).setCellValue(thisWeekVisitsNumber);
        // 填充本月到诊数
        sheet.getRow(9).getCell(7).setCellValue(thisMonthVisitsNumber);
        // 填充热门套餐
        int num = 12;
        for (Map map : hotSetmeal) {
            String name = (String) map.get("name");
            Long setmeal_count = (Long) map.get("setmeal_count");
            BigDecimal proportion = (BigDecimal) map.get("proportion");
            sheet.getRow(num).getCell(4).setCellValue(name);
            sheet.getRow(num).getCell(5).setCellValue(setmeal_count);
            sheet.getRow(num).getCell(6).setCellValue(proportion.toString());
            num++;
        }
    }

}
