package com.zyz.dangxia.util.mqtt;


import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by zhuang_ge on 2017/8/23.
 * 封装mqtt消息的类 提供方法 返回解析后的数据
 */

public class MqttMsgBean {

    //消息主题
    private String topic;

    //消息主体
    private MqttMessage mqttMessage;

    public MqttMsgBean(String topic, MqttMessage mqttMessage) {
        this.topic = topic;//原始主题 /lpwa/app/{appId}/{data|info|alert}/{userId}
        this.mqttMessage = mqttMessage;//原始数据 sn1$23#60%|sn2$35#65%

    }

    public MqttMsgBean() {

    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public MqttMessage getMqttMessage() {
        return mqttMessage;
    }

    public void setMqttMessage(MqttMessage mqttMessage) {
        this.mqttMessage = mqttMessage;
    }

}
