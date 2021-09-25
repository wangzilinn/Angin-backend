package com.wangzilin.site.model.DTO;

import com.wangzilin.site.constants.BlogConstant;
import com.wangzilin.site.constants.BlogEnum;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 9:56 PM 5/6/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Builder
@ToString
@AllArgsConstructor //Build解决父类依赖
public class Response<T> implements Serializable {
    @Getter
    @Setter
    private int code = BlogConstant.SUCCESS;

    @Getter
    @Setter
    private Object msg = "success";

    @Getter
    @Setter
    private T data;

    public Response() {
        super();
    }

    public Response(T data) {
        super();
        this.data = data;
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(BlogEnum blogEnum) {
        super();
        this.code = blogEnum.getCode();
        this.msg = blogEnum.getMsg();
    }

    public Response(Throwable throwable) {
        super();
        this.code = BlogConstant.ERROR;
        this.msg = throwable.getMessage();
    }

    @Data
    @NoArgsConstructor
    static public class Page<T> {
        private List<T> elements;
        private Integer currentNumber;//当前页面数据长度, 最大为页面大小
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
}
