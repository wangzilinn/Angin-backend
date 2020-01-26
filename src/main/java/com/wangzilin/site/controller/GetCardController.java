package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.cards.DBCard;
import com.***REMOVED***.site.cards.DisplayedCard;
import com.***REMOVED***.site.services.AccessCards;
import com.***REMOVED***.site.services.ConvertCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GetCardController {

    final private AccessCards accessCards;
    final private ConvertCards convertCards;

    @Autowired
    GetCardController(AccessCards accessCards, ConvertCards convertCards) {
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

}
