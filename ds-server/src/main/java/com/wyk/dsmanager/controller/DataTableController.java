package com.wyk.dsmanager.controller;

import com.wyk.dsmanager.entity.vo.DsDataTableVo;
import com.wyk.dsmanager.entity.vo.DsDatabaseVo;
import com.wyk.dsmanager.service.IDataTableService;
import com.wyk.dsmanager.utils.Result;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/7
 * \*
 * \* Description
 *
 * @author wyk
 */
@RestController
@RequestMapping("/api/data-table")
public class DataTableController {
    @Resource(name = "dataTableService")
    IDataTableService dataTableService;

    @PutMapping("/save")
    Result save(@RequestBody DsDataTableVo vo){return dataTableService.save(vo);}

    @GetMapping(value = "/getOne/{id}")
    public Result getOne(@PathVariable(value = "id") String id){return dataTableService.getOne(id);}

    @GetMapping(value = "/listAll")
    public Result listAll(@RequestParam(value = "resId") String resId){return dataTableService.listAll(resId);}

    @DeleteMapping(value = "/delete/{id}")
    public Result delete(@PathVariable(value = "id") String id){dataTableService.delete(id);return Result.ok();}
}
