package com.wyk.dsmanager.service;

import com.wyk.dsmanager.entity.vo.DsDictionaryTypeVo;
import com.wyk.dsmanager.utils.Result;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/13
 * \*
 * \* Description
 *
 * @author wyk
 */
public interface IDictionaryTypeService {
    /**
     * 保存 or 更新
     * @param vo
     * @return
     */
    Result save(DsDictionaryTypeVo vo);
    /**
     * 查询实体
     * @param id
     * @return
     */
    Result getOne(String id);
    /**
     * 删除
     * @param id
     */
    void delete(String id);
    /**
     * 查询所有
     * @return
     */
    Result listAll();

}
