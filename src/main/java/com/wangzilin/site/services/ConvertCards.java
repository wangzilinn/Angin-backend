package com.***REMOVED***.site.services;

import com.***REMOVED***.site.cards.DBCard;
import com.***REMOVED***.site.cards.DisplayedCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 这个类负责将一种类型的卡片转换成另一种
 */
@Service
public class ConvertCards {
    private StatusAndOption[] statusAndOptions;

    @Data
    @AllArgsConstructor
    class StatusAndOption {
        int status;
        String option;
        int addMinuets;
    }

    public ConvertCards() {
        statusAndOptions = new StatusAndOption[8];
        statusAndOptions[0] = new StatusAndOption(-1, "null", 0);
        statusAndOptions[1] = new StatusAndOption(0, "一点没印象", 0);
        statusAndOptions[2] = new StatusAndOption(1, "没啥印象", 10);
        statusAndOptions[3] = new StatusAndOption(2, "好像记住了", 30);
        statusAndOptions[4] = new StatusAndOption(3, "记住了", 300);
        statusAndOptions[5] = new StatusAndOption(4, "记得很清楚", 3000);
        statusAndOptions[6] = new StatusAndOption(5, "永远不会忘", 9000);
        statusAndOptions[7] = new StatusAndOption(6, "我爱你", 999999);
    }

    public DisplayedCard toDisplayedCard(DBCard DBCard) {
        List<String> options = new ArrayList<>();
        int status = DBCard.getStatus();
        //取当前状态的上一个状态放入可选列表
        if (status - 1 >= 0) {
            options.add(findByStatus(status - 1).getOption());
        }
        options.add(findByStatus(status).getOption());
        //取当前状态的下一个状态放入可选列表
        if (status + 1 <= statusAndOptions.length - 1) {
            options.add(findByStatus(status + 1).getOption());
        }
        return new DisplayedCard(DBCard, options);
    }

    private StatusAndOption findByStatus(int status) {
        try {
            for (StatusAndOption statusAndOption : statusAndOptions) {
                int OptionStatus = statusAndOption.getStatus();
                if (status == OptionStatus) {
                    return statusAndOption;
                }
            }
        } catch (Exception e) {
            System.out.println("没有找到卡片对应的option,status:" + status);
        }
        return statusAndOptions[0];
    }

    private StatusAndOption findByOption(String option) {
        try {
            for (StatusAndOption statusAndOption : statusAndOptions) {
                if (option.equals(statusAndOption.getOption())) {
                    return statusAndOption;
                }
            }
        } catch (Exception e) {
            System.out.println("没有找到卡片对应的option,option:" + option);
        }
        return statusAndOptions[0];
    }

    public Date optionToExpirationDate(String option) {
        StatusAndOption statusAndOption = findByOption(option);
        return setExpireDate(statusAndOption.getAddMinuets());

    }

    public int optionsToStatus(String option) {
        return findByOption(option).getStatus();
    }

    private Date setExpireDate(int minutes) {
        Date date = new Date(); //取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        if (minutes > 900000) {
            calendar.add(Calendar.YEAR, 1);
        }
        calendar.add(Calendar.MINUTE, minutes); //把日期往后增加一天,整数  往后推,负数往前移动
        return calendar.getTime(); //这个时间就是日期往后推一天的结果
    }
}
