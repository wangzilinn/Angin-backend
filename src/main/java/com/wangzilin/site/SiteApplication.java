package com.***REMOVED***.site;


import com.***REMOVED***.site.cards.Card;
import com.***REMOVED***.site.services.AccessCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SpringBootApplication
public class SiteApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SiteApplication.class, args);
    }

    final private AccessCards accessCards;

    @Autowired
    public SiteApplication(AccessCards accessCards) {
        this.accessCards = accessCards;
    }

    @Override
    public void run(String... args) {

//        accessCards.deleteAllCardsFromDB();
//        List<Card> list = accessCards.getCardsFromTxt();
//        int i = -5;
//        for (Card card : list) {
//            ///////
//            Date date=new Date(); //取时间
//            Calendar calendar = new GregorianCalendar();
//            calendar.setTime(date);
//            calendar.add(Calendar.DATE,i++); //把日期往后增加一天,整数  往后推,负数往前移动
//            date =  calendar.getTime(); //这个时间就是日期往后推一天的结果
//            //////
//
//            card.setExpireDate(date);
//            accessCards.saveCardToDB(card);
//        }
//
//        for (Card card : accessCards.getAllCards()) {
//            System.out.println(card);
//        }

    }

}
