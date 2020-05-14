package com.wangzilin.site.model.card;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class DisplayedCard extends Card {
    //之所以要显式的写出来而不是继承是因为restfulController必须这样才可以返回
    public String key;
    public String front;
    public String back;
    public String expireDate;
    public List<String> options;

    public DisplayedCard(){}//这个构造函数必须有, 以供spring调用

    public DisplayedCard(DBCard DBCard, List<String> options) {
        super(DBCard.key, DBCard.front, DBCard.back, DBCard.expireDate);
        this.key = DBCard.key;
        this.front = DBCard.front;
        this.back = DBCard.back;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        this.expireDate = simpleDateFormat.format(DBCard.expireDate);
        this.options = options;


    }
}
