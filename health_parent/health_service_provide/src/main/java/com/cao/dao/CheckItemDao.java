package com.cao.dao;

import com.cao.POJO.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface CheckItemDao {
    public void add(CheckItem checkItem);
    public Page<CheckItem> selectByCondition(String queryString);
    public Integer findBelongCountById(Integer id);
    public void deleteById(Integer id);
    public CheckItem queryById(Integer id);
    public void edit(CheckItem checkItem);
    public List<CheckItem> findAll();
}
