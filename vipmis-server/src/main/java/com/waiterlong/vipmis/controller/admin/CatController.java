package com.waiterlong.vipmis.controller.admin;

import com.google.common.collect.Maps;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.vo.CatLogVo;
import com.waiterlong.vipmis.domain.vo.CatVo;
import com.waiterlong.vipmis.service.ICatService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/12/14
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@RestController
@RequestMapping("/api/admin")
public class CatController {
    @Resource(name = "iCatService")
    private ICatService iCatService;

    @RequestMapping(value = "/cat/list", method = RequestMethod.GET)
    public Result listCatsByPage(
            @RequestParam(defaultValue = "") String cardCord,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("name", name);
        paramMap.put("cardCord", cardCord);
        return iCatService.listCatsByPage(paramMap, pageable);
    }

    @RequestMapping(value = "/cat/detail", method = RequestMethod.GET)
    public Result getCatDetail(
            @RequestParam String catId) {
        return iCatService.getCatDetail(catId);
    }

    @RequestMapping(value = "/cat/add", method = RequestMethod.POST)
    public Result addCat(
            @RequestBody CatVo catVo) {
        return iCatService.addCat(catVo);
    }

    @RequestMapping(value = "/cat/update", method = RequestMethod.POST)
    public Result updateCat(
            @RequestBody CatVo catVo) {
        return iCatService.updateCat(catVo);
    }

    @RequestMapping(value = "/cat/delete", method = RequestMethod.POST)
    public Result deleteCat(
            @RequestBody CatVo catVo) {
        return iCatService.deleteCat(catVo);
    }

    @RequestMapping(value = "/cat/log/add", method = RequestMethod.POST)
    public Result addCatLog(
            @RequestBody CatLogVo catLogVo) {
        return iCatService.addCatLog(catLogVo);
    }

    @RequestMapping(value = "/cat/log/delete", method = RequestMethod.POST)
    public Result deleteCatLog(
            @RequestBody CatLogVo catLogVo) {
        return iCatService.deleteCatLog(catLogVo);
    }

    @RequestMapping(value = "/cat/log/list", method = RequestMethod.GET)
    public Result listCatLogsByPage(
            @RequestParam String catId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("catId", catId);
        return iCatService.listCatLogsByPage(paramMap, pageable);
    }
}
