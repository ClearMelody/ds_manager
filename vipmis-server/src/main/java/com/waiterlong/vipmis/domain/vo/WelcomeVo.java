package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.Welcome;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import com.waiterlong.vipmis.utils.PinyinUtil;
import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/2
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Data
public class WelcomeVo {
    private String id;

    private String content;

    private Date createTime;
    
    public static List<WelcomeVo> convertWelcome(List<Welcome> welcomes){
        List<WelcomeVo> welcomeVos = Lists.newArrayList();
        for (Welcome welcome : welcomes) {
            welcomeVos.add(convertWelcome(welcome));
        }
        return welcomeVos;
    }

    public static WelcomeVo convertWelcome(Welcome welcome) {
        if (null == welcome) {
            return null;
        }
        WelcomeVo welcomeInfoVo = new WelcomeVo();
        AbstractMyBeanUtils.copyProperties(welcome, welcomeInfoVo);
        return welcomeInfoVo;
    }
}
