package com.cao.dao;

import com.cao.POJO.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    public void add(Setmeal setmeal);
    public void addSetmealAndCheckgroup(Map<String,Integer> map);
    public Page<Setmeal> selectPage(@Param("queryString") String queryString);
    public Setmeal selectById(Integer id);
    public List<Integer> findCheckGroupIdsBySetMealId(Integer id);
    public void updateSetmeal(Setmeal setmeal);
    public void deleteAssociation(Integer id);
    public void deleteById(Integer id);
    public List<Setmeal> getAllSetmeal();
    public Setmeal findById(Integer id);
    public List<Map<String,Object>> findSetmealCount();
}
