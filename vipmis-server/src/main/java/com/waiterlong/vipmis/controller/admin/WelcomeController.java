package com.waiterlong.vipmis.controller.admin;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.service.IWelcomeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/22
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@RestController
@RequestMapping("/api/admin")
public class WelcomeController {
    @Resource(name = "iWelcomeService")
    private IWelcomeService iWelcomeService;

    @RequestMapping(value = "/welcome/img/upload", method = RequestMethod.POST)
    public Result uploadWelcomeImg(MultipartFile file){
        return iWelcomeService.uploadWelcomeImg(file);
    }

    @RequestMapping(value = "/welcome/last", method = RequestMethod.GET)
    public Result getLastWelcome(){
        return iWelcomeService.getLastWelcome();
    }
}
