package com.wonder4work.service.impl;

import com.github.pagehelper.PageInfo;
import com.wonder4work.util.PagedGridResult;

import java.util.List;

/**
 * @since 1.0.0 2020/5/18
 */
public class BaseService{

    public static PagedGridResult setterPagedGrid(Integer page, List<?> list) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

}
