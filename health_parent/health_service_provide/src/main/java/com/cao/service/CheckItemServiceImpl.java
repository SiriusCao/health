package com.cao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.cao.POJO.CheckItem;
import com.cao.dao.CheckItemDao;
import com.cao.entity.PageResult;
import com.cao.entity.QueryPageBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult PageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);

        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();

        return new PageResult(total,rows);
    }

    @Override
    public void deleteById(Integer id) {
        Integer belongCount=checkItemDao.findBelongCountById(id);
        if (belongCount>0){
            throw new RuntimeException();
        }else {
            checkItemDao.deleteById(id);
        }
    }

    @Override
    public CheckItem findById(Integer id) {
        CheckItem checkItem=checkItemDao.queryById(id);
        return checkItem;
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> checkItemList=checkItemDao.findAll();
        return checkItemList;
    }
}
