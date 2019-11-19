package com.waiterlong.vipmis.controller.admin;

import com.google.common.collect.Maps;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.vo.LabelVo;
import com.waiterlong.vipmis.domain.vo.UserInfoVo;
import com.waiterlong.vipmis.domain.vo.UserVo;
import com.waiterlong.vipmis.service.ILabelService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/11/18
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@RestController
@RequestMapping("/api/admin")
public class LabelController {
    @Resource(name = "iLabelService")
    private ILabelService iLabelService;

    @RequestMapping(value = "/label/add", method = RequestMethod.POST)
    public Result addLabel(@RequestBody LabelVo labelVo) {
        return iLabelService.addLabel(labelVo);
    }

    @RequestMapping(value = "/label/edit", method = RequestMethod.POST)
    public Result editLabel(@RequestBody LabelVo labelVo) {
        return iLabelService.editLabel(labelVo);
    }

    @RequestMapping(value = "/label/delete", method = RequestMethod.POST)
    public Result delLabel(@RequestBody LabelVo labelVo) {
        return iLabelService.delLabel(labelVo);
    }

    @RequestMapping(value = "/label/list", method = RequestMethod.GET)
    public Result listLabelsByPage(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("name", name);
        return iLabelService.listLabelsByPage(paramMap, pageable);
    }

    @RequestMapping(value = "/label/all", method = RequestMethod.GET)
    public Result listAllLabels() {
        return iLabelService.listAllLabels();
    }

    @RequestMapping(value = "/user/label/change", method = RequestMethod.POST)
    public Result changeUserLabel(@RequestBody UserInfoVo userInfoVo) {
        return iLabelService.changeUserLabel(userInfoVo);
    }
}
