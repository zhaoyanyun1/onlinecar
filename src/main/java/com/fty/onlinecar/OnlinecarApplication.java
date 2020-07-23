package com.fty.onlinecar;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication

@MapperScan({"com.fty.onlinecar.dao","com.fty.onlinecar.base.dao"})
@ComponentScan(basePackages = {"com.fty.onlinecar.**"})
public class OnlinecarApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinecarApplication.class, args);
    }

}
