package com.zyz.dangxia.model;

public class HandledDataDO {
    private Integer id;

    private Integer c0;

    private Integer c1;

    private Integer c2;

    private Integer c3;

    private Integer t;

    private Integer p;

    private Integer classId;

    public HandledDataDO() {
    }

    public HandledDataDO(Integer c0, Integer c1, Integer c2, Integer c3, Integer t, Integer p, Integer classId) {
        this.c0 = c0;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.t = t;
        this.p = p;
        this.classId = classId;
    }

    public String getKey() {
        return "" + classId + c0 + c1 + c2 + c3 + t;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getC0() {
        return c0;
    }

    public void setC0(Integer c0) {
        this.c0 = c0;
    }

    public Integer getC1() {
        return c1;
    }

    public void setC1(Integer c1) {
        this.c1 = c1;
    }

    public Integer getC2() {
        return c2;
    }

    public void setC2(Integer c2) {
        this.c2 = c2;
    }

    public Integer getC3() {
        return c3;
    }

    public void setC3(Integer c3) {
        this.c3 = c3;
    }

    public Integer getT() {
        return t;
    }

    public void setT(Integer t) {
        this.t = t;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }
}