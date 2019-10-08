package com.waiterlong.vipmis.service;

import com.google.common.collect.Maps;
import com.waiterlong.vipmis.assets.Constant;
import com.waiterlong.vipmis.assets.WeiXinApiUrl;
import com.waiterlong.vipmis.domain.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
    @Resource(name = "iUserService")
    private IUserService userService;

    @Test
    public void findUserInfoById() {
        Map result = (Map)userService.findUserInfoById(Constant.TEST_USER_ID);
        System.out.println(result.get(WeiXinApiUrl.ERRMSG_KEY));
        System.out.println(result.get(WeiXinApiUrl.DATA_KEY));
    }

    @Test
    public void addUser() {
        UserVo userVo = new UserVo();
        userVo.setPhone("18627082367");
        userVo.setPassword("1234");
        userVo.setRealName("waiterlong");

        Map result = (Map)userService.addUser(userVo);
        System.out.println(result.get(WeiXinApiUrl.ERRMSG_KEY));
        System.out.println(result.get(WeiXinApiUrl.DATA_KEY));
    }

    @Test
    public void listUsersByPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("realName", "ter");
        Map result = (Map)userService.listUsersByPage(paramMap, pageable);
        System.out.println(result.get(WeiXinApiUrl.ERRMSG_KEY));
        System.out.println(result.get(WeiXinApiUrl.DATA_KEY));
    }

    @Test
    public void test() {
    }
}
