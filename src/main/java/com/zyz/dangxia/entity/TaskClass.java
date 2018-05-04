package com.zyz.dangxia.entity;

import javax.persistence.*;

/**
 * 任务的类别，被用于加权平均数分析
 */
@Entity
@Table(name = "task_class")
public class TaskClass {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
