package com.zyz.dangxia.util.consul;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.agent.ImmutableRegCheck;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.health.ServiceHealth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
@Service
@Slf4j
public class ConsulManager {

    @Autowired
    private Consul consul;

    @Autowired
    private String localHost;

    @Value("${server.port:8081}")
    private int port;

    @Value("${server.display-name:dangxia}")
    private String serviceName;

    @PostConstruct
    public void init() {
        log.info("开始自动注册{}");
        register(serviceName,"1");
        log.info("自动注册完成");
    }

    public void register(String serviceName, String serviceId) {
        String tag = "1";
        log.info("localhost = {},service = {},tag = {}",localHost,serviceName,tag);
        String http = String.format("http://%s:%d/health",localHost,port);
        AgentClient agentClient = consul.agentClient();
        //配置健康检查相关
        ImmutableRegCheck check = ImmutableRegCheck.builder()
                .http(http)
                .interval("5s").build();
        ImmutableRegistration registration = ImmutableRegistration.builder()
                .id(serviceId).name(serviceName).addTags(tag).address(localHost).port(port).addChecks(check)
                .build();
        agentClient.register(registration);
    }

    public List<ServiceHealth> listHealthyServices(String serviceName) {
        HealthClient client = consul.healthClient();
        return client.getHealthyServiceInstances(serviceName).getResponse();
    }
}
