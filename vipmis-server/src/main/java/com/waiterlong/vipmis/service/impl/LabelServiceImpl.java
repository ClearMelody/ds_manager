package com.waiterlong.vipmis.service.impl;

import com.waiterlong.vipmis.component.PageResult;
import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.Label;
import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.domain.vo.LabelVo;
import com.waiterlong.vipmis.repository.LabelRep;
import com.waiterlong.vipmis.repository.UserRep;
import com.waiterlong.vipmis.service.ILabelService;
import com.waiterlong.vipmis.service.base.BaseServiceImpl;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
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
 * \* Date: 2019/11/18
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Service("iLabelService")
public class LabelServiceImpl extends BaseServiceImpl implements ILabelService {
    @Resource(name = "labelRep")
    private LabelRep labelRep;
    @Resource(name = "userRep")
    private UserRep userRep;

    @Override
    public Result addLabel(@NotNull LabelVo labelVo) {
        if (null != labelVo.getId() || !labelVo.getId().trim().isEmpty()) {
            return Result.badArgumentValue();
        }
        Label label = new Label();
        AbstractMyBeanUtils.copyProperties(labelVo, label);
        label.setCreateTime(new Date());
        label = labelRep.save(label);
        return Result.ok(LabelVo.convertLabel(label));
    }

    @Override
    public Result editLabel(@NotNull LabelVo labelVo) {
        if (null == labelVo.getId() || null == labelVo.getName() || labelVo.getId().trim().isEmpty() || labelVo.getName().trim().isEmpty()) {
            return Result.badArgumentValue();
        }
        Optional<Label> labelOptional = labelRep.findById(labelVo.getId().trim());
        if (!labelOptional.isPresent()) {
            return Result.badArgumentValue();
        }
        Label label = labelOptional.get();
        label.setName(labelVo.getName());

        label = labelRep.save(label);
        return Result.ok(LabelVo.convertLabel(label));
    }

    @Override
    public Result delLabel(@NotNull LabelVo labelVo) {
        if (labelVo.getId().trim().isEmpty()) {
            return Result.badArgumentValue();
        }
        Optional<Label> labelOptional = labelRep.findById(labelVo.getId().trim());
        if (!labelOptional.isPresent()) {
            return Result.badArgumentValue();
        }
        Label label = labelOptional.get();
        List<User> users = label.getUsers();
        for (User user : users) {
            user.setLabel(null);
        }
        userRep.saveAll(users);

        labelRep.delete(label);
        return Result.ok();
    }

    @Override
    public Result listLabelsByPage(@NotNull Map<String, Object> paramMap, @NotNull Pageable pageable) {
        Page<Label> labelPage = labelRep.findByNameIsContainingOrderByCreateTimeDesc((String)paramMap.get("name"), pageable);
        return Result.ok(PageResult.setPageResult(pageable, labelPage.getTotalElements(), LabelVo.convertLabel(labelPage.getContent())));
    }
}
