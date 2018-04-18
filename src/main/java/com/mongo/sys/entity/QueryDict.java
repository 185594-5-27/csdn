package com.mongo.sys.entity;


import com.mongo.common.base.entity.QueryBase;
import com.mongo.common.base.entity.QueryField;
import com.mongo.common.base.entity.QueryType;

public class QueryDict extends QueryBase {

    @QueryField(type = QueryType.LIKE)
    private String code;
    @QueryField(type = QueryType.LIKE)
    private String text;
    @QueryField(type = QueryType.LIKE)
    private String type;
    @QueryField(type = QueryType.LIKE)
    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
