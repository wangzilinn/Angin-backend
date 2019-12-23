package com.***REMOVED***.site.services;

import com.***REMOVED***.site.cards.Card;
import com.***REMOVED***.site.cards.DisplayCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChangeCards {
    private StatusAndOption[] statusAndOptions;
    @Data@AllArgsConstructor
    class StatusAndOption {
        int status;
        String option;
        int addMinuets;
    }

    public ChangeCards() {
        statusAndOptions = new StatusAndOption[6];
        statusAndOptions[0] = new StatusAndOption(0, "一点没印象", 0);
        statusAndOptions[1] = new StatusAndOption(1, "没啥印象", 10);
        statusAndOptions[2] = new StatusAndOption(2, "好像记住了", 30);
        statusAndOptions[3] = new StatusAndOption(3, "记住了", 300);
        statusAndOptions[3] = new StatusAndOption(4, "记得很清楚", 3000);
        statusAndOptions[4] = new StatusAndOption(5, "永远不会忘", 9000);
        statusAndOptions[5] = new StatusAndOption(6, "我爱你", 999999);
    }

    public DisplayCard toDisplayCard(Card card) {
        List<String> options = new ArrayList<>();
        int status = card.getStatus();
        if (status - 1 >= 0) {
            options.add(findByStatus(status - 1).getOption());
        }
        options.add(findByStatus(status).getOption());
        if (status + 1 <= 5) {
            options.add(findByStatus(status + 1).getOption());
        }
        return new DisplayCard(card, options);
    }

    private StatusAndOption findByStatus(int status) {
        for (StatusAndOption statusAndOption : statusAndOptions) {
            if (status == statusAndOption.getStatus()) {
                return statusAndOption;
            }
        }
        return null;
    }

    private StatusAndOption findByOption(String option) {
        for (StatusAndOption statusAndOption : statusAndOptions) {
            if (option.equals(statusAndOption.getOption())) {
                return statusAndOption;
            }
        }
        return null;
    }

    public Date optionToExpireData(String option) {
        StatusAndOption statusAndOption = findByOption(option);
        return setExpireDate(statusAndOption.getAddMinuets());

    }

    public int optionsToStatus(String option) {
        return findByOption(option).getStatus();
    }

    private Date setExpireDate(int minutes) {
        Date date=new Date(); //取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        if (minutes > 900000) {
            calendar.add(Calendar.YEAR,1);
        }
        calendar.add(Calendar.MINUTE,minutes); //把日期往后增加一天,整数  往后推,负数往前移动
        return calendar.getTime(); //这个时间就是日期往后推一天的结果
    }
}
