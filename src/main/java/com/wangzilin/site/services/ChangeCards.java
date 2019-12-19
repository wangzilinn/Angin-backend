package com.***REMOVED***.site.services;

import com.***REMOVED***.site.cards.Card;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class ChangeCards {
    Card card;
    public void updateStatus(Card card, int status) {
        this.card = card;
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
        card.setExpireDate(calendar.getTime()); //这个时间就是日期往后推一天的结果
    }
}
