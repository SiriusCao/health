package com.cao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.cao.POJO.CheckGroup;
import com.cao.dao.CheckGroupDao;
import com.cao.entity.PageResult;
import com.cao.entity.QueryPageBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService{
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        //插入检查项和检查组的中间表
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    @Override
    public PageResult queryPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page=checkGroupDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup queryById(Integer id) {
        return checkGroupDao.selectById(id);
    }

    @Override
    public List<Integer> queryCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.selectCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.updateCheckGroup(checkGroup);
        checkGroupDao.deleteAssociation(checkGroup.getId());
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){
        if (checkitemIds!=null && checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkGroup_id", checkGroupId);
                map.put("checkitemId", checkitemId);
                checkGroupDao.addCheckGroupAndCheckItem(map);
            }
        }
    }

    @Override
    public void removeById(Integer id) {
        Integer belongCount=checkGroupDao.findBelongCountById(id);
        if (belongCount>0){
            throw new RuntimeException();
        }else {
            checkGroupDao.deleteAssociation(id);
            checkGroupDao.deleteById(id);
        }
    }

    @Override
    public List<CheckGroup> queryAll() {
        return checkGroupDao.selectAll();
    }
}
