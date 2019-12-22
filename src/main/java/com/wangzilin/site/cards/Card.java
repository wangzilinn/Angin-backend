package com.***REMOVED***.site.cards;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Data
public class Card {
    @Id
    private String id;
    private String key;
    private String front;
    private String back;
    private Date expireDate;
    private int status;

    public Card(){}

    public Card(Card card) {
        this.key = card.key;
        this.front = card.front;
        this.back = card.back;
        this.expireDate = card.expireDate;
        this.status = card.status;
    }
    public Card(String key, String front, String back) {
        this(key, front, back,new Date());
    }

    public Card(String key, String front, String back, Date expireDate) {
        this.key = key;
        this.front = front;
        this.back = back;
        this.expireDate = expireDate;
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
