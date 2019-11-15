package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.service.base.IBaseService;
import org.springframework.web.multipart.MultipartFile;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/22
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
public interface IWelcomeService extends IBaseService {
    /**
     * 上传图片
     * @param file MultipartFile
     * @return Object
     */
    Result uploadWelcomeImg(MultipartFile file);

    /**
     * 获取最后一个Welcome
     * @return Object
     */
    Result getLastWelcome();
}
