package com.wyk.dsmanager.controller;

import com.wyk.dsmanager.entity.vo.DsDataResourceVo;
import com.wyk.dsmanager.service.IDataResourceService;
import com.wyk.dsmanager.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/01/02
 * \*
 * \* Description
 *
 * @author wyk
 */
@RestController
@RequestMapping("/api/data-resource")
public class DataResourceController {
    @Resource(name = "dataResourceService")
    IDataResourceService dataResourceService;

    @PutMapping("/save")
    public Result save(@RequestBody DsDataResourceVo vo){ return dataResourceService.save(vo);}

    @GetMapping(value = "/listAll")
    public Result listAll(){return dataResourceService.listAll();}

    @DeleteMapping(value = "/delete/{id}")
    public Result delete(@PathVariable(value = "id") String id){return dataResourceService.delete(id);}
}
