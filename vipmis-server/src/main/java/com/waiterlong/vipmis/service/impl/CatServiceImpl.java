package com.waiterlong.vipmis.service.impl;

import com.waiterlong.vipmis.component.PageResult;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.Cat;
import com.waiterlong.vipmis.domain.CatLog;
import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.domain.vo.CatLogVo;
import com.waiterlong.vipmis.domain.vo.CatVo;
import com.waiterlong.vipmis.domain.wxvo.WxCatVo;
import com.waiterlong.vipmis.repository.CatLogRep;
import com.waiterlong.vipmis.repository.CatRep;
import com.waiterlong.vipmis.repository.UserRep;
import com.waiterlong.vipmis.service.ICatService;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    @Resource(name = "catLogRep")
    private CatLogRep catLogRep;
    @Resource(name = "userRep")
    private UserRep userRep;

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

    @Override
    public Result listCatsByPage(Map<String, Object> paramMap, Pageable pageable) {
        Page<Cat> catPage = catRep.findByNameIsContainingAndUser_CardCordIsContaining((String)paramMap.get("name"), (String)paramMap.get("cardCord"), pageable);
        return Result.ok(PageResult.setPageResult(pageable, catPage.getTotalElements(), CatVo.convertCat(catPage.getContent())));
    }

    @Override
    public Result addCat(@NotNull CatVo catVo) {
        if (null != catVo.getId() || null == catVo.getUserInfoVo() || null == catVo.getUserInfoVo().getId() || catVo.getUserInfoVo().getId().trim().isEmpty()) {
            return Result.badArgumentValue();
        }
        Optional<User> userOptional = userRep.findById(catVo.getUserInfoVo().getId().trim());
        if (!userOptional.isPresent()) {
            return Result.badArgumentValue();
        }
        Cat cat = new Cat();
        AbstractMyBeanUtils.copyProperties(catVo, cat);
        cat.setUser(userOptional.get());
        cat = catRep.save(cat);
        return Result.ok(CatVo.convertCat(cat));
    }

    @Override
    public Result updateCat(@NotNull CatVo catVo) {
        if (null == catVo.getId() || catVo.getId().trim().isEmpty()) {
            return Result.badArgumentValue();
        }
        Optional<Cat> catOptional = catRep.findById(catVo.getId().trim());
        if (!catOptional.isPresent()) {
            return Result.badArgumentValue();
        }
        Cat catTt = catOptional.get();
        Cat cat = new Cat();
        AbstractMyBeanUtils.copyProperties(catVo, cat);
        cat.setUser(catTt.getUser());
        cat.setId(catTt.getId());
        cat = catRep.save(cat);
        return Result.ok(CatVo.convertCat(cat));
    }

    @Override
    public Result deleteCat(@NotNull CatVo catVo) {
        if (null == catVo.getId() || catVo.getId().trim().isEmpty()) {
            return Result.badArgumentValue();
        }
        Optional<Cat> catOptional = catRep.findById(catVo.getId().trim());
        if (!catOptional.isPresent()) {
            return Result.badArgumentValue();
        }
        Cat cat = catOptional.get();
        List<CatLog> catLogList = cat.getCatLogs();
        catLogRep.deleteAll(catLogList);

        catRep.delete(cat);

        return Result.ok();
    }

    @Override
    public Result addCatLog(CatLogVo catLogVo) {
        if (null != catLogVo.getId() || null == catLogVo.getCatVo() || null == catLogVo.getCatVo().getId() || catLogVo.getCatVo().getId().trim().isEmpty()) {
            return Result.badArgumentValue();
        }
        Optional<Cat> catOptional = catRep.findById(catLogVo.getCatVo().getId().trim());
        if (!catOptional.isPresent()) {
            return Result.badArgumentValue();
        }
        CatLog catLog = new CatLog();
        AbstractMyBeanUtils.copyProperties(catLogVo, catLog);
        catLog.setCat(catOptional.get());
        catLog.setCreateTime(new Date());
        catLog = catLogRep.save(catLog);
        return Result.ok(CatLogVo.convertCatLog(catLog));
    }

    @Override
    public Result deleteCatLog(CatLogVo catLogVo) {
        if (null == catLogVo.getId() || catLogVo.getId().trim().isEmpty()) {
            return Result.badArgumentValue();
        }
        Optional<CatLog> catLogOptional = catLogRep.findById(catLogVo.getId().trim());
        if (!catLogOptional.isPresent()) {
            return Result.badArgumentValue();
        }
        CatLog catLog = catLogOptional.get();

        catLogRep.delete(catLog);

        return Result.ok();
    }

    @Override
    public Result listCatLogsByPage(Map<String, Object> paramMap, Pageable pageable) {
        Page<CatLog> catLogPage = catLogRep.findByCat_IdIsOrderByCreateTimeDesc((String)paramMap.get("catId"), pageable);
        return Result.ok(PageResult.setPageResult(pageable, catLogPage.getTotalElements(), CatLogVo.convertCatLog(catLogPage.getContent())));
    }
}
