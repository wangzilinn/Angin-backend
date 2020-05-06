package com.***REMOVED***.site.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 4:32 PM 5/7/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryPage implements Serializable {

    /**
     * @Author ***REMOVED***
     * @Description 当前页
     * @Date 4:33 PM 5/7/2020
     * @Param
     * @return
     **/
    private int page;

    /**
     * @Author ***REMOVED***
     * @Description 每一页的记录数
     * @Date 4:33 PM 5/7/2020
     * @Param
     * @return
     **/
    private int limit;
}
