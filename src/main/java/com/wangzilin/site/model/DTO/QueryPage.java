package com.wangzilin.site.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
     * 查询页数
     */
    @NotNull
    @Min(1)
    private int page;

    /**
     * 每页限制
     */
    @NotNull
    @Min(1)
    private int limit;

    public int getPageForMongoDB() {
        //这个方法只会被mongodb用到, mongodb中,第一页是的index是0
        return page - 1;
    }
}
