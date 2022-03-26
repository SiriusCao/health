package com.cao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.cao.POJO.OrderSetting;
import com.cao.dao.OrderSettingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService{
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    public void add(List<OrderSetting> orderSettingList) {
        for (OrderSetting orderSetting : orderSettingList) {
            int countOfOrderDate=orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            if (countOfOrderDate>0){
                orderSettingDao.editByOrderDate(orderSetting);
            }else {
                orderSettingDao.add(orderSetting);
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String data) {
        String begin=data+"-01";
        String end=data+"-31";
        List<OrderSetting> orderSettingList=orderSettingDao.getOrderSettingByMonth(begin,end);
        List<Map> orderSettingMapList=new ArrayList<>();
        for (OrderSetting orderSetting : orderSettingList) {
            Map<String,Integer> orderSettingMap=new HashMap<>();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());
            orderSettingMap.put("number",orderSetting.getNumber());
            orderSettingMap.put("reservations",orderSetting.getReservations());
            orderSettingMapList.add(orderSettingMap);
        }
        return orderSettingMapList;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        int countOfOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (countOfOrderDate>0){
            orderSettingDao.editByOrderDate(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }
    }
}
