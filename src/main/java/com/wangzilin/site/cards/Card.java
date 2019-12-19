package com.***REMOVED***.site.cards;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Data
public class Card {
    @Id
    private String id;
    private String front;
    private String back;
    private Date expireDate;
    private int status;

    public Card(){}
    public Card(String front, String back) {
        this(front,back,new Date());
    }

    public Card(String front, String back, Date expireDate) {
        this.front = front;
        this.back = back;
        this.expireDate = expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String toString(){
        return "\nfront:\n" + front + "\nback:\n" + back
                + "\nexpireDate:\n" + expireDate + "\nstatus:" + status;
    }

    public String toHTML(){
        return "</br>front:</br>" + front + "</br>back:</br>" + back
                + "</br>expireDate:</br>" + expireDate + "</br>status:" + status;
    }
}
