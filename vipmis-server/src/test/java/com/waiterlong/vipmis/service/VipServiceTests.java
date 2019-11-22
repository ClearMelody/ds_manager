package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.domain.wxvo.VipVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/11/23
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VipServiceTests {
    @Resource(name = "iVipService")
    private IVipService iVipService;

    @Test
    public void addVipInfo() {
        VipVo vipVo = new VipVo();
        vipVo.setName("测试");
        vipVo.setOpenid("12131323");
        Result result = iVipService.addVipInfo(vipVo);
        System.out.println(result.getErrmsg());
        System.out.println(result.getData());
        Assert.assertNotNull(result.getData());
    }
}
