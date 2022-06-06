package com.example.gestion_formation.models;

public class Course {
    private int code_formation;
    private String title;
    private String dom;
    private int nbj;
    private String year;
    private int month;
    private String former;
    private int nbp;

    public Course(int code_formation, String title, String dom, int nbj, String year, int month, String former, int nbp) {
        this.code_formation = code_formation;
        this.title = title;
        this.dom = dom;
        this.nbj = nbj;
        this.year = year;
        this.month = month;
        this.former = former;
        this.nbp = nbp;
    }

    public int getCode_formation() {
        return code_formation;
    }

    public String getTitle() {
        return title;
    }

    public String getDom() {
        return dom;
    }

    public int getNbj() {
        return nbj;
    }

    public String getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public String getFormer() {
        return former;
    }

    public int getNbp() {
        return nbp;
    }

    public void setCode_formation(int code_formation) {
        this.code_formation = code_formation;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public void setNbj(int nbj) {
        this.nbj = nbj;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setFormer(String former) {
        this.former = former;
    }

    public void setNbp(int nbp) {
        this.nbp = nbp;
    }
}
