package com.***REMOVED***.site.controller;

import com.***REMOVED***.site.model.DBCard;
import com.***REMOVED***.site.model.DisplayedCard;
import com.***REMOVED***.site.services.CardAccessor;
import com.***REMOVED***.site.services.CardConverter;
import com.***REMOVED***.site.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CardController {

    final private CardAccessor cardAccessor;
    final private CardConverter cardConverter;
    final private UserService userService;

    @Autowired
    CardController(CardAccessor cardAccessor, CardConverter cardConverter, UserService userService) {
        this.cardAccessor = cardAccessor;
        this.cardConverter = cardConverter;
        this.userService = userService;
    }


    @RequestMapping(value = "/expiredCards", method = RequestMethod.GET)
    public List<DisplayedCard> getAllExpireCards(@RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        String password = params.get("password");
        List<DBCard> DBCardList = cardAccessor.getAllExpiredCards();
        List<DisplayedCard> displayCards = new ArrayList<>();
        for (DBCard DBCard : DBCardList) {
            displayCards.add(cardConverter.toDisplayedCard(DBCard));
        }
        return displayCards;
    }

    @RequestMapping(value = "/getSpecificCard/{key}", method = RequestMethod.GET)
    public DisplayedCard getSpecificCard(@PathVariable String key) {
        DBCard DBCard = cardAccessor.getSpecificCard(key);
        return cardConverter.toDisplayedCard(DBCard);
    }

   @RequestMapping(value = "/getTodayCards/{expiredLimit}/{newLimit}", method = RequestMethod.GET)
    public List<DisplayedCard> getTodayCards(@PathVariable int expiredLimit, @PathVariable int newLimit){
       List<DBCard> DBCardList = cardAccessor.getTodayCards(expiredLimit, newLimit);
       List<DisplayedCard> displayCards = new ArrayList<>();
       for(DBCard DBCard : DBCardList){
           displayCards.add(cardConverter.toDisplayedCard(DBCard));
       }
       return displayCards;
    }

    @RequestMapping(value = "/updateCardStatus/{key}/{status}", method = RequestMethod.GET)
    public DisplayedCard updateCardStatus(@PathVariable String key, @PathVariable String status) {
        return cardAccessor.updateCardStatus(key, status);
    }

    @RequestMapping(value = "/updateCardDetail/{key}", method = RequestMethod.PUT)
    public void updateCardDetail(@PathVariable String key, @RequestBody DisplayedCard displayedCard){
        System.out.println("update detail: " + key);
        cardAccessor.updateCardFrontAndBack(key, displayedCard.front, displayedCard.back);
    }

    @RequestMapping(value = "/deleteCard/{key}", method = RequestMethod.DELETE)
    public void deleteCard(@PathVariable String key) {
        System.out.println("delete " + key);
        cardAccessor.deleteCard(key);
    }

//    @RequestMapping(value = "/updateCardExpirationDate/{key}/{date}", method = RequestMethod.GET)
//    public void updateCardExpirationDate(@PathVariable String key, @PathVariable String date) throws ParseException {
//        Date ExpirationDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
//        //TODO:单纯更新单词的过期时间
//    }

}
