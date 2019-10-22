package com.waiterlong.vipmis.service.impl;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.waiterlong.vipmis.assets.Constant;
import com.waiterlong.vipmis.domain.Welcome;
import com.waiterlong.vipmis.domain.vo.WelcomeVo;
import com.waiterlong.vipmis.repository.WelcomeRep;
import com.waiterlong.vipmis.service.IWelcomeService;
import com.waiterlong.vipmis.service.base.BaseServiceImpl;
import com.waiterlong.vipmis.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/22
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Service("iWelcomeService")
public class WelcomeServiceImpl extends BaseServiceImpl implements IWelcomeService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource(name = "welcomeRep")
    private WelcomeRep welcomeRep;

    @Override
    public Object uploadWelcomeImg(MultipartFile file) {
        String base64 = null;
        try {
            base64 = Base64.encode(file.getBytes());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (null == base64 || base64.isEmpty()) {
            return ResponseUtil.fail();
        } else {
            Welcome welcome = new Welcome();
            welcome.setContent("data:image/png;base64," + base64);
            welcome.setCreateTime(new Date());
            welcome = welcomeRep.saveAndFlush(welcome);
            if (welcomeRep.count() > Constant.MAX_WELCOME_COUNT) {
                welcomeRep.delete(welcomeRep.findTopByOrderByCreateTimeAsc());
            }
            return ResponseUtil.ok(WelcomeVo.convertWelcome(welcome));
        }
    }

    @Override
    public Object getLastWelcome() {
        return ResponseUtil.ok(WelcomeVo.convertWelcome(welcomeRep.findTopByOrderByCreateTimeDesc()));
    }
}