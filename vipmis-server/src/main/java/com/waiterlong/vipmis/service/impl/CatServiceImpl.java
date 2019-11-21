package com.waiterlong.vipmis.service.impl;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.Cat;
import com.waiterlong.vipmis.domain.wxvo.WxCatVo;
import com.waiterlong.vipmis.repository.CatRep;
import com.waiterlong.vipmis.service.ICatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.text.html.Option;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/11/21
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Service("iCatService")
public class CatServiceImpl implements ICatService {
    Logger logger = LoggerFactory.getLogger(CatServiceImpl.class);

    @Resource(name = "catRep")
    private CatRep catRep;

    @Override
    public Result getCatDetail(@NotNull String catId) {
        if (catId.isEmpty()) {
            return Result.badArgumentValue();
        }
        Optional<Cat> catOptional = catRep.findById(catId);
        if (!catOptional.isPresent()) {
            return Result.badArgumentValue();
        }
        return Result.ok(WxCatVo.convertCat(catOptional.get()));
    }
}
