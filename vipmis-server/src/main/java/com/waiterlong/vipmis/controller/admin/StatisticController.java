package com.waiterlong.vipmis.controller.admin;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.service.IStatisticService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/11/16
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@RestController
@RequestMapping("/api/admin")
public class StatisticController {
    @Resource(name = "iStatisticService")
    private IStatisticService iStatisticService;

    @RequestMapping(value = "/statistic/product/proportion", method = RequestMethod.GET)
    public Result getProductProportion(
            @RequestParam String start,
            @RequestParam String end) {
        return iStatisticService.getProductProportion(start, end);
    }

    @RequestMapping(value = "/statistic/user/growth", method = RequestMethod.GET)
    public Result getUserGrowth(
            @RequestParam String start,
            @RequestParam String end) {
        return iStatisticService.getUserGrowth(start, end);
    }
}
