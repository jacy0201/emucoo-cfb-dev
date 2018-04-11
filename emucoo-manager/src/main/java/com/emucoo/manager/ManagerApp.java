package com.emucoo.manager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.emucoo.common.base.mapper.MyMapper;

/**
 *
 * Created by fujg on 2017/1/17.
 */
@SpringBootApplication
@EnableTransactionManagement // 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
@MapperScan(basePackages = "com.emucoo.**.mapper", markerInterface = MyMapper.class)
@ComponentScan({"com.emucoo.manager","com.emucoo.service"})
public class ManagerApp {
    
    public static void main(String[] args) {
        SpringApplication.run(ManagerApp.class, args);
    }
}
