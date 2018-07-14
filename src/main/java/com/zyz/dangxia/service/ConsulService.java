package com.zyz.dangxia.service;

import com.orbitz.consul.model.health.ServiceHealth;

import java.util.List;

public interface ConsulService {
    void register(String serviceName,String serviceId);
    List<ServiceHealth> listHealthyServices(String serviceName);
}
