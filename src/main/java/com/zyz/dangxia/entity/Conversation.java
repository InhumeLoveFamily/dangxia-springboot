package com.zyz.dangxia.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "conversation")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "task_id")
    private int taskId;

    @Column(name = "initiator_id")
    private int initiatorId;

    @Column(name = "last_date")
    private Date lastDate;

    @Column(name = "last_words")
    private String lastWords;

    @Column(name = "init_date")
    private Date initDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(int initiatorId) {
        this.initiatorId = initiatorId;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastWords() {
        return lastWords;
    }

    public void setLastWords(String lastWords) {
        this.lastWords = lastWords;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", initiatorId=" + initiatorId +
                ", lastDate=" + lastDate +
                ", lastWords='" + lastWords + '\'' +
                ", initDate=" + initDate +
                '}';
    }
}
