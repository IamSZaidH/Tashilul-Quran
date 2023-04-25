package com.zaid.tashilulquran;

public class BookmarkModel {
    int page_no;



    int id;
    String title, para_no;

    public BookmarkModel() {
    }

    public BookmarkModel(int page_no, String para_no, String title) {
        this.page_no = page_no;
        this.title = title;
        this.para_no = para_no;
    }

    public int getPage_no() {
        return page_no;
    }

    public void setPage_no(int page_no) {
        this.page_no = page_no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPara_no() {
        return para_no;
    }

    public void setPara_no(String para_no) {
        this.para_no = para_no;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
