package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.cards.Card;
import com.***REMOVED***.site.cards.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class GetCardController {
    @Autowired
    private CardRepository cardRepository;
    @RequestMapping(value = "/getCard", method = RequestMethod.GET)
    public List<Card> getCard() {
        List<Card> cardList = cardRepository.findByExpireDateGreaterThan(new Date());
        return cardList;
    }
}
