package com.wyk.dsmanager.controller;

import com.google.common.collect.Maps;
import com.wyk.dsmanager.entity.vo.DsDataTableVo;
import com.wyk.dsmanager.entity.vo.DsDictionaryVo;
import com.wyk.dsmanager.service.IDictionaryService;
import com.wyk.dsmanager.utils.Result;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/13
 * \*
 * \* Description
 *
 * @author wyk
 */
@RestController
@RequestMapping("/api/dictionary")
public class DictionaryController {
    @Resource(name = "dictionaryService")
    IDictionaryService dictionaryService;

    @PutMapping("/save")
    Result save(@RequestBody DsDictionaryVo vo){return dictionaryService.save(vo);}

    @GetMapping(value = "/getOne/{id}")
    public Result getOne(@PathVariable(value = "id") String id){return dictionaryService.getOne(id);}

    @GetMapping(value = "/listAll")
    public Result listAll(){return dictionaryService.listAll();}

    @GetMapping(value = "/listPage")
    public Result listPage(
            @RequestParam(value = "typeId", defaultValue = "") String typeId,
            @RequestParam(value = "code", defaultValue = "") String code,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String,String> params = Maps.newHashMap();
        params.put("typeId", typeId);
        params.put("code", code);
        params.put("name", name);
        return dictionaryService.listPage(params, pageable);
    }

    @DeleteMapping(value = "/delete/{id}")
    public Result delete(@PathVariable(value = "id") String id){dictionaryService.delete(id);return Result.ok();}
}
