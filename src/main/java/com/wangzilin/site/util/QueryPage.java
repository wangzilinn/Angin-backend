package com.wangzilin.site.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 4:32 PM 5/7/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryPage implements Serializable {

    /**
     * @Author wangzilin
     * @Description 当前页
     * @Date 4:33 PM 5/7/2020
     * @Param
     * @return
     **/
    private int page;

    /**
     * @Author wangzilin
     * @Description 每一页的记录数
     * @Date 4:33 PM 5/7/2020
     * @Param
     * @return
     **/
    private int limit;
}
