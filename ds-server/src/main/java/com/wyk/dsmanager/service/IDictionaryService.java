package com.wyk.dsmanager.service;

import com.wyk.dsmanager.entity.vo.DsDictionaryVo;
import com.wyk.dsmanager.utils.Result;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/13
 * \*
 * \* Description
 *
 * @author wyk
 */
public interface IDictionaryService {
    /**
     * 保存 or 更新
     * @param vo
     * @return
     */
    Result save(DsDictionaryVo vo);
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

    /**
     * 分页查询
     * @param params
     * @param pageable
     * @return
     */
    Result listPage(Map<String,String> params, Pageable pageable);

    Map<String,String> listByTypeId(String typeId);
}
