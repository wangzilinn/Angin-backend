package com.***REMOVED***.site.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.***REMOVED***.site.model.DBCard;
import com.***REMOVED***.site.model.DisplayedCard;
import com.***REMOVED***.site.services.CardAccessor;
import com.***REMOVED***.site.services.CardConverter;
import com.***REMOVED***.site.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CardController {

    final private CardAccessor cardAccessor;
    final private CardConverter cardConverter;
    final private UserService userService;
    final private ObjectMapper mapper;

    @Autowired
    public CardController(CardAccessor cardAccessor, CardConverter cardConverter, UserService userService,
                          ObjectMapper mapper) {
        this.cardAccessor = cardAccessor;
        this.cardConverter = cardConverter;
        this.userService = userService;
        this.mapper = mapper;
    }

    //获得所有过期卡片
    @RequestMapping(value = "/expiredCards", method = RequestMethod.POST)
    public ResponseEntity<List<DisplayedCard>> getAllExpireCards(@RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        String password = params.get("password");
        if (!userService.authenticateUser(userId, password)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            List<DBCard> DBCardList = cardAccessor.getAllExpiredCards();
            List<DisplayedCard> displayCards = new ArrayList<>();
            for (DBCard DBCard : DBCardList) {
                displayCards.add(cardConverter.toDisplayedCard(DBCard));
            }
            return new ResponseEntity<>(displayCards, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //获得指定卡片
    @RequestMapping(value = "/card", method = RequestMethod.POST)
    public ResponseEntity<DisplayedCard> getSpecificCard(@RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        String password = params.get("password");
        if (!userService.authenticateUser(userId, password)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            String key = params.get("key");
            DBCard DBCard = cardAccessor.getSpecificCard(key);
            return new ResponseEntity<>(cardConverter.toDisplayedCard(DBCard), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 获得今日卡片
    @RequestMapping(value = "/todayCards", method = RequestMethod.POST)
    public ResponseEntity<List<DisplayedCard>> getTodayCards(@RequestBody Map<String, Object> params) {
        String userId = (String) params.get("userId");
        String password = (String) params.get("password");
        if (!userService.authenticateUser(userId, password)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            int expiredLimit = (int) params.get("expiredLimit");
            int newLimit = (int) params.get("newLimit");
            List<DBCard> DBCardList = cardAccessor.getTodayCards(expiredLimit, newLimit);
            List<DisplayedCard> displayCards = new ArrayList<>();
            for (DBCard DBCard : DBCardList) {
                displayCards.add(cardConverter.toDisplayedCard(DBCard));
            }
            return new ResponseEntity<>(displayCards, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 更新卡片状态
    @RequestMapping(value = "/cardStatus", method = RequestMethod.PUT)
    public ResponseEntity<DisplayedCard> updateCardStatus(@RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        String password = params.get("password");
        if (!userService.authenticateUser(userId, password)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            String key = params.get("key");
            String status = params.get("status");
            return new ResponseEntity<>(cardAccessor.updateCardStatus(key, status), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 更新卡片内容
    @RequestMapping(value = "/cardDetail", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCardDetail(@RequestBody Map<String, Object> params) {
        String userId = (String) params.get("userId");
        String password = (String) params.get("password");
        if (!userService.authenticateUser(userId, password)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            String key = (String) params.get("key");
            DisplayedCard displayedCard = mapper.convertValue(params.get("card"), DisplayedCard.class);
            cardAccessor.updateCardFrontAndBack(key, displayedCard.front, displayedCard.back);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 删除卡片
    @RequestMapping(value = "/card", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCard(@RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        String password = params.get("password");
        if (!userService.authenticateUser(userId, password)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            String key = params.get("key");
            cardAccessor.deleteCard(key);
            return new ResponseEntity<>("delete" + key, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @RequestMapping(value = "/updateCardExpirationDate/{key}/{date}", method = RequestMethod.GET)
//    public void updateCardExpirationDate(@PathVariable String key, @PathVariable String date) throws ParseException {
//        Date ExpirationDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
//        //TODO:单纯更新单词的过期时间
//    }

}
