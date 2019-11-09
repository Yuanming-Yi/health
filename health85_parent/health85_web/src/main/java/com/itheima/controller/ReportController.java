package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public Result getMemberReport(@RequestParam("beginMonth") String beginMonth, @RequestParam("endMonth") String endMonth){

        try {
            Map map = memberService.getMemberReport(beginMonth,endMonth);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }

    }

    /**
     * 查询套餐数据
     */
    @RequestMapping("getSetmealReport")
    public Result getSetmealReport(){

        try {
            Map map = setmealService.getSetmealReport();
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }

    }

    /**
     * 查询运营数据
     */
    @RequestMapping("getBusinessReport")
    public Result getBusinessReport(){

        try {
            Map  map = reportService.getBusinessReport();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }

    /**
     * 导出excelt
     */

    @RequestMapping("exportBusinessReport")
    public void exportBusinessReport(HttpSession session, HttpServletResponse response){


        try {
            //获取模板的路径
//        String realPath = session.getServletContext().getRealPath("template/report_template.xlsx");
            String templatePath = session.getServletContext().getRealPath("template")+ File.separator+"report_template.xlsx";


            //设置响应的文件格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            //设置下载方式
            response.setHeader("content-Disposition","attachment;filename=report.xlsx");

            Map result = reportService.getBusinessReport();
            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");


            XSSFWorkbook workbook = new XSSFWorkbook(templatePath);
            //获取工作表sheet
            XSSFSheet sheet = workbook.getSheetAt(0);
            //获取行
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);
            row.getCell(7).setCellValue(totalMember);

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);
            row.getCell(7).setCellValue(thisMonthNewMember);

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);
            row.getCell(7).setCellValue(todayVisitsNumber);

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);
            row.getCell(7).setCellValue(thisWeekVisitsNumber);

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            row.getCell(7).setCellValue(thisMonthVisitsNumber);

            int num = 12;
            for (Map map : hotSetmeal) {

                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(num);
                row.getCell(4).setCellValue(name);
                row.getCell(5).setCellValue(setmeal_count);
                row.getCell(6).setCellValue(proportion.toString());

                num++;
            }

            ServletOutputStream outputStream = response.getOutputStream();

            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workbook.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /***
     * 会员性别统计饼图
     */
    @RequestMapping("getReportMemberSex")
    public Result getReportMemberSex(){
        try {
            Map map=memberService.getReportMemberSex();
            return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    /***
     * 会员年龄统计饼图
     */
    @RequestMapping("getReportMemberAge")
    public Result getReportMemberAge(){
        try {
            Map map=memberService.getReportMemberAge();
            return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }



}
