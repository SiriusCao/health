package com.cao.service;

import com.cao.POJO.CheckGroup;
import com.cao.POJO.Setmeal;
import com.cao.entity.PageResult;
import com.cao.entity.QueryPageBean;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    public void add(Setmeal setmeal, Integer[] checkgroupIds);
    public PageResult queryPage(QueryPageBean pageBean);
    public Setmeal queryById(Integer id);
    public List<Integer> findCheckGroupIdsBySetMealId(Integer id);
    public void edit(Setmeal setmeal,Integer[] checkgroupIds);
    public void removeById(Integer id);
    public List<Setmeal> getAllSetmeal();
    public Setmeal findById(Integer id);
    public List<Map<String,Object>> findSetmealCount();
}
