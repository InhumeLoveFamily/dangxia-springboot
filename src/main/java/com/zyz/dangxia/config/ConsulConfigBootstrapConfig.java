package com.zyz.dangxia.config;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@Configuration
public class ConsulConfigBootstrapConfig {

    @Value("${server.port:8081}")
    int port;

    @Bean
    public Consul getConsul() {
        // TODO: 2018/7/14将ip改为动态的
        return Consul.builder()
                /*.withHostAndPort(HostAndPort.fromParts("192.168.1.101",port))*/
                .build();
    }

    @Bean
    public String getLocalHost() {
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements())
            {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                System.out.println(netInterface.getName());
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements())
                {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address)
                    {
                        String host = ip.getHostAddress();
                        System.out.println("本机的IP = " + host);
                        if(!ip.getHostAddress().equals("172.0.0.1")) {
                            return host;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "192.168.1.101";

    }

}
