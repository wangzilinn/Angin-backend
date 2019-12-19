package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.cards.Card;
import com.***REMOVED***.site.services.AccessCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class GetCardController {

    final private AccessCards accessCards;

    @Autowired
    GetCardController(AccessCards accessCards) {
        this.accessCards = accessCards;
    }

    @RequestMapping(value = "/getExpireCard", method = RequestMethod.GET)
    public String getExpireCard() {
        Card card = accessCards.getSingleExpiredCardFromDB(new Date());
        return card.toHTML();
    }

    @RequestMapping(value = "/getAllExpireCards", method = RequestMethod.GET)
    public String getAllExpireCards() {
        List<Card> cardList = accessCards.getAllExpiredCardsFromDB(new Date());
        StringBuilder returnString = new StringBuilder();
        for (Card card : cardList) {
            returnString.append(card.toHTML()).append("<br/>");
        }
        return returnString.toString();
    }
}
