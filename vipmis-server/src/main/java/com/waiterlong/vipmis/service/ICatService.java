package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.component.Result;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/11/21
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
public interface ICatService {
    Result getCatDetail(String catId);

    Result listCatsByPage(Map<String, Object> paramMap, Pageable pageable);
}
