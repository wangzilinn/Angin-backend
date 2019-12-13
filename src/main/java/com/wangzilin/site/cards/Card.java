package com.***REMOVED***.site.cards;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
@Data
public class Card {
    @Id
    private String id;
    private String front;
    private String back;
    private Date expireDate;
    private Status status;

    public Card(String front, String back) {
        this.front = front;
        this.back = back;
        expireDate = new Date();
        status = Status.KNOW_NOTHING;
    }

    public String toString(){
        return "front: " + front + "back: " + back;
    }
}
enum Status{
    KNOW_NOTHING,FAMILIAR,UNDERSTAND,RECITE
}