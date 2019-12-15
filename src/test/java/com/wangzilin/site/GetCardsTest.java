package com.***REMOVED***.site;

import com.***REMOVED***.site.cards.Card;
import com.***REMOVED***.site.utils.GetCards;
import org.junit.Before;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;


public class GetCardsTest {
    private GetCards getCards = null;
    @Before
    public void setup(){
        getCards = new GetCards();
    }
    @Test
    public void testRead() throws Exception{
        List<String> list = getCards.read();
        for (String s : list) {
            Card card = getCards.StringToCard(s);
            System.out.println(card);
        }
        System.out.println("test");
    }
    @Test
    public void testGet(){
        List<Card> list = getCards.get();
        for (Card card : list) {
            System.out.println(card);
        }
    }

}
