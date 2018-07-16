package com.zyz.dangxia.service.impl;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.agent.ImmutableRegCheck;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.health.HealthCheck;
import com.orbitz.consul.model.health.ServiceHealth;
import com.zyz.dangxia.service.ConsulService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
@Service
@Slf4j
public class ConsulServiceImpl implements ConsulService {

    @Autowired
    private Consul consul;

    @Autowired
    private String localHost;

    @Value("${server.port:8081}")
    private int port = 8081;

    @PostConstruct
    public void init() {
        log.info("开始自动注册");
        register("dangxia1","3");
        log.info("自动注册完成");
    }

    @Override
    public void register(String serviceName, String serviceId) {
        log.info("localhost = {}",localHost);
        String http = String.format("http://%s:%d/health",localHost,port);
        AgentClient agentClient = consul.agentClient();
        //配置健康检查相关
        ImmutableRegCheck check = ImmutableRegCheck.builder()
                .http(http)
                .interval("5s").build();
        ImmutableRegistration registration = ImmutableRegistration.builder()
                .id(serviceId).name(serviceName).addTags("dev").address(localHost).port(port).addChecks(check)
                .build();
        agentClient.register(registration);
    }

    @Override
    public List<ServiceHealth> listHealthyServices(String serviceName) {
        HealthClient client = consul.healthClient();
        return client.getHealthyServiceInstances(serviceName).getResponse();
    }
}
