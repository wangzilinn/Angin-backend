package com.***REMOVED***.site;


import com.***REMOVED***.site.services.AccessCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

//        List<DBCard> list = accessCards.getLocalCards();
//        int i = -5;
//        for (DBCard card : list) {
//            ///////
//            Date date=new Date(); //取时间
//            Calendar calendar = new GregorianCalendar();
//            calendar.setTime(date);
//            calendar.add(Calendar.DATE,i++); //把日期往后增加一天,整数  往后推,负数往前移动
//            date =  calendar.getTime(); //这个时间就是日期往后推一天的结果
//            //////
//            card.setExpireDate(date);
//            accessCards.saveCard(card);
//        }

    }

}
