package com.demo.ComentoStatistic.dto;

public class LoginDto {
    private String createDate;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "createDate='" + createDate + '\'' +
                '}';
    }
}
