package com.cao.service;

import com.cao.POJO.CheckGroup;
import com.cao.entity.PageResult;
import com.cao.entity.QueryPageBean;

import java.util.List;

public interface CheckGroupService {
    public void add(CheckGroup checkGroup,Integer[] checkitemIds);
    public PageResult queryPage(QueryPageBean queryPageBean);
    public CheckGroup queryById(Integer id);
    public List<Integer> queryCheckItemIdsByCheckGroupId(Integer id);
    public void edit(CheckGroup checkGroup,Integer[] checkitemIds);
    public void removeById(Integer id);
    public List<CheckGroup> queryAll();
}
