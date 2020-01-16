package com.wyk.dsmanager.service;

import com.wyk.dsmanager.entity.vo.DsDataResourceVo;
import com.wyk.dsmanager.utils.Result;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/01/02
 * \*
 * \* Description
 *
 * @author wyk
 */
public interface IDataResourceService {
    /**
     * 保存 or 更新
     * @param vo
     * @return
     */
    Result save(DsDataResourceVo vo);

    /**
     * 查询所有
     * @return
     */
    Result listAll();

    /**
     * 删除
     * @param id
     * @return
     */
    Result delete(String id);
}
