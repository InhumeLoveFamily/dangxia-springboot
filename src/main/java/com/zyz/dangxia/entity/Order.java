package com.zyz.dangxia.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dx_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "executor_id")
    private int executorId;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "status")
    private int status;//0->订单执行中、-1->订单删除、1->订单已结账、2->订单完成评价

    @Column(name = "finish_date")
    private Date finishDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExecutorId() {
        return executorId;
    }

    public void setExecutorId(int executorId) {
        this.executorId = executorId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", executorId=" + executorId +
                ", orderDate=" + orderDate +
                ", finishDate=" + finishDate +
                ", status=" + status +
                '}';
    }
}
