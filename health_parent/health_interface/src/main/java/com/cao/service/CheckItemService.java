package com.cao.service;

import com.cao.POJO.CheckItem;
import com.cao.entity.PageResult;
import com.cao.entity.QueryPageBean;

import java.util.List;

public interface CheckItemService {
    public void add(CheckItem checkItem);
    public PageResult PageQuery(QueryPageBean queryPageBean);
    public void deleteById(Integer id);
    public CheckItem findById(Integer id);
    public void edit(CheckItem checkItem);
    public List<CheckItem> findAll();
}
