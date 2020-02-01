package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.cards.DBCard;
import com.***REMOVED***.site.cards.DisplayedCard;
import com.***REMOVED***.site.services.AccessCards;
import com.***REMOVED***.site.services.ConvertCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CardServiceController {

    final private AccessCards accessCards;
    final private ConvertCards convertCards;

    @Autowired
    CardServiceController(AccessCards accessCards, ConvertCards convertCards) {
        this.accessCards = accessCards;
        this.convertCards = convertCards;
    }


    @RequestMapping(value = "/getAllExpireCards", method = RequestMethod.GET)
    public List<DisplayedCard> getAllExpireCards() {
        List<DBCard> DBCardList = accessCards.getAllExpiredCards();
        List<DisplayedCard> displayCards = new ArrayList<>();
        for(DBCard DBCard : DBCardList){
            displayCards.add(convertCards.toDisplayedCard(DBCard));
        }
        return displayCards;
    }

    @RequestMapping(value = "/getSpecificCard/{key}", method = RequestMethod.GET)
    public DisplayedCard getSpecificCard(@PathVariable String key) {
        DBCard DBCard = accessCards.getSpecificCard(key);
        return convertCards.toDisplayedCard(DBCard);
    }

   @RequestMapping(value = "/getTodayCards/{expiredLimit}/{newLimit}", method = RequestMethod.GET)
    public List<DisplayedCard> getTodayCards(@PathVariable int expiredLimit, @PathVariable int newLimit){
        List<DBCard> DBCardList = accessCards.getTodayCards(expiredLimit, newLimit);
       List<DisplayedCard> displayCards = new ArrayList<>();
       for(DBCard DBCard : DBCardList){
           displayCards.add(convertCards.toDisplayedCard(DBCard));
       }
       return displayCards;
    }

    @RequestMapping(value = "/updateCardStatus/{key}/{status}", method = RequestMethod.GET)
    public DisplayedCard updateCardStatus(@PathVariable String key, @PathVariable String status) {
        return accessCards.updateCardStatus(key, status);
    }

    @RequestMapping(value = "/updateCardDetail/{key}", method = RequestMethod.PUT)
    public void updateCardDetail(@PathVariable String key, @RequestBody DisplayedCard displayedCard){
        System.out.println("update detail: " + key);
        accessCards.updateCardFrontAndBack(key, displayedCard.front, displayedCard.back);
    }

    @RequestMapping(value = "/deleteCard/{key}", method = RequestMethod.DELETE)
    public void deleteCard(@PathVariable String key) {
        System.out.println("delete " + key);
        accessCards.deleteCard(key);
    }

//    @RequestMapping(value = "/updateCardExpirationDate/{key}/{date}", method = RequestMethod.GET)
//    public void updateCardExpirationDate(@PathVariable String key, @PathVariable String date) throws ParseException {
//        Date ExpirationDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
//        //TODO:单纯更新单词的过期时间
//    }

}
