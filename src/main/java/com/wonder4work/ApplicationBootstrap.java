package com.wonder4work;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @since 1.0.0 2020/3/23
 */
@SpringBootApplication
@MapperScan(basePackages = "com.wonder4work.mapper")
@ComponentScan(basePackages = {"com.wonder4work","org.n3r.idworker"})
public class ApplicationBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationBootstrap.class, args);
    }

}
