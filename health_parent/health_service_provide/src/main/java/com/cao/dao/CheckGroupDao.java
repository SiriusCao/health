package com.cao.dao;

import com.cao.POJO.CheckGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    public void add(CheckGroup checkGroup);
    public void addCheckGroupAndCheckItem(Map<String,Integer> map);
    public Page<CheckGroup> selectByCondition(String queryString);
    public CheckGroup selectById(Integer id);
    public List<Integer> selectCheckItemIdsByCheckGroupId(Integer id);
    public void updateCheckGroup(CheckGroup checkGroup);
    public void deleteAssociation(Integer checkGroupId);
    public Integer findBelongCountById(Integer id);
    public void deleteById(Integer id);
    public List<CheckGroup> selectAll();
}
