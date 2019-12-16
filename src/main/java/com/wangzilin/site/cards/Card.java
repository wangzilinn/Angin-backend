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

    public void updateStatus(int status) {
        this.status = status;
        switch (status) {
            case 0:
                setExpireDate(0);
                break;
            case 1:
                setExpireDate(10);
                break;
            case 2:
                setExpireDate(30);
                break;
            case 3:
                setExpireDate(300);
        }
    }

    private void setExpireDate(int minutes) {
        Date date=new Date(); //取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE,minutes); //把日期往后增加一天,整数  往后推,负数往前移动
        expireDate =  calendar.getTime(); //这个时间就是日期往后推一天的结果
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String toString(){
        return "front:" + front + " back:" + back + "\n"
                + "expireDate:" + expireDate + "status:" + status;
    }
}
