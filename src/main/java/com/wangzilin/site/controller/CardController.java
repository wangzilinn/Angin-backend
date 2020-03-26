package com.***REMOVED***.site.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.***REMOVED***.site.model.card.DBCard;
import com.***REMOVED***.site.model.card.DisplayedCard;
import com.***REMOVED***.site.services.CardAccessor;
import com.***REMOVED***.site.services.CardConverter;
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
@RequestMapping(value = "/card")
public class CardController {

    final private CardAccessor cardAccessor;
    final private CardConverter cardConverter;
    final private ObjectMapper mapper;

    public CardController(CardAccessor cardAccessor, CardConverter cardConverter,
                          ObjectMapper mapper) {
        this.cardAccessor = cardAccessor;
        this.cardConverter = cardConverter;
        this.mapper = mapper;
    }


    /**
     * 获得所有过期卡片
     *
     * @return all expired cards.
     */
    @RequestMapping(value = "/expiredCards", method = RequestMethod.GET)
    public ResponseEntity<List<DisplayedCard>> getAllExpireCards() {
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


    /**
     * 获得指定卡片
     *
     * @param body request body
     * @return specific card
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<DisplayedCard> getSpecificCard(@RequestBody Map<String, String> body) {
        try {
            String key = body.get("key");
            DBCard DBCard = cardAccessor.getSpecificCard(key);
            return new ResponseEntity<>(cardConverter.toDisplayedCard(DBCard), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 获得今日卡片
     *
     * @param body request body
     * @return today's card list.
     */
    @RequestMapping(value = "/todayCards", method = RequestMethod.POST)
    public ResponseEntity<List<DisplayedCard>> getTodayCards(@RequestBody Map<String, Object> body) {
        try {
            int expiredLimit = (int) body.get("expiredLimit");
            int newLimit = (int) body.get("newLimit");
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


    /**
     * 更新卡片状态
     *
     * @param body request body
     * @return display card.
     */
    @RequestMapping(value = "/cardStatus", method = RequestMethod.PUT)
    public ResponseEntity<DisplayedCard> updateCardStatus(@RequestBody Map<String, String> body) {
        try {
            String key = body.get("key");
            String status = body.get("status");
            return new ResponseEntity<>(cardAccessor.updateCardStatus(key, status), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * 更新卡片内容
     *
     * @param body request body
     * @return ..
     */
    @RequestMapping(value = "/cardDetail", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCardDetail(@RequestBody Map<String, Object> body) {
        try {
            String key = (String) body.get("key");
            DisplayedCard displayedCard = mapper.convertValue(body.get("card"), DisplayedCard.class);
            cardAccessor.updateCardFrontAndBack(key, displayedCard.front, displayedCard.back);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //

    /**
     * 删除卡片
     *
     * @param body request body
     * @return ..
     */
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCard(@RequestBody Map<String, String> body) {
        try {
            String key = body.get("key");
            cardAccessor.deleteCard(key);
            return new ResponseEntity<>("delete" + key, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
