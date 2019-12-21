package com.***REMOVED***.site.services;

import com.***REMOVED***.site.cards.Card;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class ChangeCards {
    public Date getNewExpireData(String status) {
        Date expireDate;
        switch (status) {
            case "一点没印象":
                expireDate = setExpireDate(0);
                break;
            case "没啥印象":
                expireDate = setExpireDate(10);
                break;
            case "好像记住了":
                expireDate = setExpireDate(30);
                break;
            case "记得很清楚":
                expireDate = setExpireDate(300);
                break;
            case "永远不会忘":
                expireDate = setExpireDate(3000);
                break;
            case "我爱你":
                expireDate = setExpireDate(9999999);
                break;
            default:
                expireDate = null;
        }
        return expireDate;
    }

    private Date setExpireDate(int minutes) {
        Date date=new Date(); //取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        if (minutes == 9999999) {
            calendar.add(Calendar.YEAR,1);
        }
        calendar.add(Calendar.MINUTE,minutes); //把日期往后增加一天,整数  往后推,负数往前移动
        return calendar.getTime(); //这个时间就是日期往后推一天的结果
    }
}
