package com.emucoo.restApi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.emucoo.common.base.mapper.MyMapper;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@MapperScan(basePackages = "com.emucoo.**.mapper", markerInterface = MyMapper.class)
@ComponentScan(basePackages = {"com.emucoo.restApi","com.emucoo.service","com.emucoo.restApi.sdk.token"})
public class RestApp 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(RestApp.class, args);
    }
}
