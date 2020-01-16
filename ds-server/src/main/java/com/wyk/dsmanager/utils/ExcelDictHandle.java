package com.wyk.dsmanager.utils;

import cn.afterturn.easypoi.handler.inter.IExcelDataHandler;
import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import com.google.common.collect.Maps;
import com.wyk.dsmanager.service.IDictionaryService;
import org.springframework.context.annotation.Import;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/15
 * \*
 * \* Description
 *
 * @author wyk
 */
public class ExcelDictHandle implements IExcelDictHandler{
    private static Map<String,Map<String,String>> dictMapCache = Maps.newHashMap();
    @Override
    public String toName(String dict, Object obj, String name, Object value) {
        Map<String,String> dictMap = dictMapCache.get(dict);
        if(dictMap == null){
            IDictionaryService dictionaryService = (IDictionaryService) SpringBeanUtils.getBean("dictionaryService");
            dictMap = dictionaryService.listByTypeId(dict);
            dictMapCache.put(dict, dictMap);
        }
        if(dictMap == null){
            return null;
        }else{
            return dictMap.get(value);
        }
    }

    @Override
    public String toValue(String dict, Object obj, String name, Object value) {
        return null;
    }
}
