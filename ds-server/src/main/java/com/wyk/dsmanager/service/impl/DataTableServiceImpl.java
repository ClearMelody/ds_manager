package com.wyk.dsmanager.service.impl;

import com.google.common.collect.Lists;
import com.wyk.dsmanager.entity.DsDataResource;
import com.wyk.dsmanager.entity.DsDataTable;
import com.wyk.dsmanager.entity.DsDataTableColumn;
import com.wyk.dsmanager.entity.DsDatabase;
import com.wyk.dsmanager.entity.vo.DsDataTableColumnVo;
import com.wyk.dsmanager.entity.vo.DsDataTableVo;
import com.wyk.dsmanager.entity.vo.DsDatabaseVo;
import com.wyk.dsmanager.repository.DataResourceRep;
import com.wyk.dsmanager.repository.DataTableColumnRep;
import com.wyk.dsmanager.repository.DataTableRep;
import com.wyk.dsmanager.repository.DatabaseRep;
import com.wyk.dsmanager.service.IDataTableService;
import com.wyk.dsmanager.utils.AbstractMyBeanUtils;
import com.wyk.dsmanager.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.dialect.Database;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/7
 * \*
 * \* Description
 *
 * @author wyk
 */
@Service("dataTableService")
public class DataTableServiceImpl implements IDataTableService {
    @Resource(name = "dataTableRep")
    DataTableRep dataTableRep;
    @Resource(name = "databaseRep")
    DatabaseRep databaseRep;
    @Resource(name = "dataResourceRep")
    DataResourceRep dataResourceRep;
    @Resource(name = "dataTableColumnRep")
    DataTableColumnRep dataTableColumnRep;

    @Override
    public Result save(DsDataTableVo vo) {
        String id = vo.getId();
        DsDataTable entity = new DsDataTable();
        entity.setUpdateTime(LocalDateTime.now());
        if(StringUtils.isNotBlank(id)){
            entity = dataTableRep.getOne(id);
            AbstractMyBeanUtils.copyProperties(vo, entity);
            List<DsDataTableColumn> columns = Lists.newLinkedList();
            for(DsDataTableColumnVo columnVo : vo.getColumns()){
                DsDataTableColumn column = new DsDataTableColumn();
                AbstractMyBeanUtils.copyProperties(columnVo, column);
                column.setDsDataTable(entity);
                columns.add(column);
            }
            entity.setColumns(columns);
            dataTableRep.save(entity);
        }else{
            String dbId = vo.getDbId();
            String resId = vo.getResId();
            if(StringUtils.isNotBlank(dbId)){
                DsDatabase database = databaseRep.getOne(dbId);
                entity.setDsDatabase(database);
            }
            if(StringUtils.isNotBlank(resId)){
                DsDataResource dataResource = dataResourceRep.getOne(resId);
                entity.setDsDataResource(dataResource);
            }
            AbstractMyBeanUtils.copyProperties(vo, entity);
            List<DsDataTableColumn> columns = Lists.newLinkedList();
            for(DsDataTableColumnVo columnVo : vo.getColumns()){
                DsDataTableColumn column = new DsDataTableColumn();
                AbstractMyBeanUtils.copyProperties(columnVo, column);
                column.setDsDataTable(entity);
                columns.add(column);
            }
            entity.setColumns(columns);
            entity.setCreateTime(LocalDateTime.now());
            dataTableRep.save(entity);
        }
        return Result.ok();
    }

    @Override
    public Result getOne(String id) {
        DsDataTable entity = dataTableRep.getOne(id);
        DsDataTableVo vo = DsDataTableVo.convert(entity);
        return Result.ok(vo);
    }

    @Override
    public Result listPage(Map<String, Object> paramMap, Pageable pageable) {
        return null;
    }

    @Override
    public Result listAll(String resId) {
        List<DsDataTableVo> list = dataTableRep.findByDsDataResource_IdOrderByCreateTimeDesc(resId);
        return Result.ok(list);
    }

    @Override
    public void delete(String id) {
        dataTableRep.deleteById(id);
    }
}
