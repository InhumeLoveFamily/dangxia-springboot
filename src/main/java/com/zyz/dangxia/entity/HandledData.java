package com.zyz.dangxia.entity;

import javax.persistence.*;

@Entity
@Table(name = "handled_data")
public class HandledData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "c0")
    private int c0;

    @Column(name = "c1")
    private int c1;

    @Column(name = "c2")
    private int c2;

    @Column(name = "c3")
    private int c3;

    @Column(name = "t")
    private int t;

    @Column(name = "p")
    private int p;

    @Column(name = "class_id")
    private int classId;

    public String getKey() {
        return "" + c0 + c1 + c2 + c3 + t;
    }

    public HandledData() {
    }

    public HandledData(int c0, int c1, int c2, int c3, int t, int p, int classId) {
        this.c0 = c0;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.t = t;
        this.p = p;
        this.classId = classId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getC0() {
        return c0;
    }

    public void setC0(int c0) {
        this.c0 = c0;
    }

    public int getC1() {
        return c1;
    }

    public void setC1(int c1) {
        this.c1 = c1;
    }

    public int getC2() {
        return c2;
    }

    public void setC2(int c2) {
        this.c2 = c2;
    }

    public int getC3() {
        return c3;
    }

    public void setC3(int c3) {
        this.c3 = c3;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
