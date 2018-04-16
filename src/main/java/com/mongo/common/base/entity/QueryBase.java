package com.mongo.common.base.entity;

public class QueryBase {

    /**
     * 页数
     */
    protected int page;

    /**
     * 获取一页行数
     * */
    protected int limit;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
