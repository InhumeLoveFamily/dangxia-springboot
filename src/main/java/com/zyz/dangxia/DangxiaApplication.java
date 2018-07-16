package com.zyz.dangxia;

import com.zyz.dangxia.util.mqtt.MqttManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
//@EnableCaching
@EnableSwagger2
@MapperScan("com.zyz.dangxia.mapper")
//@EnableDiscoveryClient
public class DangxiaApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		ConfigurableApplicationContext cac = SpringApplication.run(DangxiaApplication.class, args);
//		ConsulService consulService = (ConsulService) cac.getBean("ConsulServiceImpl");ConsulServiceImplΩ
//		consulService.register("dangxia","3");
//        new SpringApplicationBuilder(DangxiaApplication.class).web(true).run(args);
//        configureApplication(new SpringApplicationBuilder()).run(args);

        MqttManager.getInstance().creatConnect();
//        Consul consul = Consul.builder().build();
//        try {
//            consul.agentClient().register(8081, URI.create("http://192.168.1.101:8081/health").toURL(),
//                    2,"dangxia","3","dev");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return configureApplication(builder);
	}

    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
        return builder.sources(DangxiaApplication.class).bannerMode(Banner.Mode.CONSOLE).registerShutdownHook(true).web(true);
    }
}
