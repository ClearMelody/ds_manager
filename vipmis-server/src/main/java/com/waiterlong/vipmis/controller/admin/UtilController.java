package com.waiterlong.vipmis.controller.admin;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.service.IUtilService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/12/14
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@RestController
@RequestMapping("/api/admin")
public class UtilController {
    @Resource(name = "iUtilService")
    private IUtilService iUtilService;

    @RequestMapping(value = "/img/2/base64", method = RequestMethod.POST)
    public Result uploadWelcomeImg(MultipartFile file){
        return iUtilService.img2Base64(file);
    }
}
