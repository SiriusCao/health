package com.cao.dao;

import com.cao.POJO.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {
    public int findCountByOrderDate(Date orderDate);

    public void editByOrderDate(OrderSetting orderSetting);

    public void add(OrderSetting orderSetting);

    public List<OrderSetting> getOrderSettingByMonth(@Param("begin") String begin, @Param("end") String end);

    //更新已预约人数
    public void editReservationsByOrderDate(OrderSetting orderSetting);

    //根据预约日期查询预约设置信息
    public OrderSetting findByOrderDate(Date orderDate);
}
