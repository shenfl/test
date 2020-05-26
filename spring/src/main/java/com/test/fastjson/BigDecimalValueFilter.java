package com.test.fastjson;

import com.alibaba.fastjson.serializer.ValueFilter;

import java.math.BigDecimal;

public class BigDecimalValueFilter implements ValueFilter {
    /**
     * @param object 对象
     * @param name 对象的字段的名称
     * @param value 对象的字段的值
     */
    @Override
    public Object process(Object object, String name, Object value) {
        if(null != value && value instanceof BigDecimal) {
            String str = value.toString();
            if(str.endsWith(".0")) {
                str = str.substring(0, str.length()-2);
            } else if(str.endsWith(".00")) {
                str = str.substring(0, str.length()-3);
            } else {
                str = value.toString();
            }
            return str;
        }
        return value;
    }
}