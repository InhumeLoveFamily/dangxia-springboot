package com.zyz.dangxia.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private Date date;

    @Column(name = "sender")
    private int sender;

    @Column(name = "type")
    private int type;//0->文字消息（默认）、1->语音消息、2->图片消息

    @Column(name = "status")
    private int status;

    @Column(name = "conversation_id")
    private int conversationId;

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", sender=" + sender +
                ", type=" + type +
                ", status=" + status +
                ", conversationId=" + conversationId +
                '}';
    }
}
