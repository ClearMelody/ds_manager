package com.wyk.dsmanager.controller;

import com.wyk.dsmanager.entity.vo.DsDictionaryTypeVo;
import com.wyk.dsmanager.service.IDictionaryTypeService;
import com.wyk.dsmanager.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/13
 * \*
 * \* Description
 *
 * @author wyk
 */
@RestController
@RequestMapping("/api/dictionary-type")
public class DictionaryTypeController {
    @Resource(name = "dictionaryTypeService")
    IDictionaryTypeService dictionaryTypeService;

    @PutMapping("/save")
    Result save(@RequestBody DsDictionaryTypeVo vo){return dictionaryTypeService.save(vo);}

    @GetMapping(value = "/getOne/{id}")
    public Result getOne(@PathVariable(value = "id") String id){return dictionaryTypeService.getOne(id);}

    @GetMapping(value = "/listAll")
    public Result listAll(){return dictionaryTypeService.listAll();}

    @DeleteMapping(value = "/delete/{id}")
    public Result delete(@PathVariable(value = "id") String id){dictionaryTypeService.delete(id);return Result.ok();}
}
