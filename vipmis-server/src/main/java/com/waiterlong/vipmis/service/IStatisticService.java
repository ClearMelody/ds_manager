package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.service.base.IBaseService;

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
public interface IStatisticService extends IBaseService {
    Result getProductProportion(String start, String end);

    Result getUserGrowth(String start, String end);
}