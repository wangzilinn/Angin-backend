package com.***REMOVED***.site.cards;

import javax.xml.crypto.Data;
import java.util.Date;

public class Card {
    protected String key;
    protected String front;
    protected String back;
    protected Date expireDate;

    Card(String key, String front, String back, Date expireDate) {
        this.key = key;
        this.front = front;
        this.back = back;
        this.expireDate = expireDate;
    }
}
