package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.services.AccessCards;
import com.***REMOVED***.site.services.ChangeCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateCardController {
    final private AccessCards accessCards;

    @Autowired
    public UpdateCardController(AccessCards accessCards) {
        this.accessCards = accessCards;
    }
    @RequestMapping(value = "/updateCard", method = RequestMethod.GET)
    public void updateCard(String key, String status) {
        accessCards.updateCard(key, status);
    }
}
