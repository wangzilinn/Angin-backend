package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.services.ChangeCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UpdateCardController {
    final private ChangeCards changeCards;

    @Autowired
    public UpdateCardController(ChangeCards changeCards) {
        this.changeCards = changeCards;
    }
    @RequestMapping(value = "/updateCard", method = RequestMethod.GET)
    public String updateCard(String key, String status) {
        //status to new expire date,
        Date newExpireDate = changeCards.getNewExpireData(status);

        return "";
    }
}
