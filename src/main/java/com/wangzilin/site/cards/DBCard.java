package com.***REMOVED***.site.cards;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;


public class DBCard extends Card{
    @Id
    private String id;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;
    public DBCard(){
        super();
    }

    public DBCard(String key, String front, String back) {
        this(key, front, back,new Date(), -1);
    }

    public DBCard(String key, String front, String back, Date expireDate, int status) {
        super(key, front, back, expireDate);
        this.status = status;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String toString(){
        return "key:\n" + key + "\nfront:\n" + front + "\nback:\n" + back
                + "\nexpireDate:\n" + expireDate + "\nstatus:" + status;
    }

    public String toHTML(){
        return "key</br>" + key +
                "</br>front:</br>" + front +
                "</br>back:</br>" + back +
                "</br>expireDate:</br>" + expireDate +
                "</br>status:" + status;
    }
}
