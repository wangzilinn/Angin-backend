package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.cards.DisplayedCard;
import com.***REMOVED***.site.services.AccessCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/updateCardDetail/{key}", method = RequestMethod.PUT)
    public void updateCardDetail(@PathVariable String key, @RequestBody DisplayedCard displayedCard){
        System.out.println(key);
        accessCards.updateCardFrontAndBack(key, displayedCard.front, displayedCard.back);
    }

//    @RequestMapping(value = "/updateCardExpirationDate/{key}/{date}", method = RequestMethod.GET)
//    public void updateCardExpirationDate(@PathVariable String key, @PathVariable String date) throws ParseException {
//        Date ExpirationDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
//        //TODO:单纯更新单词的过期时间
//    }

}
