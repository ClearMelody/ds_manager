package com.wyk.dsmanager.controller;

import com.wyk.dsmanager.entity.vo.DsDatabaseVo;
import com.wyk.dsmanager.service.IDatabaseService;
import com.wyk.dsmanager.utils.Result;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/2
 * \*
 * \* Description
 *
 * @author wyk
 */
@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    @Resource(name = "databaseService")
    IDatabaseService databaseService;

    @PutMapping("/save")
    Result save(@RequestBody DsDatabaseVo vo){return databaseService.save(vo);}

    @GetMapping(value = "/getOne/{id}")
    public Result getOne(@PathVariable(value = "id") String id){return databaseService.getOne(id);}

    @PostMapping(value = "/listPage")
    public Result listPage(@RequestBody Map<String,Object> params,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        return databaseService.listPage(params, pageable);
    }

    @GetMapping(value = "/listAll")
    public Result listAll(){return databaseService.listAll();}

    @DeleteMapping(value = "/delete/{id}")
    public Result delete(@PathVariable(value = "id") String id){databaseService.delete(id);return Result.ok();}
}
