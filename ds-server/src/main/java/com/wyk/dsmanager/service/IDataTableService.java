package com.wyk.dsmanager.service;

import com.wyk.dsmanager.entity.vo.DsDataTableVo;
import com.wyk.dsmanager.utils.Result;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/7
 * \*
 * \* Description
 *
 * @author wyk
 */
public interface IDataTableService {
    /**
     * 保存 or 更新
     * @param vo
     * @return
     */
    Result save(DsDataTableVo vo);

    /**
     * 查询实体
     * @param id
     * @return
     */
    Result getOne(String id);

    /**
     * 分页查询
     * @param paramMap
     * @param pageable
     * @return
     */
    Result listPage(Map<String, Object> paramMap, Pageable pageable);

    /**
     * 查询所有
     * @return
     */
    Result listAll(String resId);

    /**
     * 删除
     * @param id
     */
    void delete(String id);
}
