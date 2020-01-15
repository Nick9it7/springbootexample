package com.spring.payload;


import java.util.List;

public class PaginationResponse {
    private List content;
    private Integer totalCount;

    public PaginationResponse(List content, Integer totalCount) {
        this.content = content;
        this.totalCount = totalCount;
    }

    public List getContent() {
        return content;
    }

    public void setContent(List content) {
        this.content = content;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
