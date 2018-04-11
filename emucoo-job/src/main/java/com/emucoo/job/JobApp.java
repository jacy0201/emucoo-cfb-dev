package com.emucoo.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.emucoo.common.base.mapper.MyMapper;

@SpringBootApplication
@EnableTransactionManagement // 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
@MapperScan(basePackages = "com.emucoo.**.mapper", markerInterface = MyMapper.class)
@ComponentScan({"com.emucoo.job","com.emucoo.service"})
public class JobApp {
    
    public static void main(String[] args) {
        SpringApplication.run(JobApp.class, args);
    }
}
