package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.vo.CatLogVo;
import com.waiterlong.vipmis.domain.vo.CatVo;
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

    Result addCat(CatVo catVo);

    Result updateCat(CatVo catVo);

    Result deleteCat(CatVo catVo);

    Result addCatLog(CatLogVo catLogVo);

    Result deleteCatLog(CatLogVo catLogVo);
}
