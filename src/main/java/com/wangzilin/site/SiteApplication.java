package com.***REMOVED***.site;


import com.***REMOVED***.site.cards.Card;
import com.***REMOVED***.site.cards.CardRepository;
import com.***REMOVED***.site.cards.GetCards;
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

    @Autowired
    private CardRepository cardRepository;
    public static void main(String[] args) {
        SpringApplication.run(SiteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception{
        cardRepository.deleteAll();

        GetCards getCards = new GetCards();
        List<Card> list = getCards.fromFile();
        int i = -5;
        for (Card card : list) {
            ///////
            Date date=new Date(); //取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.DATE,i++); //把日期往后增加一天,整数  往后推,负数往前移动
            date =  calendar.getTime(); //这个时间就是日期往后推一天的结果
            //////
//            System.out.println(date);
            card.setExpireDate(date);
            cardRepository.save(card);
//            System.out.println(card);
        }

        for (Card card : cardRepository.findAll()) {
            System.out.println(card);
        }

    }

}
