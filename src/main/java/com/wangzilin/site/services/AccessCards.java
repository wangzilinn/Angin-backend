package com.***REMOVED***.site.services;

import com.***REMOVED***.site.cards.DBCard;
import com.***REMOVED***.site.cards.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccessCards {
    final private CardRepository cardRepository;

    @Autowired
    public AccessCards(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    private List<String> readTxt() {
        List<String> list = new ArrayList<>();
        try {
//            String pathname = "2.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
            File filename = ResourceUtils.getFile("classpath:ankiTxt/test.txt"); // 要读取以上路径的input。txt文件
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

    private DBCard StringToCard(String cardString) {
        try {
            String[] keyAndOther = cardString.split("<br/>", 2);
            String[] frontAndBack = keyAndOther[1].split("\\t");

            return new DBCard(keyAndOther[0].strip().substring(1), frontAndBack[0], frontAndBack[1]);
        } catch (Exception e) {
            System.out.println("unable to parse");
        }
        return null;
    }

    public List<DBCard> getCardsFromTxt() {
        List<String> list = readTxt();
        List<DBCard> DBCardList = new ArrayList<>();
        for (String s : list) {
            DBCard DBCard = StringToCard(s);
            if (DBCard != null) {
                DBCardList.add(DBCard);
            }
        }
        return DBCardList;
    }

    public DBCard getSingleExpiredCardFromDB(Date date) {
        return cardRepository.findLatestByExpireDateLessThan(date);
    }

    public List<DBCard> getAllExpiredCardsFromDB(Date date) {
        return cardRepository.findByExpireDateLessThan(date);
    }

    //TODO:获得到期得卡片
//    public List<Card> getExpiredCardFromDB(Date date, Integer limit){
//        if (limit == null){
//            return cardRepository.findByExpireDateLessThan(date);
//        }else {
//
//        }
//    }

    public List<DBCard> getAllCards() {
        return cardRepository.findAll();
    }

    public void deleteAllCardsFromDB() {
        cardRepository.deleteAll();
    }

    public void saveCardToDB(DBCard DBCard) {
        cardRepository.save(DBCard);
    }

    public DBCard getSpecificCard(String key) {
        return cardRepository.findByKeyContains(key);
    }

    //根据传入的参数更新单词的过期时间
    public void updateCard(String key, String option) {
        ChangeCards changeCards = new ChangeCards();
        Date expireDate = changeCards.optionToExpireData(option);
        int status = changeCards.optionsToStatus(option);
        DBCard DBCard = cardRepository.findByKeyContains(key);
        DBCard.setExpireDate(expireDate);
        DBCard.setStatus(status);
        cardRepository.save(DBCard);
    }
}
