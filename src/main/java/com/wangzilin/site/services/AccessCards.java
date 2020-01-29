package com.***REMOVED***.site.services;

import com.***REMOVED***.site.cards.DBCard;
import com.***REMOVED***.site.cards.DisplayedCard;
import com.***REMOVED***.site.dao.CardDAO;
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


/**
 * 这个类负责从数据库类中获取数据
 */
@Service
public class AccessCards {
    final private CardDAO cardDAO;

    @Autowired
    public AccessCards(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
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

    public List<DBCard> getLocalCards() {
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

    //获得所有到期卡片
    public List<DBCard> getAllExpiredCards() {
        return cardDAO.findByExpireDateLessThan(new Date());
    }


    //获得指定key的卡片
    public DBCard getSpecificCard(String key) {
        return cardDAO.findByKeyContains(key);
    }

    //获得今日要背的卡片
    public List<DBCard> getTodayCards(int expiredCardLimit, int newCardLimit){
        List<DBCard> expiredCardList = cardDAO.findByExpireDateLessThan(new Date(), expiredCardLimit);
        List<DBCard> newCardList = cardDAO.findByStatusEqualTo(-1, newCardLimit);

        expiredCardList.addAll(newCardList);
        return expiredCardList;
    }

    //根据传入的参数更新单词的状态和过期时间
    public DisplayedCard updateCardStatus(String key, String option) {
        ConvertCards convertCards = new ConvertCards();
        Date expirationDate = convertCards.optionToExpirationDate(option);
        int status = convertCards.optionsToStatus(option);
        //先存入数据库
        cardDAO.updateStatusAndExpiredDate(key, status, expirationDate);
        //再把存入的新的取回来
        DBCard dbCard = cardDAO.findByKeyContains(key);
        return convertCards.toDisplayedCard(dbCard);
    }

    public void saveCard(DBCard dbCard) {
        cardDAO.saveCard(dbCard);
    }

}
