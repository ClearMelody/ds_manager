package com.waiterlong.vipmis.controller.admin;

import com.google.common.collect.Maps;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.service.ICatService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Result listDepositLogByPage(
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
}
