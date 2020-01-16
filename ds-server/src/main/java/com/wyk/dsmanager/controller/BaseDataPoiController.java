package com.wyk.dsmanager.controller;

import com.google.common.collect.Maps;
import com.wyk.dsmanager.service.IBaseDataSourceService;
import com.wyk.dsmanager.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.NumberFormat;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/13
 * \*
 * \* Description
 *
 * @author wyk
 */
@Controller
@RequestMapping("/api/base-db")
public class BaseDataPoiController {
    @Resource(name = "baseDbSourceService")
    IBaseDataSourceService baseDbSourceService;

    @GetMapping("/export-table-data")
    public void exportTableData(@RequestParam(value = "tbId") String tbId,
                                @RequestParam(value="order", defaultValue = "") String order,
                                HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> params = Maps.newHashMap();
        baseDbSourceService.exportTableData(tbId, params, request, response);
    }

    @GetMapping("/export-table-data-progress")
    @ResponseBody
    public Result exportTableDataProgress(HttpServletRequest request, HttpServletResponse response){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        Object percent =  request.getSession().getAttribute("percent");
        if(percent == null){
            percent = 0;
        }
        return Result.ok(numberFormat.format(percent));
    }
}
