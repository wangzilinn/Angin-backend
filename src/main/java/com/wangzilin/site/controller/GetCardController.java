package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.cards.DBCard;
import com.***REMOVED***.site.cards.DisplayDBCard;
import com.***REMOVED***.site.services.AccessCards;
import com.***REMOVED***.site.services.ChangeCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class GetCardController {

    final private AccessCards accessCards;
    final private ChangeCards changeCards;

    @Autowired
    GetCardController(AccessCards accessCards, ChangeCards changeCards) {
        this.accessCards = accessCards;
        this.changeCards = changeCards;
    }

    @RequestMapping(value = "/getExpireCard", method = RequestMethod.GET)
    public DisplayDBCard getExpireCard() {
        DBCard DBCard = accessCards.getSingleExpiredCardFromDB(new Date());
        return changeCards.toDisplayCard(DBCard);
    }

    @RequestMapping(value = "/getAllExpireCards", method = RequestMethod.GET)
    public List<DisplayDBCard> getAllExpireCards() {
        List<DBCard> DBCardList = accessCards.getAllExpiredCardsFromDB(new Date());
        List<DisplayDBCard> displayCards = new ArrayList<>();
        for(DBCard DBCard : DBCardList){
            displayCards.add(changeCards.toDisplayCard(DBCard));
        }
        return displayCards;
    }

    @RequestMapping(value = "/getSpecificCard/{key}", method = RequestMethod.GET)
    public DisplayDBCard getSpecificCard(@PathVariable String key) {
        DBCard DBCard = accessCards.getSpecificCard(key);
        return changeCards.toDisplayCard(DBCard);
    }

    //TODO:获得今日要背的所有单词
//    public DisplayCard getTodayCards(){
//        List<Card>
//    }

}
