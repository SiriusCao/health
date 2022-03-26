package com.cao.service;

import com.cao.POJO.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    public void add(List<OrderSetting> orderSettingList);
    public List<Map> getOrderSettingByMonth(String data);
    public void editNumberByDate(OrderSetting orderSetting);
}
