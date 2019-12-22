package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.cards.Card;
import com.***REMOVED***.site.cards.DisplayCard;
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
    public DisplayCard getExpireCard() {
        Card card = accessCards.getSingleExpiredCardFromDB(new Date());
        DisplayCard displayCard = changeCards.toDisplayCard(card);
        return displayCard;
    }

    @RequestMapping(value = "/getAllExpireCards", method = RequestMethod.GET)
    public List<DisplayCard> getAllExpireCards() {
        List<Card> cardList = accessCards.getAllExpiredCardsFromDB(new Date());
        List<DisplayCard> displayCards = new ArrayList<>();
        for(Card card:cardList){
            displayCards.add(changeCards.toDisplayCard(card));
        }
        return displayCards;
    }

    @RequestMapping(value = "/getSpecificCard", method = RequestMethod.GET)
    public DisplayCard getSpecificCard(String key) {
        Card card = accessCards.getSpecificCard(key);
        return changeCards.toDisplayCard(card);
    }
}
