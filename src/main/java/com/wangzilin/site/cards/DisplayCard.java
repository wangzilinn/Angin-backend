package com.***REMOVED***.site.cards;

import java.util.List;

public class DisplayCard extends Card {
    public List<String> options;

    public DisplayCard(Card card, List<String> options) {
        super(card);
        this.options = options;
    }
}
