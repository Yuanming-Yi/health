package pers.yang.service;

import java.util.Map;

/**
 * @author yf
 * @date 2019/11/8
 */
public interface ReportService {
    /**
     * 运营数据统计
     * @return
     */
    Map getBusinessReport() throws Exception;
}
