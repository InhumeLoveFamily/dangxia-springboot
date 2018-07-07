package com.zyz.dangxia.common.order;

/**
 * 用于展示某个对话（conversation）下，对应的任务实时价格已经订单状态
 */
public class OrderStateDto {
    int conversationId;

    double price;

    int orderId;

    int executorId;

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getExecutorId() {
        return executorId;
    }

    public void setExecutorId(int executorId) {
        this.executorId = executorId;
    }
}
