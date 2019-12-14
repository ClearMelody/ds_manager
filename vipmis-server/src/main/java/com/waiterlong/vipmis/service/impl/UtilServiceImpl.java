package com.waiterlong.vipmis.service.impl;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.service.IUtilService;
import com.waiterlong.vipmis.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/12/14
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Slf4j
@Service("iUtilService")
public class UtilServiceImpl extends BaseServiceImpl implements IUtilService {
    @Override
    public Result img2Base64(MultipartFile file) {
        String base64 = null;
        try {
            base64 = Base64Utils.encodeToString(file.getBytes());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (null == base64 || base64.isEmpty()) {
            return Result.fail();
        } else {
            return Result.ok("data:image/png;base64," + base64);
        }
    }
}
