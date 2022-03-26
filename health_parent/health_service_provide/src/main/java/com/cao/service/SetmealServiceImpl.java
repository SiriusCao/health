package com.cao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.cao.POJO.Setmeal;
import com.cao.dao.SetmealDao;
import com.cao.entity.PageResult;
import com.cao.entity.QueryPageBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService{
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${out_put_path}")
    private String outPutPath;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        if (checkgroupIds!=null && checkgroupIds.length>0) {
            setSetmealAndCheckgroup(setmeal.getId(), checkgroupIds);
        }
        generateMobileStaticHtml();
    }

    public void setSetmealAndCheckgroup(Integer id,Integer[] checkgroupIds){
        for (Integer checkgroupId : checkgroupIds) {
            Map<String,Integer> map=new HashMap<>();
            map.put("setmeal_id",id);
            map.put("checkgroup_id",checkgroupId);
            setmealDao.addSetmealAndCheckgroup(map);
        }
    }

    @Override
    public PageResult queryPage(QueryPageBean pageBean) {
        Integer currentPage = pageBean.getCurrentPage();
        Integer pageSize = pageBean.getPageSize();
        String queryString = pageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page=setmealDao.selectPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Setmeal queryById(Integer id) {
        return setmealDao.selectById(id);
    }

    @Override
    public List<Integer> findCheckGroupIdsBySetMealId(Integer id) {
        return setmealDao.findCheckGroupIdsBySetMealId(id);
    }

    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.updateSetmeal(setmeal);
        setmealDao.deleteAssociation(setmeal.getId());
        if (checkgroupIds!=null && checkgroupIds.length>0) {
            setSetmealAndCheckgroup(setmeal.getId(), checkgroupIds);
        }
        generateMobileStaticHtml();
    }

    @Override
    public void removeById(Integer id) {
            setmealDao.deleteAssociation(id);
            setmealDao.deleteById(id);
            generateMobileStaticHtml();
    }

    @Override
    public List<Setmeal> getAllSetmeal() {
        return setmealDao.getAllSetmeal();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    //根据模板名产生HTML文件
    public void generateHtml(String templateName,String htmlPageName,Map<String,Object> map){
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer writer=null;
        try{
            Template template = configuration.getTemplate(templateName);

            //构造输出流，向health_mobile当中输出HTML文件
            writer=new FileWriter(new File(outPutPath+"/"+htmlPageName));
            template.process(map,writer);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Value("${ftl_setmeal}")
    private String ftlSetmeal;
    @Value("${ftl_setmeal_detail}")
    private String ftlSetmealDetail;
    @Value("${html_setmeal}")
    private String htmlSetmeal;
    @Value("${html_setmeal_detail}")
    private String htmlSetmealDetail;
    @Value("${html_suffix}")
    private String HTML;

    //生成套餐列表静态页面
    public void generateMobileSetmealListHtml(List<Setmeal> setmealList){
        Map<String,Object> map=new HashMap<>();
        map.put("setmealList",setmealList);
        this.generateHtml(ftlSetmeal,htmlSetmeal,map);
    }

    //生成套餐详情页面
    public void generateMobileSetmealDetailHtml(List<Setmeal> setmealList){
        for (Setmeal setmeal : setmealList) {
            Map<String,Object> map=new HashMap<>();
            Setmeal setmealIncludeGroupAndItem = setmealDao.findById(setmeal.getId());
            map.put("setmeal",setmealIncludeGroupAndItem);
            this.generateHtml(ftlSetmealDetail,htmlSetmealDetail+setmeal.getId()+HTML,map);
        }
    }

    //生成静态页面
    public void generateMobileStaticHtml(){
        List<Setmeal> setmealList = setmealDao.getAllSetmeal();
        generateMobileSetmealListHtml(setmealList);
        generateMobileSetmealDetailHtml(setmealList);
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }
}
