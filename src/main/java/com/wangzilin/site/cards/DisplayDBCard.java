package com.***REMOVED***.site.cards;

import java.util.List;

public class DisplayDBCard extends Card {
    public List<String> options;

    public DisplayDBCard(DBCard DBCard, List<String> options) {
        super(DBCard.key, DBCard.front, DBCard.back, DBCard.expireDate);
        this.options = options;
    }
}
