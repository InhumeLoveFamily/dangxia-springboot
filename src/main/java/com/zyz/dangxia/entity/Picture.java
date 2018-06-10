package com.zyz.dangxia.entity;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "picture")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private byte[] content;

    private String url;

    private String description;

    private int type;

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", content=" + Arrays.toString(content) +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
