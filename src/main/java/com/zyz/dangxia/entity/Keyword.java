package com.zyz.dangxia.entity;

import javax.persistence.*;

/**
 * 用于KNN算法 一个Keyword类对应了一系列关键词，这些关键词都指明了同一个意思
 */
@Entity
@Table(name = "keyword")
public class Keyword {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String content;

    @Column(name = "class_id")
    private int classId;

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

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
