package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.cards.DisplayedCard;
import com.***REMOVED***.site.services.AccessCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class UpdateCardController {
    final private AccessCards accessCards;

    @Autowired
    public UpdateCardController(AccessCards accessCards) {
        this.accessCards = accessCards;
    }
    @RequestMapping(value = "/updateCardStatus/{key}/{status}", method = RequestMethod.GET)
    public DisplayedCard updateCardStatus(@PathVariable String key, @PathVariable String status) {
        return accessCards.updateCardStatus(key, status);
    }

    @RequestMapping(value = "/updateCardExpirationDate/{key}/{date}")
    public void updateCardExpirationDate(@PathVariable String key, @PathVariable String date) throws ParseException {
        Date ExpirationDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
        //TODO:单纯更新单词的过期时间
    }

    @RequestMapping(value = "/updateCardExpirationDate/{key}/{date}")
    public void updateCardDetil(@PathVariable String key, @PathVariable String date) throws ParseException {
        Date ExpirationDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
        //TODO:单纯更新单词的过期时间
    }

}
