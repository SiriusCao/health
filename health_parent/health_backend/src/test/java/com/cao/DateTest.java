package com.cao;

import org.junit.Test;

import java.util.Date;

public class DateTest {
    @Test
    public void test1(){
        Date date=new Date("2021/1/18");
        System.out.println(date.getTime());
    }
}
