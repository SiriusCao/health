package com.cao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring-mvc.xml")
public class PasswordTest {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void test(){
        System.out.println(passwordEncoder.encode("admin"));
    }
}
