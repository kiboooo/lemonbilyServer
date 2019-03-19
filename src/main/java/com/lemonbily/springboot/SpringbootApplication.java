package com.lemonbily.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@MapperScan("com.lemonbily.springboot.mapper")//此处填写 mapper接口所在包，如果不加则需要在每个 mapper上添加 @Mapper 注解。

public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
