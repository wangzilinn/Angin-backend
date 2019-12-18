package com.***REMOVED***.site;

import com.***REMOVED***.site.cards.Card;
import com.***REMOVED***.site.cards.ManageCards;
import org.junit.Before;

import org.junit.Test;

import java.util.List;


public class ManageCardsTest {
    private ManageCards manageCards = null;
    @Before
    public void setup(){
        manageCards = new ManageCards();
    }
//    @Test
//    public void testRead() throws Exception{
//        List<String> list = getCards.readTxt();
//        for (String s : list) {
//            Card card = getCards.StringToCard(s);
//            System.out.println(card);
//        }
//        System.out.println("test");
//    }
    @Test
    public void testGet(){
        List<Card> list = manageCards.getCardsFromTxt();
        for (Card card : list) {
            System.out.println(card);
        }
    }

}
