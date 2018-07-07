package com.zyz.dangxia;

import com.zyz.dangxia.util.mqtt.MqttManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableSwagger2
@MapperScan("com.zyz.dangxia.mapper")
public class DangxiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DangxiaApplication.class, args);
        MqttManager.getInstance().creatConnect();
	}
}
