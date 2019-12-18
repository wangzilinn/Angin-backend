package com.***REMOVED***.site.cards;

import com.***REMOVED***.site.cards.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Controller
@Configuration
public class ManageCards {
//    public static ManageCards getInstance(){
//        return new ManageCards();
//    }
//    private ManageCards(){};

    @Autowired
    private CardRepository cardRepository;
    private List<String> readTxt(){
        List<String> list = new ArrayList<>();
        try {
            String pathname = "C:\\Users\\78286\\Desktop\\2.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
            File filename = new File(pathname); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename), StandardCharsets.UTF_8); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Card StringToCard(String cardString) {
        try {
            String[] frontAndBack = cardString.split("\\t");
            return new Card(frontAndBack[0], frontAndBack[1]);
        } catch (Exception e) {
            System.out.println("unable to parse");
        }
            return null;
    }

    public List<Card> getCardsFromTxt() {
        List<String> list = readTxt();
        List<Card> cardList = new ArrayList<>();
        for (String s : list) {
            Card card = StringToCard(s);
            if (card != null) {
                cardList.add(card);
            }
        }
        return cardList;
    }

    public Card getSingleExpiredCardFromDB(Date date) {
            return cardRepository.findLatestByExpireDateGreaterThan(date);
    }

    public List<Card> getAllExpiredCardsFromDB(Date date) {
        return cardRepository.findByExpireDateGreaterThan(new Date());
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public void deleteAllCardsFromDB() {
        cardRepository.deleteAll();
    }

    public void saveCardToDB(Card card) {
        cardRepository.save(card);
    }


}
