package com.wangzilin.site.model.DTO;

import com.wangzilin.site.util.QueryPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 11:19 PM 5/13/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Data
@NoArgsConstructor
public class Page<T> {
    private List<T> elements;
    private Integer currentNumber;
    private Long totalNumber;
    private Integer currentPage;
    private Integer totalPages;

    public Page(List<T> elements, QueryPage queryPage, long totalNumber) {
        this.elements = elements;
        this.currentNumber = elements.size();
        this.currentPage = queryPage.getPage();
        this.totalNumber = totalNumber;
        this.totalPages = (int) Math.ceil((double) totalNumber / queryPage.getLimit());
    }
}
