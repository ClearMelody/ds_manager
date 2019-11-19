package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.vo.LabelVo;
import com.waiterlong.vipmis.domain.vo.UserInfoVo;
import com.waiterlong.vipmis.service.base.IBaseService;
import org.springframework.data.domain.Pageable;

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
public interface ILabelService extends IBaseService {
    Result addLabel(LabelVo labelVo);

    Result editLabel(LabelVo labelVo);

    Result delLabel(LabelVo labelVo);

    Result listLabelsByPage(Map<String, Object> paramMap, Pageable pageable);

    Result listAllLabels();

    Result changeUserLabel(UserInfoVo userInfoVo);
}
